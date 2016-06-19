package com.zagayevskiy.lang.utils;

import com.zagayevskiy.lang.runtime.IProgram;
import com.zagayevskiy.lang.runtime.types.base.LangObject;
import com.zagayevskiy.lang.runtime.types.struct.LangStructClass;
import com.zagayevskiy.lang.runtime.types.function.prototype.IFunctionPrototype;
import com.zagayevskiy.lang.runtime.types.function.prototype.IMethodPrototype;
import com.zagayevskiy.lang.runtime.types.primitive.LangUndefined;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class DummyProgram implements IProgram {

    public SortedMap<String, IFunctionPrototype> funcs = new TreeMap<>();
    public Map<String, LangStructClass> structs = new HashMap<>();

    public static class Builder implements IProgram.Builder {

        DummyProgram program = new DummyProgram();

        @Override
        public boolean hasFunctionClass(@Nonnull String name) {
            return program.funcs.containsKey(name);
        }

        @Nonnull
        @Override
        public IFunctionPrototype getFunctionClass(@Nonnull String name) {
            return program.funcs.get(name);
        }

        @Nonnull
        @Override
        public IProgram.Builder addFunctionClass(@Nonnull IFunctionPrototype function) {
            program.funcs.put(function.getName(), function);
            return this;
        }

        @Nonnull
        @Override
        public IProgram.Builder setMainClass(@Nonnull IFunctionPrototype mainClass) {
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

        private int count = 0;
        @Nonnull
        @Override
        public IFunctionPrototype.Builder createFunctionBuilder(@Nonnull final String name) {
            return new DummyFunctionPrototypeBuilder(name);
        }

        @Nonnull
        @Override
        public IFunctionPrototype.Builder createAnonymousFunctionBuilder() {
            return new DummyFunctionPrototypeBuilder("dummy" + count++);
        }

        @Nonnull
        @Override
        public IMethodPrototype.Builder createMethodBuilder(@Nonnull String name) {
            return new DummyMethodPrototypeBuilder(name);
        }
    }

    @Nonnull
    @Override
    public LangObject execute() {
        return LangUndefined.INSTANCE;
    }
}
