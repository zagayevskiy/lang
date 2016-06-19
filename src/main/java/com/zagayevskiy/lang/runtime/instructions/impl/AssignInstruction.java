package com.zagayevskiy.lang.runtime.instructions.impl;

import com.zagayevskiy.lang.runtime.IContext;
import com.zagayevskiy.lang.runtime.instructions.AbsBinaryInstruction;
import com.zagayevskiy.lang.runtime.operand.AssignableOperand;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.base.LangObject;

import javax.annotation.Nonnull;

public class AssignInstruction extends AbsBinaryInstruction {

    @Nonnull
    @Override
    protected final Operand execute(@Nonnull IContext context, @Nonnull Operand op1, @Nonnull Operand op2) {

        if (!(op1 instanceof AssignableOperand)) {
            throw new IllegalStateException("First operand of assign operator must implement " + AssignableOperand.class.getName());
        }

        return execute(context, (AssignableOperand) op1, op2.getValue(context));
    }

    @Nonnull
    private static Operand execute(@Nonnull IContext function, @Nonnull AssignableOperand op1, @Nonnull LangObject op2) {
        op1.setValue(function, op2);
        return op2;
    }

    @Override
    public String toString() {
        return "=";
    }
}
