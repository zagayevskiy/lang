package com.zagayevskiy.lang.parser;

import com.zagayevskiy.lang.logging.Logger;
import com.zagayevskiy.lang.runtime.IProgram;
import com.zagayevskiy.lang.runtime.IVariable;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.instructions.impl.VariableInstruction;
import com.zagayevskiy.lang.runtime.types.function.prototype.IFunctionPrototype;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.LangUndefined;
import com.zagayevskiy.lang.runtime.types.primitive.string.LangString;
import com.zagayevskiy.lang.runtime.types.struct.LangStructClass;
import com.zagayevskiy.lang.tokenization.Token;
import com.zagayevskiy.lang.tokenization.Tokenizer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;

public class Parser {

    private enum State {
        IDLE,
        PARSING
    }

    private final Tokenizer tokenizer;
    private final IProgram.Builder programBuider;
    private final IProgram.Factory programFactory;
    private final Logger logger;

    private State state = State.IDLE;
    private Token token;
    private IFunctionPrototype.Builder functionPrototypeBuilder;


    public Parser(@Nonnull Tokenizer tokenizer,
                  @Nonnull IProgram.Builder programBuilder,
                  @Nonnull IProgram.Factory programFactory, @Nonnull Logger logger) {
        this.tokenizer = tokenizer;
        this.programBuider = programBuilder;
        this.programFactory = programFactory;
        this.logger = logger;
    }

    @Nullable
    public IProgram parse() {
        if (state == State.IDLE) {
            initParser();
            state = State.PARSING;
        }

        if (program()) {
            return programBuider.build();
        }

        return null;
    }

    private boolean program() {
        //noinspection StatementWithEmptyBody
        while (defFunction() || defStruct() || defClass()) ;

        return defMain();
    }

    private boolean defClass() {
        if (token.type != Token.CLASS) {
            return false;
        }
        nextToken();

        if (token.type != Token.IDENTIFIER) {
            log("Identifier expected after 'class'");
            return false;
        }
        nextToken();

        if (token.type != Token.BRACE_OPEN) {
            log("'{' expected in class definition");
            return false;
        }
        nextToken();

        //TODO
        final IFunctionPrototype.Builder fieldsInitializer = programFactory.createFunctionBuilder("TODO");
        functionPrototypeBuilder = fieldsInitializer;

        //noinspection StatementWithEmptyBody
        while (defMethod() || defConstructor() || defVariables());

        functionPrototypeBuilder = null;
        fieldsInitializer.getStub().getName();//TODO MAY BE CALL

        if (token.type != Token.BRACE_CLOSE) {
            log("'}' expected at the end of class definition");
            return false;
        }
        nextToken();

        return true;
    }

    private boolean defMain() {
        if (token.type != Token.MAIN) {
            log("main expected");
            return false;
        }

        final String mainName = token.value;

        if (programBuider.hasFunctionClass(mainName)) {
            logger.logError(mainName + " already exists");
        }

        functionPrototypeBuilder = programFactory.createFunctionBuilder(mainName);
        programBuider.setMainClass(functionPrototypeBuilder.getStub());

        nextToken();

        if (!block()) {
            log("block expected");
            return false;
        }

        return true;
    }

    private boolean defStruct() {
        if (token.type != Token.STRUCT) {
            return false;
        }
        nextToken();

        if (token.type != Token.IDENTIFIER) {
            log("Identifier (struct name) expected after 'struct' keyword.");
            return false;
        }
        if (programBuider.getStruct(token.value) != null) {
            log(token.value + " struct already defined");
        }
        final LangStructClass struct = new LangStructClass(token.value);

        nextToken();

        if (token.type != Token.BRACE_OPEN) {
            log("'{' expected after struct");
            return false;
        }

        do {
            nextToken();
            if (token.type != Token.IDENTIFIER) {
                log("Identifier expected");
                return false;
            }
            struct.addProperty(token.value);
            nextToken();

        } while (token.type == Token.COMMA);

        if (token.type != Token.BRACE_CLOSE) {
            log("'}' expected at the end of struct definition.");
            return false;
        }

        programBuider.addStruct(struct);

        nextToken();
        return true;
    }

