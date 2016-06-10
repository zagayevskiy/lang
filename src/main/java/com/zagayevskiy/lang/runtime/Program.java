package com.zagayevskiy.lang.runtime;

import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.classes.LangStructClass;
import com.zagayevskiy.lang.runtime.types.classes.function.FunctionClassBuilder;
import com.zagayevskiy.lang.runtime.types.classes.function.IFunctionClass;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Program implements IProgram {

    public static class Builder implements IProgram.Builder {

        private Program program = new Program();

        @Nonnull
        @Override
        public IProgram.Builder addFunctionClass(@Nonnull IFunctionClass functionClass) {
            program.functionClasses.put(functionClass.getName(), functionClass);
            return this;
        }

        @Nonnull
        @Override
        public IFunctionClass getFunctionClass(@Nonnull String name) {
            if (!hasFunctionClass(name)) {
                throw new IllegalStateException("Function " + name + " not defined. Must check hasFunctionClass() before");
            }

            return program.functionClasses.get(name);
        }

        @Override
        public boolean hasFunctionClass(@Nonnull String name) {
            return program.functionClasses.containsKey(name);
        }

        @Nonnull
        @Override
        public IProgram.Builder setMainClass(@Nonnull IFunctionClass mainClass) {
            program.mainClass = mainClass;
            program.functionClasses.put(mainClass.getName(), mainClass);
            return this;
        }

        @Nonnull
        @Override
        public IProgram.Builder addStruct(@Nonnull LangStructClass struct) {
            program.structClasses.put(struct.getName(), struct);
            return this;
        }

        @Nullable
        @Override
        public LangStructClass getStruct(@Nonnull String name) {
            return program.structClasses.get(name);
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
        public IFunctionClass.Builder createFunctionBuilder(@Nonnull String name) {
            return new FunctionClassBuilder(name);
        }
    }

    private Map<String, IFunctionClass> functionClasses = new HashMap<>();
    private Map<String, LangStructClass> structClasses = new HashMap<>();
    private IFunctionClass mainClass;

    @Nonnull
    @Override
    public LangObject execute() {
        return mainClass.newInstance(Collections.<LangObject>emptyList()).execute();
    }
}
