package com.zagayevskiy.lang.tokenization;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class InputStreamTokenizer implements Tokenizer {

    static final SortedMap<String, Integer> SYMBOL_TOKENS = new TreeMap<>();
    static {
        mapSymbol("+", Token.PLUS);
        mapSymbol("-", Token.MINUS);
        mapSymbol("*", Token.ASTERISK);
        mapSymbol("=", Token.ASSIGN);
        mapSymbol("==", Token.EQUALS);

        mapSymbol("|", Token.BIT_OR);
        mapSymbol("&", Token.BIT_AND);
        mapSymbol("^", Token.BIT_XOR);
        mapSymbol("~", Token.BIT_NOT);
        mapSymbol("<<", Token.BIT_SHIFT_LEFT);
        mapSymbol(">>", Token.BIT_SHIFT_RIGHT);

        mapSymbol(">", Token.GREATER);
        mapSymbol(">=", Token.GREATER_OR_EQUALS);
        mapSymbol("<", Token.LESS);
        mapSymbol("<=", Token.LESS_OR_EQUALS);

        mapSymbol("!", Token.LOGIC_NOT);
        mapSymbol("||", Token.LOGIC_OR);
        mapSymbol("&&", Token.LOGIC_AND);

        mapSymbol(";", Token.SEMICOLON);
        mapSymbol(",", Token.COMMA);

        mapSymbol("{", Token.BRACE_OPEN);
        mapSymbol("}", Token.BRACE_CLOSE);
        mapSymbol("(", Token.PARENTHESIS_OPEN);
        mapSymbol(")", Token.PARENTHESIS_CLOSE);
    }

    static final Map<String, Integer> KEYWORD_TOKENS = new HashMap<>();
    static {
        mapKeyword(Token.MAIN_NAME, Token.MAIN);
        mapKeyword("function", Token.FUNCTION);
        mapKeyword("struct", Token.STRUCT);
        mapKeyword("var", Token.VAR);

        mapKeyword("true", Token.TRUE);
        mapKeyword("false", Token.FALSE);

        mapKeyword("if", Token.IF);
        mapKeyword("else", Token.ELSE);
    }

    private static void mapSymbol(@Nonnull String value, int tokenType) {
        assert !SYMBOL_TOKENS.containsKey(value);
        assert !SYMBOL_TOKENS.containsValue(tokenType);
        SYMBOL_TOKENS.put(value, tokenType);
    }

    private static void mapKeyword(@Nonnull String keyword, int tokenType) {
        assert !KEYWORD_TOKENS.containsKey(keyword);
        assert !KEYWORD_TOKENS.containsValue(tokenType);
        KEYWORD_TOKENS.put(keyword, tokenType);
    }

    private static final String WHITE_SPACES = " \n";

    private enum State {
        IDLE,
        TOKENIZATION
    }

    private State state = State.IDLE;
    private BufferedReader bufferedReader;
    @Nullable
    private String currentLine;
    @Nullable
    private String currentChar;

    private int currentIndex;
    private int lineNumber;

    public InputStreamTokenizer(@Nonnull InputStream inputStream) {
        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Nonnull
    @Override
    public Token nextToken() throws IOException {
        if (state == State.IDLE) {
            initCharReader();
            state = State.TOKENIZATION;
        }

        if (currentChar == null) {
            return new Token(Token.FINISH, "", currentIndex, lineNumber);
        }

        while(isWhiteSpace(currentChar)) {
            nextChar();
            if (currentChar == null) {
                return new Token(Token.FINISH, "", currentIndex, lineNumber);
            }
        }

        assert !currentChar.isEmpty();

        StringBuilder buffer = new StringBuilder(currentChar);

        if (Character.isDigit(currentChar.charAt(0))) {
            nextChar();
            while (Character.isDigit(currentChar.charAt(0))) {
                buffer.append(currentChar);
                nextChar();
            }

            return new Token(Token.INTEGER, buffer.toString(), currentIndex, lineNumber);
        }

        if (Character.isAlphabetic(currentChar.charAt(0))) {
            nextChar();
            while (Character.isAlphabetic(currentChar.charAt(0))) {
                buffer.append(currentChar);
                nextChar();
            }

            String bufferString = buffer.toString();
            if (KEYWORD_TOKENS.containsKey(bufferString)) {
                return new Token(KEYWORD_TOKENS.get(bufferString), bufferString, currentIndex, lineNumber);
            }

            return new Token(Token.IDENTIFIER, bufferString, currentIndex, lineNumber);
        }

        String currentBuffer = currentChar;
        String prevBuffer = null;

        SortedMap<String, Integer> found = SYMBOL_TOKENS.tailMap(currentBuffer);
        SortedMap<String, Integer> prevFound = null;

        while (!found.isEmpty() && found.firstKey().startsWith(currentBuffer)) {
            prevBuffer = currentBuffer;
            prevFound = found;

            nextChar();
            if (currentChar == null) {
                break;
            }

            currentBuffer = buffer.append(currentChar).toString();

            found = found.tailMap(currentBuffer);
        }

        if (prevFound != null) {
            final Integer mayBeTokenType = prevFound.get(prevBuffer);
            if (mayBeTokenType != null) {
                return new Token(mayBeTokenType, prevBuffer, currentIndex, lineNumber);
            } else {

                throw new RuntimeException(String.format(
                        "Unknown sequence \"%s\", may be \"%s\"?",
                        prevBuffer,
                        getAllWithPrefix(prevFound, prevBuffer)));
            }
        } else {
            throw new RuntimeException(String.format("Unknown symbol \"%s\"", currentBuffer));
        }
    }

    //TODO: remove
    private List<String> linesDebug = new ArrayList<>();
    @Nullable
    @Override
    public String getLineDebug(int lineNumber) {
        return linesDebug.get(lineNumber);
    }

    private void initCharReader() throws IOException {
        lineNumber = 0;
        currentIndex = 0;
        currentLine = bufferedReader.readLine();
        linesDebug.add(currentLine);
        nextChar();
    }

    private void nextChar() throws IOException {
        if (currentLine == null) {
            currentChar = null;
            return;
        }

        if (currentIndex == currentLine.length()) {
            currentChar = "\n";
            ++currentIndex;
            return;
        }

        while (currentIndex >= currentLine.length()) {
            currentLine = bufferedReader.readLine();
            linesDebug.add(currentLine);
            ++lineNumber;

            if (currentLine == null) {
                currentChar = null;
                return;
            }

            currentIndex = 0;
        }

        currentChar = currentLine.substring(currentIndex, currentIndex + 1);

        ++currentIndex;
    }

    private String getAllWithPrefix(SortedMap<String, ?> map, String prefix) {
        StringBuilder builder = new StringBuilder();
        for (String s: map.keySet()) {
            if (s.startsWith(prefix)) {
                builder.append(s).append(" or ");
            } else {
                break;
            }
        }

        return builder.substring(0, builder.length() - 3);
    }

    private boolean isWhiteSpace(@Nonnull String character) {
        return WHITE_SPACES.contains(character);
    }
}
