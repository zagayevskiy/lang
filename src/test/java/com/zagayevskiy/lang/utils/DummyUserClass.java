package com.zagayevskiy.lang.utils;

import com.zagayevskiy.lang.runtime.types.base.AbsLangObject;
import com.zagayevskiy.lang.runtime.types.function.prototype.IMethodPrototype;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.string.LangString;
import com.zagayevskiy.lang.runtime.userclass.IUserClass;
import com.zagayevskiy.lang.runtime.userclass.IUserClassPrototype;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DummyUserClass extends AbsLangObject implements IUserClassPrototype {

    private final String name;

    public DummyUserClass(String name) {
        this.name = name;
    }

    @Nullable
    @Override
    public IMethodPrototype getMethodPrototype(@Nonnull String name) {
        return null;
    }

    @Nonnull
    @Override
    public String getLangClassName() {
        return name;
    }

    @Nonnull
    @Override
    public LangBoolean toLangBoolean() {
        return LangBoolean.FALSE;
    }

    @Nonnull
    @Override
    public LangInteger toLangInteger() {
        return LangInteger.NaN;
    }

    @Nonnull
    @Override
    public LangString toLangString() {
        return LangString.from(toString());
    }

    @Override
    public String toString() {
        return "dummy$class$" + name;
    }

    @Nullable
    @Override
    public IUserClass.Constructor getConstructor(int argumentsCount) {
        return null;
    }
}
