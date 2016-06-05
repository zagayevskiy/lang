package com.zagayevskiy.lang.runtime;

import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.LangUndefined;

import javax.annotation.Nonnull;

public class Variable implements IVariable {

    private final int id;

    @Nonnull
    private LangObject value = LangUndefined.INSTANCE;

    public Variable(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Nonnull
    @Override
    public LangObject getValue() {
        return value;
    }

    @Override
    public void setValue(@Nonnull LangObject value) {
        this.value = value;
    }
}
