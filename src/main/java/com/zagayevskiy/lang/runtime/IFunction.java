package com.zagayevskiy.lang.runtime;

import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public interface IFunction {

    int ALREADY_EXISTS = -1;

    @Nonnull
    String getName();

    @Nonnull
    IFunction addInstruction(@Nonnull Instruction instruction);

    @Nonnull
    IFunction putInstruction(@Nonnull Instruction instruction, int address);

    @Nonnull
    IFunction removeLastInstruction();

    int getInstructionsCount();

    boolean hasVariable(@Nonnull String name);

    @Nonnull
    IVariable addVariable(@Nonnull String name);

    @Nonnull
    IVariable getVariable(int id);

    @Nonnull
    IVariable getVariable(@Nonnull String name);

    void pushOperand(@Nonnull Operand operand);

    @Nonnull
    Operand popOperand();

    @Nonnull
    LangObject execute();

    void jump(int position);
}
