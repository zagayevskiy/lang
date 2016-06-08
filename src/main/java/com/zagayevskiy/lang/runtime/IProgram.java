package com.zagayevskiy.lang.runtime;

import com.zagayevskiy.lang.runtime.types.classes.LangStructClass;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IProgram {

    interface Builder {

        boolean hasFunction(@Nonnull String name);

        @Nonnull
        Builder addFunction(@Nonnull IFunction function);

        @Nonnull
        Builder addStruct(@Nonnull LangStructClass struct);

        @Nullable
        LangStructClass getStruct(@Nonnull String name);

        @Nonnull
        IProgram build();
    }

    interface Factory {
        @Nonnull
        IFunction createFunction(@Nonnull String name);
    }

    @Nonnull
    LangObject execute();
}
