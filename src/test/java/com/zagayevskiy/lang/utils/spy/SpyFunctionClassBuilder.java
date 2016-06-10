package com.zagayevskiy.lang.utils.spy;

import com.zagayevskiy.lang.runtime.IVariable;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.types.classes.function.IFunctionClass;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class SpyFunctionClassBuilder implements IFunctionClass.Builder {
    private IFunctionClass.Builder inner;

    public List<SpyFunctionClass> spyFunctionClasses = new ArrayList<>();

    SpyFunctionClassBuilder(IFunctionClass.Builder inner) {
        this.inner = inner;
    }

    @Nonnull
    @Override
    public IVariable addArgument(@Nonnull String name) {
        return inner.addArgument(name);
    }

    @Override
    @Nonnull
    public IFunctionClass.Builder addInstruction(@Nonnull Instruction instruction) {
        return inner.addInstruction(instruction);
    }

    @Override
    @Nonnull
    public IFunctionClass.Builder putInstruction(@Nonnull Instruction instruction, int address) {
        return inner.putInstruction(instruction, address);
    }

    @Override
    @Nonnull
    public IFunctionClass.Builder removeLastInstruction() {
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

    @Override
    @Nonnull
    public SpyFunctionClass build() {
        SpyFunctionClass spy = new SpyFunctionClass(inner.build());
        spyFunctionClasses.add(spy);
        return spy;
    }
}
