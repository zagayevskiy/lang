package com.zagayevskiy.lang.utils;

import com.zagayevskiy.lang.runtime.IVariable;
import com.zagayevskiy.lang.runtime.Variable;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.types.function.prototype.IFunctionPrototype;

import javax.annotation.Nonnull;
import java.util.ArrayList;

class DummyFunctionPrototypeBuilder implements IFunctionPrototype.Builder {

    private ArrayList<String> vars = new ArrayList<>();
    @Nonnull
    final String name;

    DummyFunctionPrototypeBuilder(@Nonnull String name) {
        this.name = name;
    }

    @Nonnull
    @Override
    public IVariable addArgument(@Nonnull String name) {
        return addVariable(name);
    }

    @Nonnull
    @Override
    public IFunctionPrototype.Builder addInstruction(@Nonnull Instruction instruction) {
        return this;
    }

    @Nonnull
    @Override
    public IFunctionPrototype.Builder putInstruction(@Nonnull Instruction instruction, int address) {
        return this;
    }

    @Nonnull
    @Override
    public IFunctionPrototype.Builder removeLastInstruction() {
        return this;
    }

    @Override
    public int getInstructionsCount() {
        return 0;
    }

    @Override
    public boolean hasVariable(@Nonnull String name) {
        return vars.contains(name);
    }

    @Nonnull
    @Override
    public IVariable addVariable(@Nonnull String name) {
        vars.add(name);
        return new Variable(vars.indexOf(name));
    }

    @Nonnull
    @Override
    public IVariable getVariable(@Nonnull String name) {
        return new Variable(vars.indexOf(name));
    }

    @Nonnull
    @Override
    public IVariable getVariable(int index) {
        return new Variable(index);
    }

    @Nonnull
    @Override
    public IFunctionPrototype getStub() {
        return new DummyFunctionPrototype(name);
    }
}
