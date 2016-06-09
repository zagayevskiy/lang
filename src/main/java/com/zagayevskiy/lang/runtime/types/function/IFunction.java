package com.zagayevskiy.lang.runtime.types.function;

import com.zagayevskiy.lang.runtime.IVariable;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public interface IFunction extends LangObject {

    @Nonnull
    String getName();

    @Nonnull
    IVariable getVariable(int id);

    void pushOperand(@Nonnull Operand operand);

    @Nonnull
    Operand popOperand();

    @Nonnull
    LangObject execute();

    void jump(int position);
}
