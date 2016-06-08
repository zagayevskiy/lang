package com.zagayevskiy.lang.utils;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.IProgram;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.LangUndefined;
import com.zagayevskiy.lang.runtime.types.classes.LangStructClass;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class DummyProgram implements IProgram {

    public SortedMap<String, IFunction> funcs = new TreeMap<>();
    public Map<String, LangStructClass> structs = new HashMap<>();

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
        public IProgram.Builder addStruct(@Nonnull LangStructClass struct) {
            program.structs.put(struct.getName(), struct);
            return this;
        }

        @Nullable
        @Override
        public LangStructClass getStruct(@Nonnull String name) {
            return program.structs.get(name);
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

    @Nonnull
    @Override
    public LangObject execute() {
        return LangUndefined.INSTANCE;
    }
}
