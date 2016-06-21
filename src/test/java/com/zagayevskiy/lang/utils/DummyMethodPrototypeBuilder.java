package com.zagayevskiy.lang.utils;

import com.zagayevskiy.lang.runtime.types.function.prototype.IMethodPrototype;
import com.zagayevskiy.lang.tokenization.Token;

import javax.annotation.Nonnull;

public class DummyMethodPrototypeBuilder extends DummyFunctionPrototypeBuilder implements IMethodPrototype.Builder {
    public DummyMethodPrototypeBuilder(@Nonnull String name) {
        super(name);
        addArgument(Token.SELF_NAME);
    }

    @Nonnull
    @Override
    public IMethodPrototype getStub() {
        return new DummyMethodPrototype(name);
    }
}
