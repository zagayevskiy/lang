package com.zagayevskiy.lang.runtime.types.classes.function;

import com.zagayevskiy.lang.runtime.IVariable;
import com.zagayevskiy.lang.runtime.Variable;
import com.zagayevskiy.lang.runtime.instructions.Instruction;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FunctionClassBuilder implements IFunctionClass.Builder {

    private final String name;
    private final ArrayList<Instruction> instructions = new ArrayList<>();

    private final ArrayList<Variable> arguments = new ArrayList<>();
    private final ArrayList<Variable> variables = new ArrayList<>();
    private final Map<String, Variable> variablesByName = new HashMap<>();

    public FunctionClassBuilder(@Nonnull String name) {
        this.name = name;
    }

    @Nonnull
    @Override
    public IFunctionClass.Builder addInstruction(@Nonnull Instruction instruction) {
        instructions.add(instruction);
        return this;
    }

    @Nonnull
    @Override
    public IFunctionClass.Builder putInstruction(@Nonnull Instruction instruction, int address) {
        instructions.set(address, instruction);
        return this;
    }

    @Override
    public int getInstructionsCount() {
        return instructions.size();
    }

    @Nonnull
    @Override
    public IFunctionClass.Builder removeLastInstruction() {
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

        final int id = variables.size();
        final Variable var = new Variable(id);
        variables.add(var);
        variablesByName.put(name, var);

        return var;
    }

    @Nonnull
    @Override
    public IVariable addArgument(@Nonnull String name) {
        if (hasVariable(name)) {
            throw new IllegalStateException("Argument " + name + "already defined must check hasVariable before.");
        }
        final Variable var = addVariable(name);
        arguments.add(var);
        return var;
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
    @Override
    public IFunctionClass build() {
        return new FunctionClass(name, arguments, instructions, variables);
    }
}