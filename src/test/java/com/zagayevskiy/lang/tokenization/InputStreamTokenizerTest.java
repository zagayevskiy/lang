package com.zagayevskiy.lang.tokenization;

import com.zagayevskiy.lang.TestUtils;
import javafx.util.Pair;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.junit.Assert.*;

public class InputStreamTokenizerTest {

    @Test
    public void mapsFilledRight() {
        assertMapFilledRight("SYMBOL_TOKENS", InputStreamTokenizer.SYMBOL_TOKENS);
        assertMapFilledRight("KEYWORD_TOKENS", InputStreamTokenizer.KEYWORD_TOKENS);
    }

    @Test
    public void symbolicTokenizationOneLine() throws IOException {
        StringBuilder builder = new StringBuilder();
        for (String symbol: InputStreamTokenizer.SYMBOL_TOKENS.keySet()) {
            builder.append(symbol).append("   ");
        }

        InputStreamTokenizer tokenizer = new InputStreamTokenizer(TestUtils.stringStream(builder.toString()));

        for (Map.Entry<String, Integer> entry: InputStreamTokenizer.SYMBOL_TOKENS.entrySet()) {
            Token token = tokenizer.nextToken();
            assertEquals(entry.getKey(), token.value);
            assertEquals(entry.getValue().intValue(), token.type);
            assertEquals(0, token.lineNumber);
        }

        assertTrue(Token.FINISH == tokenizer.nextToken().type);
    }

    @Test
    public void symbolicTokenizationMultipleLines() throws IOException {
        StringBuilder builder = new StringBuilder();
        for (String symbol: InputStreamTokenizer.SYMBOL_TOKENS.keySet()) {
            builder.append(symbol).append("\n");
        }

        InputStreamTokenizer tokenizer = new InputStreamTokenizer(TestUtils.stringStream(builder.toString()));

        int lineNumber = 0;
        for (Map.Entry<String, Integer> entry: InputStreamTokenizer.SYMBOL_TOKENS.entrySet()) {
            Token token = tokenizer.nextToken();
            assertEquals(entry.getKey(), token.value);
            assertEquals(entry.getValue().intValue(), token.type);
            assertEquals(lineNumber, token.lineNumber);
            ++lineNumber;
        }

        assertTrue(Token.FINISH == tokenizer.nextToken().type);
    }

    @Test
    public void symbolicTokenizationMultipleTimes() throws IOException {
        List<Map.Entry<String, Integer>> expected = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Integer> entry: InputStreamTokenizer.SYMBOL_TOKENS.entrySet()) {
            builder.append(entry.getKey()).append("  ");
            expected.add(entry);

            builder.append(entry.getKey()).append(" \n ");
            expected.add(entry);

            builder.append(entry.getKey()).append(" ");
            expected.add(entry);
        }

        InputStreamTokenizer tokenizer = new InputStreamTokenizer(TestUtils.stringStream(builder.toString()));

        for(Map.Entry<String, Integer> entry: expected) {
            Token token = tokenizer.nextToken();

            assertEquals(entry.getKey(), token.value);
            assertEquals(entry.getValue().intValue(), token.type);
        }

