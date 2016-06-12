package com.zagayevskiy.lang.tokenization;

public class Token {
    
    public static final String MAIN_NAME = "main";

    public static final int FINISH = 0x0;
    public static final int IDENTIFIER = 0x1;
    public static final int INTEGER = 0x2;
    public static final int STRING = 0x3;

    public static final int ASSIGN = 0x10;
    public static final int EQUALS = 0x11;
    public static final int NOT_EQUALS = 0x12;

    public static final int BIT_OR = 0x100;
    public static final int BIT_AND = 0x101;
    public static final int BIT_XOR = 0x102;
    public static final int BIT_SHIFT_LEFT = 0x103;
    public static final int BIT_SHIFT_RIGHT = 0x104;
    public static final int BIT_NOT = 0x105;

    public static final int PLUS = 0x1001;
    public static final int MINUS = 0x1002;
    public static final int ASTERISK = 0x1003;
    public static final int PERCENT = 0x1004;
    public static final int SLASH = 0x1005;

    public static final int LESS = 0x10000;
    public static final int GREATER = 0x10001;
    public static final int LESS_OR_EQUALS = 0x10002;
    public static final int GREATER_OR_EQUALS = 0x10003;

    public static final int LOGIC_NOT = 0x100000;
    public static final int LOGIC_OR = 0x100001;
    public static final int LOGIC_AND = 0x100002;

    public static final int SEMICOLON = 0x1000000;
    public static final int BRACE_OPEN = 0x1000001;
    public static final int BRACE_CLOSE = 0x1000002;
    public static final int PARENTHESIS_OPEN = 0x1000003;
    public static final int PARENTHESIS_CLOSE = 0x1000004;
    public static final int SQUARE_BRACKET_OPEN = 0x1000005;
    public static final int SQUARE_BRACKET_CLOSE = 0x1000006;
    public static final int COMMA = 0x1000007;
    public static final int ARROW_RIGHT = 0x1000008;
    public static final int BACKSLASH = 0x1000009;

    public static final int MAIN = 0x10000001;
    public static final int FUNCTION = 0x10000002;
    public static final int STRUCT = 0x10000003;
    public static final int VAR = 0x10000004;
    public static final int TRUE = 0x10000005;
    public static final int FALSE = 0x10000006;
    public static final int IF = 0x10000007;
    public static final int ELSE = 0x10000008;
    public static final int NEW = 0x10000009;
    public static final int RETURN = 0x1000000A;

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
