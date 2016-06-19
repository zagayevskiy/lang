package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.instructions.AbsBinaryObjInstruction;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.base.LangObject;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;

import javax.annotation.Nonnull;

public class NotEqualsInstruction extends AbsBinaryObjInstruction {
    @Nonnull
    @Override
    protected Operand execute(@Nonnull LangObject left, @Nonnull LangObject right) {
        return LangBoolean.from(!left.equals(right));
    }

    @Override
    public String toString() {
        return "!=";
    }
}
