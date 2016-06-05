package com.zagayevskiy.lang.runtime.types;

import javax.annotation.Nonnull;

public abstract class LangObject {

    @Nonnull
    public abstract LangString toLangString();

    @Nonnull
    public abstract LangInteger toLangInteger();
}
