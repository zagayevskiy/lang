package com.zagayevskiy.lang.runtime.instructions;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.instructions.impl.*;

import javax.annotation.Nonnull;

public interface Instruction {
    void execute(@Nonnull IFunction function);

    Instruction ASSIGN = new AssignInstruction();
    Instruction BIT_SHIFT_LEFT = new BitShiftLeftInstruction();
    Instruction JUMP = new JumpInstruction();
    Instruction JUMP_FALSE = new JumpFalseInstruction();
    Instruction LOGIC_AND = new LogicAndInstruction();
    Instruction LOGIC_NOT = new LogicNotInstruction();
    Instruction LOGIC_OR = new LogicOrInstruction();
    Instruction MINUS = new MinusInstruction();
    Instruction MULTIPLY = new MultiplyInstruction();
    Instruction NOP = new NopInstruction();
    Instruction PLUS = new PlusInstruction();
    Instruction POP = new PopInstruction();
}
