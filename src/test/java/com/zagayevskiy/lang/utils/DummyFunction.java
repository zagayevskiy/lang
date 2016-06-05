package com.zagayevskiy.lang.utils;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.IProgram;
import com.zagayevskiy.lang.runtime.instructions.Instruction;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class DummyFunction implements IFunction {

    private final String name;
    public final List<Instruction> instructions = new ArrayList<>();

    public DummyFunction(String name) {
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
    }
}
