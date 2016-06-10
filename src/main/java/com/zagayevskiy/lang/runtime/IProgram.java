package com.zagayevskiy.lang.runtime;

import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.classes.LangStructClass;
import com.zagayevskiy.lang.runtime.types.classes.function.IFunctionClass;
import com.zagayevskiy.lang.runtime.types.function.IFunction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IProgram {

    interface Builder {

        @Nonnull
        Builder addFunctionClass(@Nonnull IFunctionClass functionClass);

        @Nonnull
        IFunctionClass getFunctionClass(@Nonnull String name);

        boolean hasFunctionClass(@Nonnull String name);


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
