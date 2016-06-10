package com.zagayevskiy.lang.runtime.types.classes.function;

import com.zagayevskiy.lang.runtime.Variable;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.function.Function;
import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.LangString;
import com.zagayevskiy.lang.runtime.types.primitive.LangUndefined;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FunctionClass implements IFunctionClass {

    @Nonnull
    private final String name;
    @Nonnull
    private final List<Variable> variables;
    private final int firstArgumentIndex;
    private final int argumentsCount;
    @Nonnull
    private final List<Instruction> instructions;

    FunctionClass(@Nonnull String name, @Nonnull List<Variable> variables, int argumentsCount, @Nonnull List<Instruction> instructions) {
        this(name, variables, 0, argumentsCount, instructions);
    }

    private FunctionClass(@Nonnull String name, @Nonnull List<Variable> variables, int firstArgumentIndex, int argumentsCount, @Nonnull List<Instruction> instructions) {
        if (firstArgumentIndex < 0) {
            throw new IllegalArgumentException("firstArgumentIndex must be >= 0");
        }
        if (argumentsCount < 0) {
            throw new IllegalArgumentException("argumentsCount must be >= 0");
        }
        if (firstArgumentIndex + argumentsCount > variables.size()) {
            throw new IllegalArgumentException("firstArgumentIndex + argumentsCount must be <= variables.size()");
        }

        this.name = name;
        this.variables = variables;
        this.firstArgumentIndex = firstArgumentIndex;
        this.argumentsCount = argumentsCount;
        this.instructions = instructions;
    }

    @Nonnull
    @Override
    public IFunction newInstance(@Nonnull List<LangObject> arguments) {
        if (argumentsCount != arguments.size()) {
            throw new IllegalArgumentException("arguments count must be equals to getArgumentsCount() to create new IFunction instance."
                    + " Want " + argumentsCount + ","
                    + " but has " + arguments.size());
        }
        return new Function(name, instructions, bindArguments(arguments));
    }

    @Override
    @Nonnull
    public String getName() {
        return name;
    }

    @Override
    public int getArgumentsCount() {
        return argumentsCount;
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
        return "function$" + name + "$" + argumentsCount;
    }

    @Nonnull
    private List<Variable> bindArguments(@Nonnull List<LangObject> arguments) {
        if (arguments.size() > variables.size() - firstArgumentIndex) {
            throw new IllegalArgumentException("Too many arguments. "
                    + "Want " + (variables.size() - firstArgumentIndex) + ", "
                    + "but has " + arguments.size());
        }

        final Variable[] result = new Variable[variables.size()];

        //apply already bound
        int i = 0;
        for (Variable bound: variables) {
            if (i >= firstArgumentIndex) {
                break;
            }
            result[i++] = new Variable(bound);
        }

        //bind new arguments
        for (LangObject argument: arguments) {
            if (argument == null) {
                throw new IllegalArgumentException("Arguments must not be null.");
            }
            result[i] = new Variable(i, argument);
            ++i;
        }

        for (; i < variables.size(); ++i) {
            result[i] = new Variable(i);
        }


        return Collections.unmodifiableList(Arrays.asList(result));
    }
}