    private boolean defFunction() {
        if (token.type != Token.FUNCTION) {
            return false;
        }
        nextToken();

        if (token.type != Token.IDENTIFIER) {
            log("identifier expected after 'function'");
            return false;
        }

        if (programBuider.hasFunctionClass(token.value)) {
            log("function " + token.value + " already defined");
            return false;
        }

        functionPrototypeBuilder = programFactory.createFunctionBuilder(token.value);

        nextToken();

        if (!defFunctionArguments()) {
            log("function arguments expected");
            return false;
        }

        programBuider.addFunctionClass(functionPrototypeBuilder.getStub());

        if (!block()) {
            log("block expected after function definition");
            return false;
        }

        return true;
    }

    private boolean defMethod() {
        if (token.type != Token.METHOD) {
            return false;
        }
        nextToken();

        if (token.type != Token.IDENTIFIER) {
            log("identifier expected after 'method'");
            return false;
        }

        //TODO
        final IFunctionPrototype.Builder temp = functionPrototypeBuilder;
        functionPrototypeBuilder = programFactory.createMethodBuilder(token.value);

        nextToken();

        if (!defFunctionArguments()) {
            log("method arguments expected");
            return false;
        }

        if (!block()) {
            log("method body expected");
            return false;
        }

        functionPrototypeBuilder = temp;

        return true;
    }

    private boolean defConstructor() {
        if (token.type != Token.IDENTIFIER) {
            return false;
        }
        nextToken();

        //TODO
        final IFunctionPrototype.Builder temp = functionPrototypeBuilder;
        functionPrototypeBuilder = programFactory.createMethodBuilder(token.value);

        if (!defFunctionArguments()) {
            log("constructor arguments expected");
            return false;
        }

        if(!block()) {
            log("constructor body expected");
            return false;
        }

        functionPrototypeBuilder = temp;

        return true;
    }

    private boolean defFunctionArguments() {
        if (token.type != Token.PARENTHESIS_OPEN) {
            log("'(' expected after at the begin of args list");
            return false;
        }

        do {
            nextToken();
            if (token.type != Token.IDENTIFIER) {
                log("Identifier expected");
                return false;
            }

            functionPrototypeBuilder.addArgument(token.value);
            nextToken();

        } while (token.type == Token.COMMA);

        if (token.type != Token.PARENTHESIS_CLOSE) {
            log("')' expected at the end of args list");
            return false;
        }
        nextToken();

        return true;
    }

    private boolean block() {
        if (token.type != Token.BRACE_OPEN) {
            return false;
        }
        nextToken();

        if (!operator()) {
            return false;
        }

        do {
            functionPrototypeBuilder.addInstruction(Instruction.POP);
        } while (operator());

        functionPrototypeBuilder.removeLastInstruction();

        if (token.type != Token.BRACE_CLOSE) {
            log("} expected");
            return false;
        }
        nextToken();

        return true;
    }

    private boolean operator() {
        return block() ||
                defVariables() ||
                expressionOperator() ||
                ifOperator() ||
                returnOperator() ||
                loopFor() ||
                emptyOperator();
    }

