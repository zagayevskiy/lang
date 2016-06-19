package com.zagayevskiy.lang.runtime.types.base;

import com.zagayevskiy.lang.runtime.types.base.methods.impl.Methods;
import com.zagayevskiy.lang.runtime.types.function.prototype.IMethodPrototype;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class BaseLangClass implements LangClass {

    public static final BaseLangClass INSTANCE = new BaseLangClass();

    private Map<String, IMethodPrototype> methodPrototypes = new HashMap<>();

    protected BaseLangClass() {
        registerAllMethods(Methods.DEFAULT_METHODS);
    }

    @Override
    @Nullable
    public final IMethodPrototype getMethodPrototype(@Nonnull String name) {
        return methodPrototypes.get(name);
    }

    protected final void registerMethod(@Nonnull IMethodPrototype methodPrototype) {
        methodPrototypes.put(methodPrototype.getName(), methodPrototype);
    }

    protected final void registerAllMethods(@Nonnull IMethodPrototype... prototypes) {
        for (IMethodPrototype prototype : prototypes) {
            registerMethod(prototype);
        }
    }

    @Nonnull
    @Override
    public String getLangClassName() {
        return BaseLangClass.class.getSimpleName();
    }
}