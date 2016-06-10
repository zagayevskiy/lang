package com.zagayevskiy.lang.parser;

import com.zagayevskiy.lang.logging.Logger;
import com.zagayevskiy.lang.runtime.types.classes.function.IFunctionClass;
import com.zagayevskiy.lang.runtime.IProgram;
import com.zagayevskiy.lang.runtime.IVariable;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.instructions.impl.VariableInstruction;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.LangString;
import com.zagayevskiy.lang.runtime.types.classes.LangStructClass;
import com.zagayevskiy.lang.runtime.types.primitive.LangUndefined;
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
    private IFunctionClass.Builder functionClassBuilder;


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
        while (defFunction() | defStruct()) ;

        return defMain();
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

        functionClassBuilder = programFactory.createFunctionBuilder(mainName);
        programBuider.setMainClass(functionClassBuilder.getStub());

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
            log(token.value + " struct already defined" );
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

        functionClassBuilder = programFactory.createFunctionBuilder(token.value);

        nextToken();
        if (token.type != Token.PARENTHESIS_OPEN) {
            log("'(' expected after 'function'");
            return false;
        }

        do {
            nextToken();
            if (token.type != Token.IDENTIFIER) {
                log("Identifier expected");
                return false;
            }

            functionClassBuilder.addArgument(token.value);
            nextToken();

        } while (token.type == Token.COMMA);

        if (token.type != Token.PARENTHESIS_CLOSE) {
            log("')' expected at the end of function args definition.");
            return false;
        }
        programBuider.addFunctionClass(functionClassBuilder.getStub());

        nextToken();
        if (!block()) {
            log("block expected after function definition");
            return false;
        }

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
            functionClassBuilder.addInstruction(Instruction.POP);
        } while (operator());

        functionClassBuilder.removeLastInstruction();

        if (token.type != Token.BRACE_CLOSE) {
            log("} expected");
            return false;
        }

        nextToken();

        return true;
    }

    private boolean operator() {
        return block() |
                defVariables() |
                expressionOperator() |
                ifOperator() |
                emptyOperator();
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
        final int jumpToElseAddress = functionClassBuilder.getInstructionsCount();
        functionClassBuilder
                .addInstruction(Instruction.NOP)
                .addInstruction(Instruction.JUMP_FALSE);

        if (!operator()) {
            log("operator expected after if(expression)");
            return false;
        }

        final int jumpToEndOfElseAddress = functionClassBuilder.getInstructionsCount();
        functionClassBuilder
                .addInstruction(Instruction.NOP)
                .addInstruction(Instruction.JUMP)
                .putInstruction(LangInteger.from(functionClassBuilder.getInstructionsCount()), jumpToElseAddress);

        if (token.type == Token.ELSE) {

            nextToken();

            if (!operator()) {
                log("operator expected after else");
                return false;
            }

        } else {
            functionClassBuilder.addInstruction(LangUndefined.INSTANCE);
        }

        functionClassBuilder.putInstruction(LangInteger.from(functionClassBuilder.getInstructionsCount()), jumpToEndOfElseAddress);

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
            functionClassBuilder.addInstruction(Instruction.POP);
            nextToken();
            if (!defSingleVariable()) {
                log("variable definition expected");
                return false;
            }
        }

        return true;
    }

    private boolean defSingleVariable() {

        if (token.type != Token.IDENTIFIER) {
            return false;
        }

        if (functionClassBuilder.hasVariable(token.value)) {
            log("Variable " + token.value + " already defined.");
            return false;
        }

        final IVariable variable = functionClassBuilder.addVariable(token.value);
        final String variableName = token.value;

        nextToken();

        if (token.type == Token.ASSIGN) {

            functionClassBuilder.addInstruction(VariableInstruction.from(variable.getId(), variableName));

            nextToken();

            if (!expression()) {
                log("expression expected after '=' in variable definition");
                return false;
            }

            functionClassBuilder.addInstruction(Instruction.ASSIGN);
        } else {
            functionClassBuilder.addInstruction(LangUndefined.INSTANCE);
        }

        return true;
    }

    private boolean emptyOperator() {
        if (token.type != Token.SEMICOLON) {
            return false;
        }
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

        return true;
    }

    private boolean expression() {
        if (!conjunction()) {
            return false;
        }

        while (token.type == Token.LOGIC_OR) {
            nextToken();

            if (!conjunction()) {
                log("sub-expression expected after '|'");
                return false;
            }
            functionClassBuilder.addInstruction(Instruction.LOGIC_OR);
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
            functionClassBuilder.addInstruction(Instruction.LOGIC_AND);
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
                return false;
            }
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
                return false;
            }
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
                return false;
            }
        }

        return true;
    }

    private boolean equality() {
        if (!comparison()) {
            return false;
        }

        while (token.type == Token.EQUALS) { //TODO: !=
            nextToken();
            if (!comparison()) {
                return false;
            }
        }

        return true;
    }

    private boolean comparison() {
        if (!bitShifting()) {
            return false;
        }

        while (token.type == Token.GREATER) { //TODO: >=, <, <=
            nextToken();
            if (!bitShifting()) {
                return false;
            }
            functionClassBuilder.addInstruction(Instruction.GREATER);
        }

        return true;
    }

    private boolean bitShifting() {
        if (!addition()) {
            return false;
        }

        while (token.type == Token.BIT_SHIFT_LEFT) { //TODO: >>
            nextToken();
            if (!addition()) {
                return false;
            }
            functionClassBuilder.addInstruction(Instruction.BIT_SHIFT_LEFT);
        }

        return true;
    }

    private boolean addition() {
        if (!multiplication()) {
            return false;
        }

        while (token.type == Token.PLUS | token.type == Token.MINUS) {
            final Instruction additionInstruction = token.type == Token.PLUS
                    ? Instruction.PLUS
                    : Instruction.MINUS;

            nextToken();
            if (!multiplication()) {
                return false;
            }

            functionClassBuilder.addInstruction(additionInstruction);
        }

        return true;
    }

    private boolean multiplication() {
        if (!unary()) {
            return false;
        }

        while (token.type == Token.ASTERISK) { //TODO: /, %
            nextToken();
            if (!unary()) {
                return false;
            }
            functionClassBuilder.addInstruction(Instruction.MULTIPLY);
        }

        return true;
    }

    private boolean unary() {

        Instruction unaryInstruction = null;
        switch (token.type) {
            case Token.LOGIC_NOT:
                unaryInstruction = Instruction.LOGIC_NOT;
                break;
            case Token.BIT_NOT:
                //TODO unaryInstruction = Instruction.BIT_NOT
                break;
        }

        if (unaryInstruction != null) {
            nextToken();
            if (!defValue()) {
                log("arrayList expected afted " + unaryInstruction.toString());
                return false;
            }

            functionClassBuilder.addInstruction(unaryInstruction);
            return true;
        }

        return defValue();
    }

    private boolean defValue() {

        if (token.type == Token.IDENTIFIER) {

            if (functionClassBuilder.hasVariable(token.value)) {
                functionClassBuilder.addInstruction(VariableInstruction.from(functionClassBuilder.getVariable(token.value).getId(), token.value));
            } else if (programBuider.hasFunctionClass(token.value)) {
                functionClassBuilder.addInstruction(programBuider.getFunctionClass(token.value));
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

                functionClassBuilder.addInstruction(Instruction.ASSIGN);
            } else {
                chain();
            }

            return true;
        }

        return defIntConst() |
                defBooleanConst() |
                expressionInParenthesis() |
                defNewArray() |
                newStructInstance();
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

            functionClassBuilder.addInstruction(Instruction.ARRAY_DEREFERENCE);

            nextToken();

            if (token.type == Token.ASSIGN) {
                nextToken();
                if (!expression()) {
                    log("expression expected in array[e] = HERE");
                    return false;
                }
                functionClassBuilder.addInstruction(Instruction.ASSIGN);
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
            functionClassBuilder
                    .addInstruction(LangString.from(token.value))
                    .addInstruction(Instruction.PROPERTY_DEREFERENCE);

            nextToken();

            if (token.type == Token.ASSIGN) {
                nextToken();
                if (!expression()) {
                    log("expression expected in s->p = HERE");
                    return false;
                }
                functionClassBuilder.addInstruction(Instruction.ASSIGN);
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

            functionClassBuilder
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
        functionClassBuilder
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

        functionClassBuilder
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

    private boolean defIntConst() {
        if (token.type != Token.INTEGER) {
            return false;
        }

        final int intValue = Integer.parseInt(token.value);
        functionClassBuilder.addInstruction(LangInteger.from(intValue));
        nextToken();

        return true;
    }

    private boolean defBooleanConst() {

        switch (token.type) {
            case Token.TRUE:
                functionClassBuilder.addInstruction(LangBoolean.TRUE);
                break;

            case Token.FALSE:
                functionClassBuilder.addInstruction(LangBoolean.FALSE);
                break;

            default:
                return false;
        }

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