    private boolean loopFor() {
        if (token.type != Token.FOR) {
            return false;
        }
        nextToken();

        if (token.type != Token.PARENTHESIS_OPEN) {
            log("'(' expected after 'for'");
            return false;
        }
        nextToken();

        //for(HERE; ...; ...) ...
        if (!expression()) {
            functionPrototypeBuilder.addInstruction(LangUndefined.INSTANCE);
        }
        if (token.type != Token.SEMICOLON) {
            log("';' expected");
            return false;
        }
        nextToken();
        final LangInteger conditionAddress = LangInteger.from(functionPrototypeBuilder.getInstructionsCount());
        //for(...; HERE; ...) ...
        if (!expression()) {
            functionPrototypeBuilder.addInstruction(LangBoolean.TRUE);
        }
        final int pasteOutsideAddressHere = functionPrototypeBuilder.getInstructionsCount();
        final int pasteBodyBeginAddressHere = pasteOutsideAddressHere + 3;
        functionPrototypeBuilder
                .addInstruction(Instruction.NOP)
                .addInstruction(Instruction.JUMP_FALSE)
                .addInstruction(Instruction.POP)  //Just to pop initialization result or previous step result
                .addInstruction(Instruction.NOP)
                .addInstruction(Instruction.JUMP);

        if (token.type != Token.SEMICOLON) {
            log("';' expected");
            return false;
        }
        nextToken();

        final LangInteger stepAddress = LangInteger.from(functionPrototypeBuilder.getInstructionsCount());
        //for (...; ...; HERE) ...
        if (expression()) {
            functionPrototypeBuilder.addInstruction(Instruction.POP);
        }
        functionPrototypeBuilder
                .addInstruction(conditionAddress)
                .addInstruction(LangInteger.JUMP);

        if (token.type != Token.PARENTHESIS_CLOSE) {
            log("')' expected after 'for'");
            return false;
        }
        nextToken();

        functionPrototypeBuilder.putInstruction(
                LangInteger.from(functionPrototypeBuilder.getInstructionsCount()),
                pasteBodyBeginAddressHere);

        //for (...;...;...) HERE
        if (!operator()) {
            log("operator expected after 'for(...)'");
            return false;
        }

        functionPrototypeBuilder
                .addInstruction(stepAddress)
                .addInstruction(Instruction.JUMP)
                .putInstruction(LangInteger.from(functionPrototypeBuilder.getInstructionsCount()), pasteOutsideAddressHere);

        return true;
    }

    private boolean returnOperator() {
        if (token.type != Token.RETURN) {
            return false;
        }

        nextToken();
        if (!expression()) {
            log("expression expected after 'return'");
            return false;
        }

        if (token.type != Token.SEMICOLON) {
            log("';' expected");
            return false;
        }

        functionPrototypeBuilder.addInstruction(Instruction.RETURN);
        return true;
    }

    private boolean ifOperator() {
        if (token.type != Token.IF) {
            return false;
        }

        nextToken();

        if (!expressionInParenthesis()) {
            log("(expression) expected");
            return false;
        }
        final int jumpToElseAddress = functionPrototypeBuilder.getInstructionsCount();
        functionPrototypeBuilder
                .addInstruction(Instruction.NOP)
                .addInstruction(Instruction.JUMP_FALSE);

        if (!operator()) {
            log("operator expected after if(expression)");
            return false;
        }

        final int jumpToEndOfElseAddress = functionPrototypeBuilder.getInstructionsCount();
        functionPrototypeBuilder
                .addInstruction(Instruction.NOP)
                .addInstruction(Instruction.JUMP)
                .putInstruction(LangInteger.from(functionPrototypeBuilder.getInstructionsCount()), jumpToElseAddress);

        if (token.type == Token.ELSE) {

            nextToken();

            if (!operator()) {
                log("operator expected after else");
                return false;
            }

        } else {
            functionPrototypeBuilder.addInstruction(LangUndefined.INSTANCE);
        }

        functionPrototypeBuilder.putInstruction(LangInteger.from(functionPrototypeBuilder.getInstructionsCount()), jumpToEndOfElseAddress);

        return true;
    }

    private boolean defVariables() {
        if (token.type != Token.VAR) {
            return false;
        }

        nextToken();

        if (!defSingleVariable()) {
            log("variable definition expected");
            return false;
        }

        while (token.type == Token.COMMA) {
            functionPrototypeBuilder.addInstruction(Instruction.POP);
            nextToken();
            if (!defSingleVariable()) {
                log("variable definition expected");
                return false;
            }
        }

        if (token.type != Token.SEMICOLON) {
            log("; expected after variables definition");
            return false;
        }
        nextToken();

        return true;
    }

