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
    public void simpleMainParsing() {
        String program = "main{;}";
        Parser parser = new Parser(new InputStreamTokenizer(TestUtils.stringStream(program)),
                dummyBuilder,
                dummyFactory,
                ThrowLogger.INSTANCE);
        assertParsingCorrect(parser);
    }

    @Test
    public void multipleExpressionsOperatorsParsing() {
        Parser parser = new Parser(new InputStreamTokenizer(TestUtils.fileStream("parser/multiple_expressions_operators.txt")),
                dummyBuilder,
                dummyFactory,
                ThrowLogger.INSTANCE);

        assertParsingCorrect(parser);
    }

    //TODO: move it
    @Test
    public void justIntegerConstsListGeneration() {
        int intsCount = 1000;
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

    private void assertParsingCorrect(@Nonnull Parser parser) {
        IProgram program = parser.parse();
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