package com.zagayevskiy.lang.runtime.types.classes.function;

import com.zagayevskiy.lang.runtime.IVariable;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.classes.LangClass;
import com.zagayevskiy.lang.runtime.types.function.IFunction;

import javax.annotation.Nonnull;
import java.util.List;

public interface IFunctionClass extends LangClass, LangObject {
    interface Builder {

        @Nonnull
        IFunctionClass getStub();

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
    }

    @Nonnull
    String getName();

    int getArgumentsCount();

    @Nonnull
    IFunction newInstance(@Nonnull List<LangObject> arguments);

    @Nonnull
    IFunctionClass applyPartially(@Nonnull List<LangObject> arguments);
}
