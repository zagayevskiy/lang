package com.zagayevskiy.lang.runtime.types.function.prototype;

import com.zagayevskiy.lang.runtime.IVariable;
import com.zagayevskiy.lang.runtime.Variable;
import com.zagayevskiy.lang.runtime.instructions.Instruction;

import javax.annotation.Nonnull;
import java.util.*;

public class FunctionPrototypeBuilder implements IFunctionPrototype.Builder {

    private enum State {
        BUILD_ARGUMENTS, BUILD_BODY
    }

    private IFunctionPrototype functionPrototype;

    private final String name;
    private final List<Instruction> instructions = new ArrayList<>();

    private final List<Variable> variables = new ArrayList<>();
    private final Map<String, Variable> variablesByName = new HashMap<>();

    private State state = State.BUILD_ARGUMENTS;
    private int argumentsCount = 0;

    public FunctionPrototypeBuilder(@Nonnull String name) {
        this.name = name;
    }

    @Nonnull
    @Override
    public IFunctionPrototype getStub() {
        if (functionPrototype == null) {
            functionPrototype = new FunctionPrototype(name,
                    Collections.unmodifiableList(variables),
                    argumentsCount,
                    Collections.unmodifiableList(instructions));
        }
        state = State.BUILD_BODY;
        return functionPrototype;
    }

    @Nonnull
    @Override
    public IFunctionPrototype.Builder addInstruction(@Nonnull Instruction instruction) {
        state = State.BUILD_BODY;
        instructions.add(instruction);
        return this;
    }

    @Nonnull
    @Override
    public IFunctionPrototype.Builder putInstruction(@Nonnull Instruction instruction, int address) {
        state = State.BUILD_BODY;
        instructions.set(address, instruction);
        return this;
    }

    @Override
    public int getInstructionsCount() {
        return instructions.size();
    }

    @Nonnull
    @Override
    public IFunctionPrototype.Builder removeLastInstruction() {
        state = State.BUILD_BODY;
        instructions.remove(instructions.size() - 1);
        return this;
    }

    @Override
    public boolean hasVariable(@Nonnull String name) {
        return variablesByName.containsKey(name);
    }

    @Nonnull
    @Override
    public Variable addVariable(@Nonnull String name) {
        if (hasVariable(name)) {
            throw new IllegalStateException("Variable " + name + " already defined. Must check hasVariable before.");
        }
        state = State.BUILD_BODY;

        return doAddVariable(name);
    }

    @Nonnull
    @Override
    public IVariable addArgument(@Nonnull String name) {
        if (state != State.BUILD_ARGUMENTS) {
            throw new IllegalStateException("Can not add argument after body building begin");
        }
        if (hasVariable(name)) {
            throw new IllegalStateException("Argument " + name + "already defined must check hasVariable before.");
        }

        ++argumentsCount;
        return doAddVariable(name);
    }

    @Nonnull
    @Override
    public IVariable getVariable(int id) {
        return variables.get(id);
    }

    @Nonnull
    @Override
    public IVariable getVariable(@Nonnull String name) {
        if (!hasVariable(name)) {
            throw new IllegalStateException("Variable " + name + "not defined. Must check hasVariable before.");
        }

        return variablesByName.get(name);
    }

    @Nonnull
    private Variable doAddVariable(@Nonnull String name) {
        final int id = variables.size();
        final Variable variable = new Variable(id);
        variables.add(variable);
        variablesByName.put(name, variable);
        return variable;
    }
}