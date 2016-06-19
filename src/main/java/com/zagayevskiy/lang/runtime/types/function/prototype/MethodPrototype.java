package com.zagayevskiy.lang.runtime.types.function.prototype;

import com.zagayevskiy.lang.runtime.Variable;
import com.zagayevskiy.lang.runtime.instructions.Instruction;

import javax.annotation.Nonnull;
import java.util.List;

class MethodPrototype extends FunctionPrototype implements IMethodPrototype {
    MethodPrototype(@Nonnull String name,
                    @Nonnull List<Variable> variables,
                    int argumentsCount,
                    @Nonnull List<Instruction> instructions) {
        super(name, variables, argumentsCount, instructions);
    }

    @Override
    public boolean isSelfBound() {
        return false;
    }
}
