package com.zagayevskiy.lang.tokenization;

public class Token {
    
    public static final String MAIN_NAME = "main";

    public static final int FINISH = 0;
    public static final int PLUS = 1;
    public static final int MINUS = 2;
    public static final int ASTERISK = 3;

    public static final int ASSIGN = 4;
    public static final int EQUALS = 5;

    public static final int BIT_OR = 8;
    public static final int BIT_AND = 9;
    public static final int BIT_XOR = 10;
    public static final int BIT_SHIFT_LEFT = 11;
    public static final int BIT_SHIFT_RIGHT = 12;
    public static final int BIT_NOT = 13;

    public static final int LESS = 20;
    public static final int GREATER = 21;
    public static final int LESS_OR_EQUALS = 22;
    public static final int GREATER_OR_EQUALS = 23;

    public static final int LOGIC_NOT = 30;
    public static final int LOGIC_OR = 31;
    public static final int LOGIC_AND = 32;

    public static final int SEMICOLON = 0x01000000;
    public static final int BRACE_OPEN = 0x01000001;
    public static final int BRACE_CLOSE = 0x01000002;
    public static final int PARENTHESIS_OPEN = 0x01000003;
    public static final int PARENTHESIS_CLOSE = 0x01000004;
    public static final int COMMA = 0x01000005;

    public static final int IDENTIFIER = 0x0100;
    public static final int INTEGER = 0x0101;

    public static final int MAIN = 0x010001;
    public static final int FUNCTION = 0x010002;
    public static final int STRUCT = 0x010003;
    public static final int VAR = 0x010004;
    public static final int TRUE = 0x010005;
    public static final int FALSE = 0x010006;
    public static final int IF = 0x010007;
    public static final int ELSE = 0x010008;

    public final int type;
    public final String value;
    public final int position;
    public final int lineNumber;

    public Token(int type, String value, int position, int lineNumber) {
        this.type = type;
        this.value = value;
        this.position = position;
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return value + "#" + type;
    }
}
