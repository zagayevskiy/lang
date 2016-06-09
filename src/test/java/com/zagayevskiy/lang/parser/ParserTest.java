package com.zagayevskiy.lang.parser;

import com.zagayevskiy.lang.Programs;
import com.zagayevskiy.lang.runtime.IProgram;
import com.zagayevskiy.lang.tokenization.InputStreamTokenizer;
import com.zagayevskiy.lang.utils.DummyProgram;
import com.zagayevskiy.lang.utils.TestUtils;
import com.zagayevskiy.lang.utils.ThrowLogger;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

public class ParserTest {

    private IProgram.Builder dummyBuilder;
    private IProgram.Factory dummyFactory;

    @Before
    public void setUp() {
        dummyBuilder = new DummyProgram.Builder();
        dummyFactory = new DummyProgram.Factory();
    }

    @Test
    public void parsingCorrect() {
        for (Pair<String, ?> program: Programs.PROGRAMS) {
            assertParsingCorrect(program.getKey());
        }
    }

    @Test
    public void multipleExpressionsOperatorsParsing() {
        assertParsingCorrect("", TestUtils.fileStream("parser/multiple_expressions_operators.txt"));
    }

    private void assertParsingCorrect(@Nonnull String program) {
        assertParsingCorrect("In program '" + program.replaceAll("\n", "\\n") + "'", TestUtils.stringStream(program));
    }

    private void assertParsingCorrect(@Nonnull String message, @Nonnull InputStream program) {
        setUp();
        Parser parser = new Parser(new InputStreamTokenizer(program),
                dummyBuilder,
                dummyFactory,
                ThrowLogger.INSTANCE);
        assertParsingCorrect(message, parser);
    }

    private void assertParsingCorrect(@Nonnull String message, @Nonnull Parser parser) {
        IProgram program;
        try {
           program =parser.parse();
        } catch (Throwable t) {
            throw new AssertionError(message, t);
        }
        assertNotNull(message, program);

    }
}