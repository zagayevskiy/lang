package com.zagayevskiy.lang.runtime.instructions;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.IProgram;
import com.zagayevskiy.lang.runtime.operand.ConstOperand;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;

public class ConstInstruction extends ConstOperand implements Instruction{

    public ConstInstruction(@Nonnull LangObject value) {
        super(value);
    }

    @Override
    public void execute(@Nonnull IProgram program, @Nonnull IFunction function) {
        program.pushOperand(this);
    }

    @Override
    public String toString() {
        return "c:" + getValue().toString();
    }
}
