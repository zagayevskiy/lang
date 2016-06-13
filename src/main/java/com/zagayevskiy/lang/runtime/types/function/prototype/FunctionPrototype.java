package com.zagayevskiy.lang.runtime.types.function.prototype;

import com.zagayevskiy.lang.runtime.Variable;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.types.IContext;
import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.function.Function;
import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.LangString;
import com.zagayevskiy.lang.runtime.types.primitive.LangUndefined;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FunctionPrototype implements IFunctionPrototype {

    @Nonnull
    private final String name;
    @Nonnull
    private final List<Variable> variables;
    private final int firstArgumentIndex;
    private final int argumentsCount;
    @Nonnull
    private final List<Instruction> instructions;

    FunctionPrototype(@Nonnull String name, @Nonnull List<Variable> variables, int argumentsCount, @Nonnull List<Instruction> instructions) {
        this(name, variables, 0, argumentsCount, instructions);
    }

    private FunctionPrototype(@Nonnull String name, @Nonnull List<Variable> variables, int firstArgumentIndex, int argumentsCount, @Nonnull List<Instruction> instructions) {
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

    @Nonnull
    @Override
    public IFunctionPrototype applyPartially(@Nonnull List<LangObject> arguments) {
        if (argumentsCount <= arguments.size()) {
            throw new IllegalArgumentException("arguments count must be less than getArgumentsCount() to apply function partially");
        }
        return new FunctionPrototype(name + "$partially",
                bindArguments(arguments),
                firstArgumentIndex + arguments.size(),
                argumentsCount - arguments.size(),
                instructions);
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
    public LangObject getValue(@Nonnull IContext function) {
        return this;
    }

    @Override
    public void execute(@Nonnull IContext context) {
        context.pushOperand(this);
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
        return getLangClassName();
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
