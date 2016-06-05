package com.zagayevskiy.lang.runtime;

import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public interface IVariable {

    int getId();

    @Nonnull
    LangObject getValue();

    void setValue(@Nonnull LangObject value);
}
