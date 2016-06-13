package com.zagayevskiy.lang.runtime.types;

import com.zagayevskiy.lang.runtime.IVariable;
import com.zagayevskiy.lang.runtime.operand.Operand;

import javax.annotation.Nonnull;

public interface IContext {

    void doReturn();

    @Nonnull
    IVariable getVariable(int id);

    void jump(int position);

    @Nonnull
    Operand popOperand();
    void pushOperand(@Nonnull Operand operand);
}