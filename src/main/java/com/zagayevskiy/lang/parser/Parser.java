package com.zagayevskiy.lang.parser;

import com.zagayevskiy.lang.logging.Logger;
import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.IProgram;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.instructions.impl.BitShiftLeftInstruction;
import com.zagayevskiy.lang.runtime.instructions.impl.PlusInstruction;
import com.zagayevskiy.lang.runtime.instructions.impl.PopInstruction;
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
        return expressionOperator() | emptyOperator();
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
                return false;
            }
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
                return false;
            }
        }

        return true;
    }

    private boolean bitOr() {
        if (!bitXor()) {
            return false;
        }

        while (token.type == Token.LOGIC_OR) {
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

        while (token.type == Token.BIT_OR) {
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

        while (token.type == Token.PLUS) { //TODO: -
            nextToken();
            if (!multiplication()) {
                return false;
            }

            currentFunction.addInstruction(Instruction.PLUS);
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

        if (token.type == Token.LOGIC_NOT) { //TODO ~
            nextToken();
        }

        return defValue();
    }

    private boolean defValue() {

        if (token.type == Token.INTEGER) {
            final int intValue = Integer.parseInt(token.value);

            currentFunction.addInstruction(LangInteger.from(intValue));

            nextToken();
            return true;
        }

        if (token.type == Token.IDENTIFIER) {
            nextToken();
            return true;
        }

        if (token.type == Token.PARENTHESIS_OPEN) {
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

        return false;
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
        for (int i = 0; i < token.position; ++i) {
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
