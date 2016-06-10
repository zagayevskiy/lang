package com.zagayevskiy.lang.utils;

import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.classes.function.IFunctionClass;
import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.LangString;
import com.zagayevskiy.lang.runtime.types.primitive.LangUndefined;

import javax.annotation.Nonnull;
import java.util.List;

class DummyFunctionClass implements IFunctionClass {

    private final String name;

    DummyFunctionClass(@Nonnull String name) {
        this.name = name;
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Nonnull
    @Override
    public IFunction newInstance(@Nonnull List<LangObject> arguments) {
        return new DummyFunction(name);
    }

    @Override
    public int getArgumentsCount() {
        return 0;
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
        return LangString.from("dummy_function_class$" + name);
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
    public String getLangClassName() {
        return "dummy_func$" + name;
    }
}
