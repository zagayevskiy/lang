package com.zagayevskiy.lang.utils;

import com.zagayevskiy.lang.runtime.IVariable;
import com.zagayevskiy.lang.runtime.instructions.Instruction;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.IContext;
import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.LangString;
import com.zagayevskiy.lang.runtime.types.primitive.LangUndefined;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class DummyFunction implements IFunction, IContext {

    private final String name;
    public final List<Instruction> instructions = new ArrayList<>();

    public DummyFunction(String name) {
        this.name = name;
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Nonnull
    @Override
    public IVariable getVariable(int id) {
        return new DummyVariable();
    }

    @Override
    public void pushOperand(@Nonnull Operand operand) {
    }

    @Nonnull
    @Override
    public Operand popOperand() {
        return LangUndefined.INSTANCE;
    }

    @Nonnull
    @Override
    public LangObject call() {
        return LangUndefined.INSTANCE;
    }

    @Override
    public void jump(int position) {
    }

    @Override
    public void doReturn() {
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
        return LangString.from("dummy_func_" + name);
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
}
