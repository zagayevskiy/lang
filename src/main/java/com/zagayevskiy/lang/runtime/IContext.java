package com.zagayevskiy.lang.runtime;

import com.zagayevskiy.lang.logging.RuntimeLogger;
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

    @Nonnull
    RuntimeLogger getRuntimeLogger();
}