    private boolean defSingleVariable() {

        if (token.type != Token.IDENTIFIER) {
            return false;
        }

        if (functionPrototypeBuilder.hasVariable(token.value)) {
            log("Variable " + token.value + " already defined.");
            return false;
        }

        final IVariable variable = functionPrototypeBuilder.addVariable(token.value);
        final String variableName = token.value;

        nextToken();

        if (token.type == Token.ASSIGN) {

            functionPrototypeBuilder.addInstruction(VariableInstruction.from(variable.getId(), variableName));

            nextToken();

            if (!expression()) {
                log("expression expected after '=' in variable definition");
                return false;
            }

            functionPrototypeBuilder.addInstruction(Instruction.ASSIGN);
        } else {
            functionPrototypeBuilder.addInstruction(LangUndefined.INSTANCE);
        }

        return true;
    }

    private boolean emptyOperator() {
        if (token.type != Token.SEMICOLON) {
            return false;
        }
        functionPrototypeBuilder.addInstruction(LangUndefined.INSTANCE);
        nextToken();
        return true;
    }

    private boolean expressionOperator() {
        if (!expression()) {
            return false;
        }

        if (token.type != Token.SEMICOLON) {
            log("; expected");
            return false;
        }
        nextToken();

        return true;
    }

    private boolean expression() {
        if (!conjunction()) {
            return false;
        }

        while (token.type == Token.LOGIC_OR) {
            nextToken();

            if (!conjunction()) {
                log("sub-expression expected after '||'");
                return false;
            }
            functionPrototypeBuilder.addInstruction(Instruction.LOGIC_OR);
        }

        return true;
    }

    private boolean conjunction() {

        if (!bitOr()) {
            return false;
        }

        while (token.type == Token.LOGIC_AND) {
            nextToken();
            if (!bitOr()) {
                log("sub-expression expected after '&&'");
                return false;
            }
            functionPrototypeBuilder.addInstruction(Instruction.LOGIC_AND);
        }

        return true;
    }

    private boolean bitOr() {
        if (!bitXor()) {
            return false;
        }

        while (token.type == Token.BIT_OR) {
            nextToken();
            if (!bitXor()) {
                log("expression expected after |");
                return false;
            }
            functionPrototypeBuilder.addInstruction(Instruction.BIT_OR);
        }

        return true;
    }

    private boolean bitXor() {
        if (!bitAnd()) {
            return false;
        }

        while (token.type == Token.BIT_XOR) {
            nextToken();
            if (!bitAnd()) {
                log("expression expected after ^");
                return false;
            }
            functionPrototypeBuilder.addInstruction(Instruction.BIT_XOR);
        }

        return true;
    }

    private boolean bitAnd() {
        if (!equality()) {
            return false;
        }

        while (token.type == Token.BIT_AND) {
            nextToken();
            if (!equality()) {
                log("expression expected after &");
                return false;
            }

            functionPrototypeBuilder.addInstruction(Instruction.BIT_AND);
        }

        return true;
    }

    private boolean equality() {
        if (!comparison()) {
            return false;
        }

        for (Instruction i; (i = Mapper.equality(token)) != null; ) {
            nextToken();
            if (!comparison()) {
                log("expression expected after " + i.toString());
                return false;
            }
            functionPrototypeBuilder.addInstruction(i);
        }

        return true;
    }

    private boolean comparison() {
        if (!bitShifting()) {
            return false;
        }

        for (Instruction i; (i = Mapper.comparison(token))!= null; ) {
            nextToken();
            if (!bitShifting()) {
                log("expression expected after " + i.toString());
                return false;
            }
            functionPrototypeBuilder.addInstruction(i);
        }

        return true;
    }

    private boolean bitShifting() {
        if (!addition()) {
            return false;
        }

        for (Instruction i; (i = Mapper.bitShift(token)) != null; ) {
            nextToken();
            if (!addition()) {
                return false;
            }
            functionPrototypeBuilder.addInstruction(i);
        }

        return true;
    }

