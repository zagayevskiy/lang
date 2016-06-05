package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.instructions.AbsBinaryInstruction;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.LangInteger;
import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.LangUndefined;

import javax.annotation.Nonnull;

public class PlusInstruction extends AbsBinaryInstruction {

    @Nonnull
    @Override
    protected Operand execute(@Nonnull LangObject left, @Nonnull LangObject right) {
        return left.plus(right);
    }

    @Override
    public String toString() {
        return "+";
    }
}
