package com.zagayevskiy.lang.runtime.types.function;

import com.zagayevskiy.lang.runtime.IVariable;
import com.zagayevskiy.lang.runtime.Variable;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.LangString;

import javax.annotation.Nonnull;
import java.util.*;

public class Function implements IFunction {

    private final String name;
    private final ArrayList<Instruction> instructions;
    private int instructionPointer;

    private final ArrayList<Variable> variables;

    private final Deque<Operand> operandsStack = new ArrayDeque<>();

    public Function(@Nonnull String name, ArrayList<Instruction> instructions, ArrayList<Variable> variables) {
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

    @Nonnull
    @Override
    public LangObject plus(@Nonnull LangObject other) {
        return LangString.from(toLangString().stringValue + other.toLangString().stringValue);
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
}