package com.zagayevskiy.lang;

import com.zagayevskiy.lang.logging.ConsoleLogger;
import com.zagayevskiy.lang.parser.Parser;
import com.zagayevskiy.lang.runtime.IProgram;
import com.zagayevskiy.lang.runtime.Program;
import com.zagayevskiy.lang.tokenization.InputStreamTokenizer;

import java.io.ByteArrayInputStream;

public class App {

    public static void main(String[] args) {
        final String programText = "main { var x = 3; var y = x + 5; x = y - 7; y = x << x; (y + x)*111; }";

        final Parser parser = new Parser(
                new InputStreamTokenizer(new ByteArrayInputStream(programText.getBytes())),
                new Program.Builder(),
                new Program.Factory(),
                new ConsoleLogger());

        final IProgram program = parser.parse();

        assert program != null;

        System.out.println(program.execute().toLangString().stringValue);
    }
}
