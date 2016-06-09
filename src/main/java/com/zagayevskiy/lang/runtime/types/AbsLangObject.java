package com.zagayevskiy.lang.runtime.types;

import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.types.primitive.LangString;

import javax.annotation.Nonnull;

public abstract class AbsLangObject implements LangObject {

    @Nonnull
    public LangObject plus(@Nonnull LangObject other) {
        return LangString.from(toLangString().stringValue + other.toLangString().stringValue);
    }

    @Nonnull
    @Override
    public LangObject getValue(@Nonnull IFunction function) {
        return this;
    }

    @Override
    public void execute(@Nonnull IFunction function) {
        function.pushOperand(this);
    }
}
