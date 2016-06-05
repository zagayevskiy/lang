package com.zagayevskiy.lang.runtime.instructions;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.IProgram;

import javax.annotation.Nonnull;

public interface Instruction {
    void execute(@Nonnull IProgram program, @Nonnull IFunction function);
}
