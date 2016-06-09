package com.zagayevskiy.lang.runtime.types.classes.function;

import com.zagayevskiy.lang.runtime.IVariable;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.classes.LangClass;
import com.zagayevskiy.lang.runtime.types.function.IFunction;

import javax.annotation.Nonnull;

public interface IFunctionClass extends LangClass, LangObject {
    interface Builder {

        @Nonnull
        Builder addInstruction(@Nonnull Instruction instruction);

        @Nonnull
        Builder putInstruction(@Nonnull Instruction instruction, int address);

        @Nonnull
        Builder removeLastInstruction();

        int getInstructionsCount();

        boolean hasVariable(@Nonnull String name);

        @Nonnull
        IVariable addVariable(@Nonnull String name);

        @Nonnull
        IVariable getVariable(@Nonnull String name);

        @Nonnull
        IVariable getVariable(int index);

        @Nonnull
        IVariable addArgument(@Nonnull String name);

        @Nonnull
        IFunctionClass build();
    }

    interface InstanceBuilder {
        @Nonnull
        InstanceBuilder withArgument(@Nonnull LangObject argument);
        IFunction build();
    }

    @Nonnull
    String getName();

    @Nonnull
    InstanceBuilder newInstanceBuilder(int argsCount);
}
