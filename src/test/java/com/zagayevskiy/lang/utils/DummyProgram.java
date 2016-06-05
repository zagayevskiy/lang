package com.zagayevskiy.lang.utils;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.IProgram;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.LangUndefined;

import javax.annotation.Nonnull;
import java.util.*;

public class DummyProgram implements IProgram {

    public SortedMap<String, IFunction> funcs = new TreeMap<>();

    public static class Builder implements IProgram.Builder {

        DummyProgram program = new DummyProgram();

        @Override
        public boolean hasFunction(@Nonnull String name) {
            return program.funcs.containsKey(name);
        }

        @Nonnull
        @Override
        public IProgram.Builder addFunction(@Nonnull IFunction function) {
            program.funcs.put(function.getName(), function);
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
        public IFunction createFunction(@Nonnull final String name) {
            return new DummyFunction(name);
        }
    }

    @Override
    public void pushOperand(@Nonnull Operand operand) {
    }

    @Nonnull
    @Override
    public Operand popOperand() {
        return new Operand() {
        };
    }

    @Nonnull
    @Override
    public LangObject execute() {
        return LangUndefined.INSTANCE;
    }
}
