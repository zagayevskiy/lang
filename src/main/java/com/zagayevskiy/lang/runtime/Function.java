package com.zagayevskiy.lang.runtime;

import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.LangUndefined;

import javax.annotation.Nonnull;
import java.util.*;

public class Function implements IFunction {

    private final String name;
    private final ArrayList<Instruction> instructions = new ArrayList<>();
    private int instructionPointer;

    private final ArrayList<Variable> variables = new ArrayList<>();
    private final Map<String, Variable> variablesByName = new HashMap<>();

    private final Deque<Operand> operandsStack = new ArrayDeque<>();

    public Function(@Nonnull String name) {
        this.name = name;
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Nonnull
    @Override
    public IFunction addInstruction(@Nonnull Instruction instruction) {
        instructions.add(instruction);
        return this;
    }

    @Nonnull
    @Override
    public IFunction putInstruction(@Nonnull Instruction instruction, int address) {
        instructions.set(address, instruction);
        return this;
    }

    @Override
    public int getInstructionsCount() {
        return instructions.size();
    }

    @Nonnull
    @Override
    public IFunction removeLastInstruction() {
        instructions.remove(instructions.size() - 1);
        return this;
    }

    @Override
    public boolean hasVariable(@Nonnull String name) {
        return variablesByName.containsKey(name);
    }

    @Nonnull
    @Override
    public IVariable addVariable(@Nonnull String name) {
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

    @Override
    public void pushOperand(@Nonnull Operand operand) {
        operandsStack.push(operand);
    }

    @Nonnull
    @Override
    public Operand popOperand() {
        return operandsStack.pop();
    }

    @Nonnull
    @Override
    public LangObject execute() {
        instructionPointer = 0;

        while (instructionPointer < instructions.size()) {
            Instruction instruction = instructions.get(instructionPointer);
            ++instructionPointer;
            instruction.execute(this);
        }

        return operandsStack.pop().getValue(this);
    }

    @Override
    public void jump(int position) {
        instructionPointer = position;
    }

    @Override
    public String toString() {
        return "func " + name;
    }
}
