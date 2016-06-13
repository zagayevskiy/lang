package com.zagayevskiy.lang.runtime;

import com.zagayevskiy.lang.Programs;
import com.zagayevskiy.lang.parser.Parser;
import com.zagayevskiy.lang.runtime.types.IContext;
import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.tokenization.InputStreamTokenizer;
import com.zagayevskiy.lang.utils.TestUtils;
import com.zagayevskiy.lang.utils.ThrowLogger;
import com.zagayevskiy.lang.utils.spy.SpyFunctionPrototype;
import com.zagayevskiy.lang.utils.spy.SpyFunctionClassBuilder;
import com.zagayevskiy.lang.utils.spy.SpyProgramFactory;
import javafx.util.Pair;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.io.InputStream;

import static com.zagayevskiy.lang.utils.TestUtils.assertOperandsStackEmpty;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ProgramTest {

    @Test
    public void executionStrings() {
        for (Pair<String, LangObject> p: Programs.PROGRAMS) {
            assertExecutionCorrect(p.getKey(), TestUtils.stringStream(p.getKey()), p.getValue());
        }
    }

    @Test
    public void executionFiles() {
        for (Pair<String, LangObject> p: Programs.FILES) {
            assertExecutionCorrect(p.getKey(), TestUtils.fileStream(p.getKey()), p.getValue());
        }
    }

    private void assertExecutionCorrect(@Nonnull String programId, @Nonnull InputStream inputStream, @Nonnull LangObject expectedResult) {
        SpyProgramFactory spyFactory = new SpyProgramFactory(new Program.Factory());

        final Parser parser = new Parser(
                new InputStreamTokenizer(inputStream),
                new Program.Builder(),
                spyFactory,
                new ThrowLogger());

        final IProgram program = parser.parse();

        assertThat(program, notNullValue());

        final LangObject result;
        try {
            result = program.execute();
        } catch (Throwable t) {
            throw new RuntimeException("Exception in: " + programId, t);
        }

        assertThat("In: " + programId, result, equalTo(expectedResult));

        for (SpyFunctionClassBuilder spyBuilder: spyFactory.spyFuncsClazzBuilders.values()) {
            for (SpyFunctionPrototype spyFunctionClass: spyBuilder.spyFunctionClasses) {
                for (IFunction f: spyFunctionClass.functions) {
                    assertOperandsStackEmpty("In: " + programId + ". Func: " + f.getName(), (IContext)f);
                }
            }
        }
    }
}