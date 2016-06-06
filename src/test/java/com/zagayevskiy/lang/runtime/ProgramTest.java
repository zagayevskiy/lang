package com.zagayevskiy.lang.runtime;

import com.zagayevskiy.lang.Programs;
import com.zagayevskiy.lang.parser.Parser;
import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.tokenization.InputStreamTokenizer;
import com.zagayevskiy.lang.tokenization.Token;
import com.zagayevskiy.lang.utils.SpyProgramFactory;
import com.zagayevskiy.lang.utils.TestUtils;
import com.zagayevskiy.lang.utils.ThrowLogger;
import javafx.util.Pair;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static com.zagayevskiy.lang.utils.TestUtils.assertOperandsStachEmpty;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ProgramTest {

    @Test
    public void execution() {
        for (Pair<String, LangObject> p: Programs.PROGRAMS) {

            SpyProgramFactory spyFactory = new SpyProgramFactory(new Program.Factory());

            final Parser parser = new Parser(
                    new InputStreamTokenizer(new ByteArrayInputStream(p.getKey().getBytes())),
                    new Program.Builder(),
                    spyFactory,
                    new ThrowLogger());

            final IProgram program = parser.parse();

            assertThat(program, notNullValue());

            final LangObject result;
            try {
                result = program.execute();
            } catch (Throwable t) {
                throw new RuntimeException("Exception in: " + p.getKey(), t);
            }

            assertThat("In: " + p.getKey(), result, equalTo(p.getValue()));

            assertOperandsStachEmpty(spyFactory.funcs.get(Token.MAIN_NAME));
        }
    }
}