    private boolean addition() {
        if (!multiplication()) {
            return false;
        }

        for (Instruction i; (i = Mapper.addition(token)) != null; ) {
            nextToken();
            if (!multiplication()) {
                log("expression expected after " + i.toString());
                return false;
            }

            functionPrototypeBuilder.addInstruction(i);
        }

        return true;
    }

    private boolean multiplication() {
        if (!unary()) {
            return false;
        }

        for (Instruction i; (i = Mapper.multiplication(token)) != null; ) {
            nextToken();
            if (!unary()) {
                log("expression expected after " + i.toString());
                return false;
            }
            functionPrototypeBuilder.addInstruction(i);
        }

        return true;
    }

    private boolean unary() {

        final Instruction unaryInstruction = Mapper.unary(token);
        if (unaryInstruction != null) {
            nextToken();
            if (!defValue()) {
                log("value expected after " + unaryInstruction.toString());
                return false;
            }

            functionPrototypeBuilder.addInstruction(unaryInstruction);
            return true;
        }

        return defValue();
    }

    private boolean defValue() {

        if (token.type == Token.IDENTIFIER) {

            if (functionPrototypeBuilder.hasVariable(token.value)) {
                functionPrototypeBuilder.addInstruction(VariableInstruction.from(functionPrototypeBuilder.getVariable(token.value).getId(), token.value));
            } else if (programBuider.hasFunctionClass(token.value)) {
                functionPrototypeBuilder.addInstruction(programBuider.getFunctionClass(token.value));
            } else {
                log("unknown identifier " + token.value);
                return false;
            }

            nextToken();

            if (token.type == Token.ASSIGN) {
                nextToken();
                if (!expression()) {
                    log("expression expected after '='");
                    return false;
                }

                functionPrototypeBuilder.addInstruction(Instruction.ASSIGN);
            } else {
                chain();
            }

            return true;
        }

        return defConst() ||
                expressionInParenthesis() ||
                defNewArray() ||
                newStructInstance() ||
                defLambda() ||
                block();
    }

    private boolean defLambda() {
        if (token.type != Token.BACKSLASH) {
            return false;
        }

        nextToken();

        IFunctionPrototype.Builder savedBuilder = functionPrototypeBuilder;
        functionPrototypeBuilder = programFactory.createAnonymousFunctionBuilder();
        try {

            if (token.type != Token.PARENTHESIS_OPEN) {
                log("'(' expected after lambda ");
                return false;
            }

            do {
                nextToken();
                if (token.type != Token.IDENTIFIER) {
                    log("Identifier expected");
                    return false;
                }

                functionPrototypeBuilder.addArgument(token.value);
                nextToken();

            } while (token.type == Token.COMMA);

            if (token.type != Token.PARENTHESIS_CLOSE) {
                log("')' expected at the end of lambda args definition.");
                return false;
            }

            nextToken();
            if (!expression()) {
                log("expression expected at the end of lambda definition");
                return false;
            }
            savedBuilder.addInstruction(functionPrototypeBuilder.getStub());
        } finally {
            functionPrototypeBuilder = savedBuilder;
        }

        chain();
        return true;
    }

    private boolean chain() {
        if (token.type == Token.SQUARE_BRACKET_OPEN) {
            nextToken();

            if (!expression()) {
                log("expression expected in [].");
                return false;
            }

            if (token.type != Token.SQUARE_BRACKET_CLOSE) {
                log("] expected.");
                return false;
            }

            functionPrototypeBuilder.addInstruction(Instruction.ARRAY_DEREFERENCE);

            nextToken();

            if (token.type == Token.ASSIGN) {
                nextToken();
                if (!expression()) {
                    log("expression expected in array[e] = HERE");
                    return false;
                }
                functionPrototypeBuilder.addInstruction(Instruction.ASSIGN);
                return true;
            } else {
                chain();
            }

            return true;
        }

        if (token.type == Token.ARROW_RIGHT) {
            nextToken();
            if (token.type != Token.IDENTIFIER) {
                log("identifier (property name) expected after '->'.");
                return false;
            }
            functionPrototypeBuilder
                    .addInstruction(LangString.from(token.value))
                    .addInstruction(Instruction.BIND_PROPERTY);

            nextToken();

            if (token.type == Token.ASSIGN) {
                nextToken();
                if (!expression()) {
                    log("expression expected in s->p = HERE");
                    return false;
                }
                functionPrototypeBuilder.addInstruction(Instruction.ASSIGN);
                return true;
            }

            chain();
            return true;
        }

        if (token.type == Token.PARENTHESIS_OPEN) {
            nextToken();
            final int argsCount = expressionsList();

            if (token.type != Token.PARENTHESIS_CLOSE) {
                log("')' expected");
                return false;
            }

            functionPrototypeBuilder
                    .addInstruction(LangInteger.from(argsCount))
                    .addInstruction(Instruction.CALL);

            nextToken();
            chain();
        }

        return false;
    }

