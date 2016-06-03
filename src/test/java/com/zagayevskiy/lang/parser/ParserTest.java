package com.zagayevskiy.lang.parser;

import com.zagayevskiy.lang.TestUtils;
import com.zagayevskiy.lang.tokenization.InputStreamTokenizer;
import org.junit.Test;

import javax.annotation.Nonnull;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void simpleMainParsing() {
        String program = "main{;}";
        Parser parser = new Parser(new InputStreamTokenizer(TestUtils.stringStream(program)));
        assertParsingCorrect(parser);
    }

    @Test
    public void multipleExpressionsOperatorsParsing() {
        Parser parser = new Parser(new InputStreamTokenizer(TestUtils.fileStream("parser/multiple_expressions_operators.txt")));

        assertParsingCorrect(parser);
    }

    private void assertParsingCorrect(@Nonnull Parser parser) {
        boolean b = parser.parse();
        assertTrue(b);
    }
}