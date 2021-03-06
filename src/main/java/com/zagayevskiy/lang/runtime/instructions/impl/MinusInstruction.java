package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.instructions.AbsBinaryIntInstruction;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;

import javax.annotation.Nonnull;

public class MinusInstruction extends AbsBinaryIntInstruction {

    @Nonnull
    @Override
    protected LangInteger execute(@Nonnull LangInteger left, @Nonnull LangInteger right) {
        return left.minus(right);
    }

    @Override
    public String toString() {
        return "-";
    }
}
