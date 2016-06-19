package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.instructions.AbsUnaryObjInstruction;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.base.LangObject;

import javax.annotation.Nonnull;

public class BitNotInstruction extends AbsUnaryObjInstruction {
    @Nonnull
    @Override
    protected Operand execute(@Nonnull LangObject operand) {
        return operand.toLangInteger().bitNot();
    }

    @Override
    public String toString() {
        return "~";
    }
}
