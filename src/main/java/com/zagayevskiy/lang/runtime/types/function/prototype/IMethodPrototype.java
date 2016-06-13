package com.zagayevskiy.lang.runtime.types.function.prototype;

import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public interface IMethodPrototype extends IFunctionPrototype {

    interface Builder extends IFunctionPrototype.Builder {

        @Nonnull
        @Override
        IMethodPrototype getStub();
    }

    boolean isSelfBound();
}
