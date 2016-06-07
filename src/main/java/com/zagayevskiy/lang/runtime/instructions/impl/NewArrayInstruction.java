package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.instructions.AbsMultipleArgsInstruction;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.LangArray;

import javax.annotation.Nonnull;

public class NewArrayInstruction extends AbsMultipleArgsInstruction {

    @Nonnull
    @Override
    protected Operand execute(@Nonnull IFunction function, int argsCount) {

        final LangArray array = new LangArray(argsCount);

        for (int i = argsCount - 1; i >= 0; --i) {
            array.set(i, function.popOperand().getValue(function));
        }

        return array;
    }

    @Override
    public String toString() {
        return "new[]";
    }
}
