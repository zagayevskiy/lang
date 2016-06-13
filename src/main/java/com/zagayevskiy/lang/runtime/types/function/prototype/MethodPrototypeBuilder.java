package com.zagayevskiy.lang.runtime.types.function.prototype;

import com.zagayevskiy.lang.runtime.IVariable;
import com.zagayevskiy.lang.runtime.Variable;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.tokenization.Token;

import javax.annotation.Nonnull;
import java.util.List;

public class MethodPrototypeBuilder extends FunctionPrototypeBuilder implements IMethodPrototype.Builder {
    public MethodPrototypeBuilder(@Nonnull String name) {
        super(name);
        addArgument(Token.SELF_NAME);
    }

    @Nonnull
    @Override
    public IMethodPrototype getStub() {
        return (IMethodPrototype) super.getStub();
    }

    @Nonnull
    @Override
    protected IFunctionPrototype createStub(@Nonnull String name, @Nonnull List<Variable> variables, int argumentsCount, @Nonnull List<Instruction> instructions) {
        return new MethodPrototype(name, variables, argumentsCount, instructions);
    }
}
