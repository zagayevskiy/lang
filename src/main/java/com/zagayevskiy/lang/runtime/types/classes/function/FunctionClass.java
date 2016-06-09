package com.zagayevskiy.lang.runtime.types.classes.function;

import com.zagayevskiy.lang.runtime.types.function.Function;
import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.Variable;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.types.*;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.LangString;
import com.zagayevskiy.lang.runtime.types.primitive.LangUndefined;

import javax.annotation.Nonnull;
import java.util.*;

public class FunctionClass implements IFunctionClass {

    @Nonnull
    private final String name;
    @Nonnull
    private final ArrayList<Variable> arguments;
    @Nonnull
    private final ArrayList<Variable> variables;
    @Nonnull
    private final ArrayList<Instruction> instructions;

    FunctionClass(@Nonnull String name, @Nonnull ArrayList<Variable> arguments, @Nonnull ArrayList<Instruction> instructions, @Nonnull ArrayList<Variable> variables) {
        this.name = name;
        this.arguments = arguments;
        this.variables = variables;
        this.instructions = instructions;
    }

    @Nonnull
    @Override
    public IFunctionClass.InstanceBuilder newInstanceBuilder(int argsCount) {
        return new InstanceBuilder() {
            @Nonnull
            @Override
            public InstanceBuilder withArgument(@Nonnull LangObject argument) {
                return this;
            }

            @Override
            public IFunction build() {
                return new Function(name, instructions, variables);
            }
        };
    }

    @Override
    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    @Override
    public LangObject plus(@Nonnull LangObject other) {
        return LangUndefined.INSTANCE;
    }

    @Nonnull
    @Override
    public LangObject getValue(@Nonnull IFunction function) {
        return this;
    }

    @Override
    public void execute(@Nonnull IFunction function) {
        function.pushOperand(this);
    }

    @Nonnull
    @Override
    public LangBoolean toLangBoolean() {
        return LangBoolean.FALSE;
    }

    @Nonnull
    @Override
    public LangInteger toLangInteger() {
        return LangInteger.NaN;
    }

    @Nonnull
    @Override
    public LangString toLangString() {
        return LangString.from(toString());
    }

    @Override
    public String toString() {
        return "func";
    }

    @Nonnull
    @Override
    public String getLangClassName() {
        return "func TODO";
    }
}
