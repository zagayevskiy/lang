package com.zagayevskiy.lang.utils;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.IVariable;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.LangUndefined;

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
    public boolean hasVariable(@Nonnull String name) {
        return false;
    }

    @Nonnull
    @Override
    public IVariable addVariable(@Nonnull String name) {
        return new DummyVariable();
    }

    @Nonnull
    @Override
    public IVariable getVariable(int id) {
        return new DummyVariable();
    }

    @Nonnull
    @Override
    public IVariable getVariable(@Nonnull String name) {
        return new DummyVariable();
    }

    @Override
    public void pushOperand(@Nonnull Operand operand) {
    }

    @Nonnull
    @Override
    public Operand popOperand() {
        return LangUndefined.INSTANCE;
    }

    @Nonnull
    @Override
    public LangObject execute() {
        return LangUndefined.INSTANCE;
    }

    @Override
    public void jump(int position) {
    }
}
