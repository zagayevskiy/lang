package com.zagayevskiy.lang.parser;

import com.zagayevskiy.lang.tokenization.Token;
import com.zagayevskiy.lang.tokenization.Tokenizer;

import javax.annotation.Nonnull;
import java.io.IOException;

public class Parser {

    private enum State {
        IDLE,
        PARSING
    }

    private State state = State.IDLE;
    private Tokenizer tokenizer;
    private Token token;


    public Parser(@Nonnull Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public boolean parse() {
        if (state == State.IDLE) {
            initParser();
            state = State.PARSING;
        }

        return program();
    }

    private boolean program() {
        return defFunction() | defStruct() | defMain();
    }

    private boolean defMain() {
        if (token.type != Token.MAIN) {
            return false;
        }

        nextToken();
        return block();
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

        while(operator());

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
            nextToken();
            return true;
        }

        if (token.type == Token.IDENTIFIER) {
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
        System.out.println(String.format(
                "At %s : %s\t-\t%s. Found: #%s -> '%s'",
                String.valueOf(token.lineNumber),
                String.valueOf(token.position),
                message,
                String.valueOf(token.type),
                token.value));
        String line = tokenizer.getLineDebug(token.lineNumber);
        System.out.println(line);
        for (int i = 0; i < token.position; ++i) {
            System.out.print("~");
        }
        System.out.println("^");
    }
}
