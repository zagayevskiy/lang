package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.instructions.AbsBinaryIntInstruction;
import com.zagayevskiy.lang.runtime.types.LangInteger;

import javax.annotation.Nonnull;

public class BitShiftLeftInstruction extends AbsBinaryIntInstruction {

    @Override
    protected LangInteger execute(@Nonnull LangInteger left, @Nonnull LangInteger right) {
        return left.shiftLeft(right);
    }

    @Override
    public String toString() {
        return "<<";
    }
}
