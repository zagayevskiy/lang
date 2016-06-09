package com.zagayevskiy.lang.utils.spy;

import com.zagayevskiy.lang.runtime.IProgram;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class SpyProgramFactory implements IProgram.Factory {

    public Map<String, SpyFunctionClassBuilder> spyFuncsClazzBuilders = new HashMap<>();

    private final IProgram.Factory factory;

    public SpyProgramFactory(IProgram.Factory factory) {
        this.factory = factory;
    }

    @Nonnull
    @Override
    public SpyFunctionClassBuilder createFunctionBuilder(@Nonnull String name) {
        SpyFunctionClassBuilder spy = new SpyFunctionClassBuilder(factory.createFunctionBuilder(name));
        spyFuncsClazzBuilders.put(name, spy);
        return spy;
    }
}
