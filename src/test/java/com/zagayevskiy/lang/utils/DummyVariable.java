package com.zagayevskiy.lang.utils;

import com.zagayevskiy.lang.runtime.IVariable;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public class DummyVariable implements IVariable {
    @Override
    public int getId() {
        return 0;
    }

    @Nonnull
    @Override
    public LangObject getValue() {
        return new DummyLangObject();
    }

    @Override
    public void setValue(@Nonnull LangObject value) {

    }
}
