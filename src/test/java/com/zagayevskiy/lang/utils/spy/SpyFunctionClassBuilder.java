package com.zagayevskiy.lang.utils.spy;

import com.zagayevskiy.lang.runtime.IVariable;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.types.function.prototype.IFunctionPrototype;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class SpyFunctionClassBuilder implements IFunctionPrototype.Builder {
    private IFunctionPrototype.Builder inner;

    public List<SpyFunctionPrototype> spyFunctionClasses = new ArrayList<>();

    SpyFunctionClassBuilder(IFunctionPrototype.Builder inner) {
        this.inner = inner;
    }

    @Nonnull
    @Override
    public IVariable addArgument(@Nonnull String name) {
        return inner.addArgument(name);
    }

    @Override
    @Nonnull
    public IFunctionPrototype.Builder addInstruction(@Nonnull Instruction instruction) {
        return inner.addInstruction(instruction);
    }

    @Override
    @Nonnull
    public IFunctionPrototype.Builder putInstruction(@Nonnull Instruction instruction, int address) {
        return inner.putInstruction(instruction, address);
    }

    @Override
    @Nonnull
    public IFunctionPrototype.Builder removeLastInstruction() {
        return inner.removeLastInstruction();
    }

    @Override
    public int getInstructionsCount() {
        return inner.getInstructionsCount();
    }

    @Override
    public boolean hasVariable(@Nonnull String name) {
        return inner.hasVariable(name);
    }

    @Override
    @Nonnull
    public IVariable addVariable(@Nonnull String name) {
        return inner.addVariable(name);
    }

    @Override
    @Nonnull
    public IVariable getVariable(@Nonnull String name) {
        return inner.getVariable(name);
    }

    @Override
    @Nonnull
    public IVariable getVariable(int index) {
        return inner.getVariable(index);
    }

    @Nonnull
    @Override
    public IFunctionPrototype getStub() {
        SpyFunctionPrototype spy = new SpyFunctionPrototype(inner.getStub());
        spyFunctionClasses.add(spy);
        return spy;
    }
}
