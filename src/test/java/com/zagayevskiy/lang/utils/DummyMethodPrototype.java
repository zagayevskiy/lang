package com.zagayevskiy.lang.utils;

import com.zagayevskiy.lang.runtime.types.function.prototype.IMethodPrototype;

import javax.annotation.Nonnull;

class DummyMethodPrototype extends DummyFunctionPrototype implements IMethodPrototype {
    DummyMethodPrototype(@Nonnull String name) {
        super(name);
    }

    @Override
    public boolean isSelfBound() {
        return false;
    }
}
