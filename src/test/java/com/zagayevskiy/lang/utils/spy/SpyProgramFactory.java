package com.zagayevskiy.lang.utils.spy;

import com.zagayevskiy.lang.runtime.IProgram;
import com.zagayevskiy.lang.runtime.types.classes.function.IFunctionClass;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class SpyProgramFactory implements IProgram.Factory {

    public Map<String, SpyFunctionClassBuilder> spyFuncsClazzBuilders = new HashMap<>();

    private final IProgram.Factory factory;
    private  int count = 0;

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

    @Nonnull
    @Override
    public IFunctionClass.Builder createAnonymousFunctionBuilder() {
        SpyFunctionClassBuilder spy = new SpyFunctionClassBuilder(factory.createAnonymousFunctionBuilder());
        spyFuncsClazzBuilders.put("spy#" + count++, spy);
        return spy;
    }
}
