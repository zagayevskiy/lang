package com.zagayevskiy.lang.runtime.types.base;

import com.zagayevskiy.lang.runtime.IContext;
import com.zagayevskiy.lang.runtime.types.primitive.string.LangString;

import javax.annotation.Nonnull;

public abstract class AbsLangObject implements LangObject {

    @Nonnull
    public LangObject plus(@Nonnull LangObject other) {
        return LangString.from(toLangString().stringValue + other.toLangString().stringValue);
    }

    @Nonnull
    @Override
    public LangObject getValue(@Nonnull IContext function) {
        return this;
    }

    @Override
    public void execute(@Nonnull IContext context) {
        context.pushOperand(this);
    }

    @Nonnull
    @Override
    public LangClass getLangClass() {
        return BaseLangClass.INSTANCE;
    }
}
