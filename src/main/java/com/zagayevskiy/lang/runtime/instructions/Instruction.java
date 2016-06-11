package com.zagayevskiy.lang.runtime.instructions;

import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.instructions.impl.*;

import javax.annotation.Nonnull;

public interface Instruction {
    void execute(@Nonnull IFunction function);

    Instruction ARRAY_DEREFERENCE = new ArrayDereferenceInstruction();
    Instruction ASSIGN = new AssignInstruction();
    Instruction BIT_AND = new BitAndInstruction();
    Instruction BIT_NOT = new BitNotInstruction();
    Instruction BIT_OR = new BitOrInstruction();
    Instruction BIT_XOR = new BitXorInstruction();
    Instruction BIT_SHIFT_LEFT = new BitShiftLeftInstruction();
    Instruction BIT_SHIFT_RIGHT = new BitShiftRightInstruction();
    Instruction CALL = new CallInstruction();
    Instruction DIVISION = new DivisionInstruction();
    Instruction EQUALS = new EqualsInstruction();
    Instruction JUMP = new JumpInstruction();
    Instruction JUMP_FALSE = new JumpFalseInstruction();
    Instruction LOGIC_AND = new LogicAndInstruction();
    Instruction LOGIC_NOT = new LogicNotInstruction();
    Instruction LOGIC_OR = new LogicOrInstruction();
    Instruction MINUS = new MinusInstruction();
    Instruction MOD = new ModInstruction();
    Instruction MULTIPLY = new MultiplyInstruction();
    Instruction NEW_ARRAY = new NewArrayInstruction();
    Instruction NEW_STRUCT_INSTANCE = new NewStructInstanceInstruction();
    Instruction NOP = new NopInstruction();
    Instruction NOT_EQUALS = new NotEqualsInstruction();
    Instruction PLUS = new PlusInstruction();
    Instruction POP = new PopInstruction();
    Instruction PROPERTY_DEREFERENCE = new PropertyDereference();
    Instruction UNARY_MINUS = new UnaryMinusInstruction();
}
