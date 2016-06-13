package com.zagayevskiy.lang.runtime;

import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.classes.LangStructClass;
import com.zagayevskiy.lang.runtime.types.function.prototype.IFunctionPrototype;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IProgram {

    interface Builder {
        @Nonnull
        Builder addStruct(@Nonnull LangStructClass struct);

        @Nonnull
        Builder addFunctionClass(@Nonnull IFunctionPrototype functionClass);

        @Nullable
        LangStructClass getStruct(@Nonnull String name);

        @Nonnull
        IFunctionPrototype getFunctionClass(@Nonnull String name);

        boolean hasFunctionClass(@Nonnull String name);

        @Nonnull
        Builder setMainClass(@Nonnull IFunctionPrototype mainClass);

        @Nonnull
        IProgram build();
    }

    interface Factory {
        @Nonnull
        IFunctionPrototype.Builder createAnonymousFunctionBuilder();

        @Nonnull
        IFunctionPrototype.Builder createFunctionBuilder(@Nonnull String name);
    }

    @Nonnull
    LangObject execute();
}
