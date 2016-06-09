package com.zagayevskiy.lang.runtime;

import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.classes.LangStructClass;
import com.zagayevskiy.lang.runtime.types.classes.function.IFunctionClass;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IProgram {

    interface Builder {

        boolean hasFunctionClass(@Nonnull String name);

        @Nonnull
        Builder addFunctionClass(@Nonnull IFunctionClass functionClass);

        @Nonnull
        Builder addStruct(@Nonnull LangStructClass struct);

        @Nonnull
        Builder setMainClass(@Nonnull IFunctionClass mainClass);

        @Nullable
        LangStructClass getStruct(@Nonnull String name);

        @Nonnull
        IProgram build();
    }

    interface Factory {
        @Nonnull
        IFunctionClass.Builder createFunctionBuilder(@Nonnull String name);
    }

    @Nonnull
    LangObject execute();
}
