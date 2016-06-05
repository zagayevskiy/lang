package com.zagayevskiy.lang.runtime;

import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public interface IProgram {

    interface Builder {

        boolean hasFunction(@Nonnull String name);

        @Nonnull
        Builder addFunction(@Nonnull IFunction function);

        @Nonnull
        IProgram build();
    }

    interface Factory {
        @Nonnull
        IFunction createFunction(@Nonnull String name);
    }

    void pushOperand(@Nonnull Operand operand);

    @Nonnull
    Operand popOperand();

    @Nonnull
    LangObject execute();
}
