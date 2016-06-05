package com.zagayevskiy.lang.runtime;

import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.tokenization.Token;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class Program implements IProgram {

    public static class Builder implements IProgram.Builder {

        private Program program = new Program();

        @Override
        public boolean hasFunction(@Nonnull String name) {
            return program.functions.containsKey(name);
        }

        @Nonnull
        @Override
        public IProgram.Builder addFunction(@Nonnull IFunction function) {
            program.functions.put(function.getName(), function);
            if (Token.MAIN_NAME.equals(function.getName())) {
                program.main = function;
            }
            return this;
        }

        @Nonnull
        @Override
        public IProgram build() {
            return program;
        }
    }

    public static class Factory implements IProgram.Factory {
        @Nonnull
        @Override
        public IFunction createFunction(@Nonnull String name) {
            return new Function(name);
        }
    }

    private Map<String, IFunction> functions = new HashMap<>();
    private IFunction main;

    @Nonnull
    @Override
    public LangObject execute() {
        return main.execute();
    }
}
