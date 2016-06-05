package com.zagayevskiy.lang.runtime;

import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.LangObject;

import javax.annotation.Nonnull;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class Function implements IFunction {

    private final String name;
    private ArrayList<Instruction> instructions = new ArrayList<>();
    private int instructionPointer;

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
    public IFunction removeLastInstruction() {
        instructions.remove(instructions.size() - 1);
        return this;
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

        return operandsStack.pop().getValue();
    }

    @Override
    public void jump(int position) {
        instructionPointer = position;
    }
}
