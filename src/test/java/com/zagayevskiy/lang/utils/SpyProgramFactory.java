package com.zagayevskiy.lang.utils;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.IProgram;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpyProgramFactory implements IProgram.Factory {

    public Map<String, IFunction> funcs = new HashMap<>();

    private final IProgram.Factory factory;

    public SpyProgramFactory(IProgram.Factory factory) {
        this.factory = factory;
    }

    @Nonnull
    @Override
    public IFunction createFunction(@Nonnull String name) {
        IFunction function = factory.createFunction(name);
        funcs.put(name, function);
        return function;
    }
}
