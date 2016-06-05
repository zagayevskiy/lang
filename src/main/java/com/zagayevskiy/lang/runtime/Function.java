package com.zagayevskiy.lang.runtime;

import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class Function implements IFunction {

    private final String name;
    private ArrayList<Instruction> instructions = new ArrayList<>();
    private int instructionPointer;

    public Function(@Nonnull String name) {
        this.name = name;
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Nonnull
    @Override
    public IFunction addInstruction(@Nonnull Instruction instruction) {
        instructions.add(instruction);
        return this;
    }

    @Nonnull
    @Override
    public IFunction removeLastInstruction() {
        instructions.remove(instructions.size() - 1);
        return this;
    }

    @Override
    public void execute(@Nonnull IProgram program) {
        instructionPointer = 0;
        while (instructionPointer < instructions.size()) {
            Instruction instruction = instructions.get(instructionPointer);
            ++instructionPointer;
            instruction.execute(program, this);
        }
    }
}
