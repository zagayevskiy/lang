package com.zagayevskiy.lang.runtime.instructions;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.instructions.impl.*;

import javax.annotation.Nonnull;

public interface Instruction {
    void execute(@Nonnull IFunction function);

    Instruction ASSIGN = new AssignInstruction();
    Instruction BIT_SHIFT_LEFT = new BitShiftLeftInstruction();
    Instruction MINUS = new MinusInstruction();
    Instruction MULTIPLY = new MultiplyInstruction();
    Instruction NOP = new NopInstruction();
    Instruction PLUS = new PlusInstruction();
    Instruction POP = new PopInstruction();
}
