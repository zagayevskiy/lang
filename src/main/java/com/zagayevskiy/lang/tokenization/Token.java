package com.zagayevskiy.lang.tokenization;

public class Token {

    public static int FINISH = 0x0;
    public static int PLUS = 0x1;
    public static int MINUS = 0x2;
    public static int ASTERISK = 0x3;
    public static int ASSIGN = 0x4;
    public static int EQUALS = 0x5;

    public static int IDENTIFIER = 0x0100;
    public static int INTEGER = 0x0101;

    public static int MAIN = 0x010001;
    public static int FUNCTION = 0x010002;
    public static int STRUCT = 0x010003;

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
}