    private boolean defNewArray() {
        if (token.type != Token.SQUARE_BRACKET_OPEN) {
            return false;
        }

        nextToken();

        final int count = expressionsList();
        if (count < 0) {
            log("expressions list expected inside [].");
            return false;
        }

        if (token.type != Token.SQUARE_BRACKET_CLOSE) {
            log("] expected.");
            return false;
        }
        functionPrototypeBuilder
                .addInstruction(LangInteger.from(count))
                .addInstruction(Instruction.NEW_ARRAY);

        nextToken();

        return true;
    }

    private boolean newStructInstance() {
        if (token.type != Token.NEW) {
            return false;
        }
        nextToken();

        if (token.type != Token.IDENTIFIER) {
            log("identifier expected after new");
            return false;
        }
        final LangStructClass clazz = programBuider.getStruct(token.value);
        if (clazz == null) {
            log("struct " + token.value + " not defined");
            return false;
        }
        nextToken();
        if (token.type != Token.PARENTHESIS_OPEN) {
            log("( expected.");
            return false;
        }
        nextToken();

        final int argsCount = expressionsList();
        if (argsCount != clazz.getPropertiesCount()) {
            log(clazz.getPropertiesCount() + " args expected, but " + argsCount + " found.");
            return false;
        }

        if (token.type != Token.PARENTHESIS_CLOSE) {
            log(") expected.");
            return false;
        }
        nextToken();

        functionPrototypeBuilder
                .addInstruction(LangInteger.from(argsCount))
                .addInstruction(clazz)
                .addInstruction(Instruction.NEW_STRUCT_INSTANCE);

        return true;
    }

    private int expressionsList() {
        if (!expression()) {
            return 0;
        }

        int result = 1;

        while (token.type == Token.COMMA) {
            nextToken();
            if (!expression()) {
                log("expression expected after ','");
                return -1;
            }
            ++result;
        }

        return result;
    }

    private boolean expressionInParenthesis() {
        if (token.type != Token.PARENTHESIS_OPEN) {
            return false;
        }

        nextToken();
        if (!expression()) {
            log("expression expected after (");
            return false;
        }

        if (token.type != Token.PARENTHESIS_CLOSE) {
            log(") expected");
            return false;
        }

        nextToken();
        return true;
    }

    private boolean defConst() {
        final Instruction constInstruction = Mapper.constant(token);
        if (constInstruction == null) {
            return false;
        }
        functionPrototypeBuilder.addInstruction(constInstruction);

        nextToken();
        return true;
    }

    private void initParser() {
        nextToken();
    }

    private void nextToken() {
        try {
            token = tokenizer.nextToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void log(@Nonnull String message) {
        String line = tokenizer.getLineDebug(token.lineNumber);
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < token.position - 1; ++i) {
            builder.append("~");
        }
        builder.append("^");

        logger.logError(String.format(
                "At %s : %s   -   %s. Found: #%s -> '%s'\n"
                        + "%s\n"
                        + "%s\n",
                String.valueOf(token.lineNumber),
                String.valueOf(token.position),
                message,
                String.valueOf(token.type),
                token.value,
                line,
                builder.toString()));
    }
}
