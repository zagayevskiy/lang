package com.zagayevskiy.lang.utils;

import com.zagayevskiy.lang.runtime.IProgram;
import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.classes.LangStructClass;
import com.zagayevskiy.lang.runtime.types.classes.function.IFunctionClass;
import com.zagayevskiy.lang.runtime.types.primitive.LangUndefined;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class DummyProgram implements IProgram {

    public SortedMap<String, IFunctionClass> funcs = new TreeMap<>();
    public Map<String, LangStructClass> structs = new HashMap<>();

    public static class Builder implements IProgram.Builder {

        DummyProgram program = new DummyProgram();

        @Override
        public boolean hasFunctionClass(@Nonnull String name) {
            return program.funcs.containsKey(name);
        }

        @Nonnull
        @Override
        public IProgram.Builder addFunctionClass(@Nonnull IFunctionClass function) {
            program.funcs.put(function.getName(), function);
            return this;
        }

        @Nonnull
        @Override
        public IProgram.Builder setMainClass(@Nonnull IFunctionClass mainClass) {
            program.funcs.put(mainClass.getName(), mainClass);
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
        public IFunctionClass.Builder createFunctionBuilder(@Nonnull final String name) {
            return new DummyFunctionClassBuilder(name);
        }
    }

    @Nonnull
    @Override
    public LangObject execute() {
        return LangUndefined.INSTANCE;
    }
}