        assertEquals(Token.FINISH, tokenizer.nextToken().type);
    }

    @Test
    public void numberConstsTokenization() throws IOException {
        StringBuilder builder = new StringBuilder();
        List<String> expectedList = new ArrayList<>();
        for (int i = 0; i < 10500; ++i) {
            for (int j = 0; j < 10; ++j) {
                i += j;
                String value = String.valueOf(i);
                expectedList.add(value);
                builder
                        .append(value)
                        .append("  ");
            }
            builder.append("\n\n\n \n");
        }

        InputStreamTokenizer tokenizer = new InputStreamTokenizer(TestUtils.stringStream(builder.toString()));

        for (String expected: expectedList) {
            Token token = tokenizer.nextToken();
            assertEquals(expected, token.value);
            assertEquals(Token.INTEGER, token.type);
        }

        assertEquals(Token.FINISH, tokenizer.nextToken().type);
    }

    @Test
    public void numberConstSymbolTokensMixTokenization() throws IOException {
        StringBuilder builder = new StringBuilder();
        List<Pair<String, Integer>> expectedList = new ArrayList<>();
        for (int i = 0; i < 1050; ++i) {
            for (int j = 0; j < 10; ++j) {
                i += j;
                String text = String.valueOf(i);
                expectedList.add(new Pair<>(text, Token.INTEGER));
                builder.append(text);

                Pair<String, Integer> pair = getSymbolicTokenAt(i);
                expectedList.add(pair);
                builder.append(pair.getKey());
            }

            builder.append("\n\n\n \n");
        }

        InputStreamTokenizer tokenizer = new InputStreamTokenizer(TestUtils.stringStream(builder.toString()));

        for (Pair<String, Integer> expected: expectedList) {
            Token token = tokenizer.nextToken();
            assertEquals(expected.getKey(), token.value);
            assertEquals(expected.getValue().intValue(), token.type);
        }

        assertEquals(Token.FINISH, tokenizer.nextToken().type);
    }

    @Test
    public void keywordsTokenization() throws IOException {
        StringBuilder builder = new StringBuilder();
        List<Map.Entry<String, Integer>> expectedList = new ArrayList<>();

        for (Map.Entry<String, Integer> entry: InputStreamTokenizer.KEYWORD_TOKENS.entrySet()) {
            builder.append(entry.getKey()).append(" ");
            expectedList.add(entry);

            for (Map.Entry<String, Integer> entry0: InputStreamTokenizer.KEYWORD_TOKENS.entrySet()) {
                builder.append(entry0.getKey()).append(" ");
                expectedList.add(entry0);
            }

            builder.append(entry.getKey()).append("\n\n\n");
            expectedList.add(entry);
        }

        InputStreamTokenizer tokenizer = new InputStreamTokenizer(TestUtils.stringStream(builder.toString()));

        for(Map.Entry<String, Integer> expected: expectedList) {
            Token token = tokenizer.nextToken();
            assertEquals(expected.getKey(), token.value);
            assertEquals(expected.getValue().intValue(), token.type);
        }

        assertEquals(Token.FINISH, tokenizer.nextToken().type);
    }

    @Test
    public void keywordsConstsSymbolicMixTokenization() throws IOException {
        StringBuilder builder = new StringBuilder();
        List<Pair<String, Integer>> expectedList = new ArrayList<>();

        for(int i = 0; i < 100; ++i) {
            for (Map.Entry<String, Integer> entry : InputStreamTokenizer.KEYWORD_TOKENS.entrySet()) {
                builder.append(entry.getKey()).append("  ");
                expectedList.add(entryToPair(entry));

                String text = String.valueOf(i);
                builder.append(text).append(" ");
                expectedList.add(new Pair<>(text, Token.INTEGER));

                builder.append(entry.getKey());
                expectedList.add(entryToPair(entry));

                Pair<String, Integer> pair = getSymbolicTokenAt(i);
                builder.append(pair.getKey());
                expectedList.add(pair);

                builder.append(entry.getKey()).append("   ");
                expectedList.add(entryToPair(entry));
            }
        }

        InputStreamTokenizer tokenizer = new InputStreamTokenizer(TestUtils.stringStream(builder.toString()));

        for (Pair<String, Integer> expected: expectedList) {
            Token token = tokenizer.nextToken();
            assertEquals(expected.getValue().intValue(), token.type);
            assertEquals(expected.getKey(), token.value);
        }

        assertEquals(Token.FINISH, tokenizer.nextToken().type);
    }

    private Pair<String, Integer> getSymbolicTokenAt(int position) {
        Set<Map.Entry<String, Integer>> entrySet = InputStreamTokenizer.SYMBOL_TOKENS.entrySet();
        position %= entrySet.size();

        Iterator<Map.Entry<String, Integer>> iterator = entrySet.iterator();

        for(; position > 0; --position) {
            iterator.next();
        }

        return entryToPair(iterator.next());
    }

    private <K, V> Pair<K, V> entryToPair(@Nonnull Map.Entry<K, V> entry) {
        return new Pair<>(entry.getKey(), entry.getValue());
    }

    private void assertMapFilledRight(String mapName, Map<String, Integer> map) {
        Map<Integer, String> exists = new HashMap<>();
        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            final Integer value = entry.getValue();
            final String key = entry.getKey();
            assertFalse("In " + mapName + ": Values must not be equals! In " + exists.get(value) + " and " + key + " : " + value, exists.containsKey(value));
            exists.put(value, key);
        }
    }

}