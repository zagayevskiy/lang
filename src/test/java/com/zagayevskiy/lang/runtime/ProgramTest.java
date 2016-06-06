package com.zagayevskiy.lang.runtime;

import com.zagayevskiy.lang.Programs;
import com.zagayevskiy.lang.parser.Parser;
import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.tokenization.InputStreamTokenizer;
import com.zagayevskiy.lang.utils.ThrowLogger;
import javafx.util.Pair;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ProgramTest {

    @Test
    public void execution() {
        for (Pair<String, LangObject> p: Programs.PROGRAMS) {

            final Parser parser = new Parser(
                    new InputStreamTokenizer(new ByteArrayInputStream(p.getKey().getBytes())),
                    new Program.Builder(),
                    new Program.Factory(),
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
        }
    }
}