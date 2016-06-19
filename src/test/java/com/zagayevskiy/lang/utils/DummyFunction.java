package com.zagayevskiy.lang.utils;

import com.zagayevskiy.lang.logging.RuntimeLogger;
import com.zagayevskiy.lang.runtime.IVariable;
import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.IContext;
import com.zagayevskiy.lang.runtime.types.base.LangObject;
import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.types.primitive.LangUndefined;

import javax.annotation.Nonnull;

class DummyFunction implements IFunction, IContext {

    private final String name;

    DummyFunction(String name) {
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
    public LangObject call(@Nonnull IContext context) {
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
    public RuntimeLogger getRuntimeLogger() {
        return new RuntimeLogger() {
            @Override
            public void println(@Nonnull String message) {
            }

            @Override
            public void warning(@Nonnull String message) {
            }
        };
    }
}
