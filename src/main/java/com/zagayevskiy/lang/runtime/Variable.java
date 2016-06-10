package com.zagayevskiy.lang.runtime;

import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.primitive.LangUndefined;

import javax.annotation.Nonnull;

public class Variable implements IVariable {

    private final int id;

    @Nonnull
    private LangObject value;

    public Variable(int id) {
        this(id, LangUndefined.INSTANCE);
    }

    public Variable(@Nonnull Variable other) {
        this(other.id, other.value);
    }

    public Variable(int id, @Nonnull LangObject value) {
        this.id = id;
        this.value = value;
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
