package com.zagayevskiy.lang.runtime.types.function;

import com.zagayevskiy.lang.logging.ConsoleRuntimeLogger;
import com.zagayevskiy.lang.logging.RuntimeLogger;
import com.zagayevskiy.lang.runtime.IContext;
import com.zagayevskiy.lang.runtime.IVariable;
import com.zagayevskiy.lang.runtime.Variable;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.base.LangObject;

import javax.annotation.Nonnull;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Function implements IContext, IFunction {

    private final String name;
    private final List<Instruction> instructions;
    private int instructionPointer;

    private final List<Variable> variables;

    private final Deque<Operand> operandsStack = new ArrayDeque<>();

    public Function(@Nonnull String name, List<Instruction> instructions, List<Variable> variables) {
        this.name = name;
        this.instructions = instructions;
        this.variables = variables;
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }


    @Nonnull
    @Override
    public IVariable getVariable(int id) {
        return variables.get(id);
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
    public LangObject call(@Nonnull IContext unused) {
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
    public void doReturn() {
        jump(instructions.size());
    }

    @Override
    public String toString() {
        return "func " + name;
    }

    @Nonnull
    @Override
    public RuntimeLogger getRuntimeLogger() {
        //TODO: resolve dependency
        return ConsoleRuntimeLogger.INSTANCE;
    }
}
