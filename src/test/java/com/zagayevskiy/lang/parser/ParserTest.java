package com.zagayevskiy.lang.parser;

import com.zagayevskiy.lang.runtime.IProgram;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.instructions.impl.PopInstruction;
import com.zagayevskiy.lang.runtime.types.LangInteger;
import com.zagayevskiy.lang.tokenization.InputStreamTokenizer;
import com.zagayevskiy.lang.utils.DummyFunction;
import com.zagayevskiy.lang.utils.DummyProgram;
import com.zagayevskiy.lang.utils.TestUtils;
import com.zagayevskiy.lang.utils.ThrowLogger;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nonnull;

import java.io.InputStream;

import static org.junit.Assert.*;

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
        final String[] programs = {
                "main{;}",
                "main { 2 + 3 * (1 + (1 << 1) * 2); }",
                "main { var x, y, z=123, t, qwerty,\n asdf=345; var g, h, k; \n var temp = qwerty; }",
                "main { 1; var x = 2; var y = x + 1, z = y + 2;  x * 10; var result = x<<y<<z; }"
        };

        for (String program: programs) {
            assertParsingCorrect(program);
        }
    }

    @Test
    public void multipleExpressionsOperatorsParsing() {
        assertParsingCorrect("", TestUtils.fileStream("parser/multiple_expressions_operators.txt"));
    }

    //TODO: move it
    @Test
    public void justIntegerConstsListGeneration() {
        int intsCount = 10;
        StringBuilder programBuilder = new StringBuilder("main{");
        for(int i = 0; i < intsCount; ++i) {
            programBuilder.append(i).append(";");
        }
        programBuilder.append("}");

        DummyFunction main = mainFrom(programBuilder.toString());

        int index = 0;
        for (Instruction instruction: main.instructions) {
            System.out.print(instruction.toString());
            System.out.print(" ");

            if (index % 2 == 0) {
                final int expected = index / 2;
                LangInteger actual = (LangInteger)instruction;
                assertEquals(expected, actual.intValue);
                assertFalse(actual.isNan);
            } else {
                assertEquals(PopInstruction.class, instruction.getClass());
            }

            ++index;
        }
    }

    //TODO: move it
    @Test
    public void generate() {
        String programText = "main { 2 + 3 * (1 + (1 << 1) * 2); }";

        DummyFunction main = mainFrom(programText);

        for (Instruction i: main.instructions) {
            System.out.print(i.toString());
            System.out.print("; ");
        }
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
        assertNotNull(program);

    }


    private DummyFunction mainFrom(String programText) {
        Parser parser = new Parser(new InputStreamTokenizer(TestUtils.stringStream(programText)),
                dummyBuilder,
                dummyFactory,
                ThrowLogger.INSTANCE);
        DummyProgram program = (DummyProgram) parser.parse();
        assertNotNull(program);

        assertEquals(program.funcs.size(), 1);

        return  (DummyFunction) program.funcs.get(program.funcs.firstKey());
    }
}