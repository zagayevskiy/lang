package com.zagayevskiy.lang.parser;

import com.zagayevskiy.lang.logging.Logger;
import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.IProgram;
import com.zagayevskiy.lang.runtime.IVariable;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.instructions.impl.VariableInstruction;
import com.zagayevskiy.lang.runtime.types.LangBoolean;
import com.zagayevskiy.lang.runtime.types.LangInteger;
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
    private IFunction currentFunction;


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
        return defFunction() | defStruct() | defMain();
    }

    private boolean defMain() {
        if (token.type != Token.MAIN) {
            return false;
        }

        final String mainName = token.value;

        if (programBuider.hasFunction(mainName)) {
            logger.logError(mainName + " already exists");
        }

        currentFunction = programFactory.createFunction(mainName);
        programBuider.addFunction(currentFunction);

        nextToken();

        if (!block()) {
            log("block expected");
            return false;
        }

        return true;
    }

    private boolean defStruct() {
        return token.type == 0;
    }

    private boolean defFunction() {
        return token.type == 0;
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
            currentFunction.addInstruction(Instruction.POP);
        } while(operator());

        currentFunction.removeLastInstruction();

        if (token.type != Token.BRACE_CLOSE) {
            log("} expected");
            return false;
        }

        nextToken();

        return true;
    }

    private boolean operator() {
        return defVariables() | expressionOperator() | emptyOperator();
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

        if (currentFunction.hasVariable(token.value)) {
            log("Variable " + token.value + " already defined.");
            return false;
        }

        final IVariable variable = currentFunction.addVariable(token.value);
        final String variableName = token.value;

        nextToken();

        if (token.type == Token.ASSIGN) {

            currentFunction.addInstruction(VariableInstruction.from(variable.getId(), variableName));

            nextToken();

            if (!expression()) {
                log("expression expected after '=' in variable definition");
                return false;
            }

            currentFunction.addInstruction(Instruction.ASSIGN);
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
                log("sub-expression expected after '||'");
                return false;
            }
            currentFunction.addInstruction(Instruction.LOGIC_OR);
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
            currentFunction.addInstruction(Instruction.LOGIC_AND);
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
            currentFunction.addInstruction(Instruction.BIT_SHIFT_LEFT);
        }

        return true;
    }

    private boolean addition() {
        if (!multiplication()) {
            return false;
        }

        while (token.type == Token.PLUS || token.type == Token.MINUS) {
            final Instruction additionInstruction  = token.type == Token.PLUS
                    ? Instruction.PLUS
                    : Instruction.MINUS;

            nextToken();
            if (!multiplication()) {
                return false;
            }

            currentFunction.addInstruction(additionInstruction);
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
            currentFunction.addInstruction(Instruction.MULTIPLY);
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
                log("value expected afted " + unaryInstruction.toString());
                return false;
            }

            currentFunction.addInstruction(unaryInstruction);
            return true;
        }

        return defValue();
    }

    private boolean defValue() {

        if (token.type == Token.IDENTIFIER) {

            if (currentFunction.hasVariable(token.value)) {
                currentFunction.addInstruction(VariableInstruction.from(currentFunction.getVariable(token.value).getId(), token.value));
            }

            nextToken();

            if (token.type == Token.ASSIGN) {
                nextToken();
                if (!expression()) {
                    log("expression expected after '='");
                    return false;
                }

                currentFunction.addInstruction(Instruction.ASSIGN);
            }

            return true;
        }

        return defIntConst() |
                defBooleanConst() |
                expressionInParenthesis();
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
        currentFunction.addInstruction(LangInteger.from(intValue));
        nextToken();

        return true;
    }

    private boolean defBooleanConst() {

        switch (token.type) {
            case Token.TRUE:
                currentFunction.addInstruction(LangBoolean.TRUE);
                break;

            case Token.FALSE:
                currentFunction.addInstruction(LangBoolean.FALSE);
                break;

            default: return false;
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
            throw  new RuntimeException(e);
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
