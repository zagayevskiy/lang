package com.zagayevskiy.lang.runtime;

import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public interface IFunction {

    @Nonnull
    String getName();

    @Nonnull
    IFunction addInstruction(@Nonnull Instruction instruction);

    @Nonnull
    IFunction removeLastInstruction();

    void pushOperand(@Nonnull Operand operand);

    @Nonnull
    Operand popOperand();

    @Nonnull
    LangObject execute();

    void jump(int position);
}
