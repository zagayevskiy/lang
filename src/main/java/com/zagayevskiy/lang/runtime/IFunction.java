package com.zagayevskiy.lang.runtime;

import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public interface IFunction {

    @Nonnull
    String getName();

    @Nonnull
    IFunction addInstruction(@Nonnull Instruction instruction);

    @Nonnull
    IFunction removeLastInstruction();

    void execute(@Nonnull IProgram program);
}
