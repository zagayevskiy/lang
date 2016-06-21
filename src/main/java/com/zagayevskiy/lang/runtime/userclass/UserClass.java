package com.zagayevskiy.lang.runtime.userclass;

import com.zagayevskiy.lang.runtime.types.base.AbsLangObject;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.string.LangString;

import javax.annotation.Nonnull;

public class UserClass extends AbsLangObject implements IUserClass {

    @Nonnull
    private final IUserClassPrototype userClassPrototype;

    public UserClass(@Nonnull IUserClassPrototype userClassPrototype) {
        this.userClassPrototype = userClassPrototype;
    }

    @Nonnull
    @Override
    public IUserClassPrototype getLangClass() {
        return userClassPrototype;
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
        return getLangClass().toString() + "$instance";
    }
}
