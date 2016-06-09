package com.zagayevskiy.lang.runtime.instructions;

import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public abstract class AbsBinaryObjInstruction extends AbsBinaryInstruction {

    @Nonnull
    @Override
    protected final Operand execute(@Nonnull IFunction function, @Nonnull Operand op1, @Nonnull Operand op2) {
        return execute(op1.getValue(function), op2.getValue(function));
    }

    @Nonnull
    protected abstract Operand execute(@Nonnull LangObject left, @Nonnull LangObject right);
}
