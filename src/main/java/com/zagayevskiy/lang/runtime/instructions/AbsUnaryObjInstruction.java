package com.zagayevskiy.lang.runtime.instructions;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public abstract class AbsUnaryObjInstruction extends AbsUnaryInstruction {

    @Nonnull
    @Override
    protected final Operand execute(@Nonnull IFunction function, @Nonnull Operand operand) {
        return execute(operand.getValue(function));
    }

    @Nonnull
    protected abstract Operand execute(@Nonnull LangObject operand);


}
