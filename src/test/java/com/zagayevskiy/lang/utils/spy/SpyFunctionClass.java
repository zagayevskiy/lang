package com.zagayevskiy.lang.utils.spy;

import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.classes.function.IFunctionClass;
import com.zagayevskiy.lang.runtime.types.function.IFunction;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.LangString;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class SpyFunctionClass implements IFunctionClass {
    private final IFunctionClass inner;
    public List<IFunction> functions = new ArrayList<>();

    SpyFunctionClass(IFunctionClass inner) {
        this.inner = inner;
    }

    @Override
    @Nonnull
    public String getName() {
        return inner.getName();
    }

    @Override
    @Nonnull
    public IFunction newInstance(@Nonnull List<LangObject> arguments) {
        IFunction f = inner.newInstance(arguments);
        functions.add(f);
        return f;
    }

    @Override
    @Nonnull
    public IFunctionClass applyPartially(@Nonnull List<LangObject> arguments) {
        return inner.applyPartially(arguments);
    }

    @Override
    public int getArgumentsCount() {
        return inner.getArgumentsCount();
    }

    @Override
    @Nonnull
    public String getLangClassName() {
        return inner.getLangClassName();
    }

    @Override
    @Nonnull
    public LangBoolean toLangBoolean() {
        return inner.toLangBoolean();
    }

    @Override
    @Nonnull
    public LangInteger toLangInteger() {
        return inner.toLangInteger();
    }

    @Override
    @Nonnull
    public LangString toLangString() {
        return inner.toLangString();
    }

    @Override
    @Nonnull
    public LangObject plus(@Nonnull LangObject other) {
        return inner.plus(other);
    }

    @Nonnull
    @Override
    public LangObject getValue(@Nonnull IFunction function) {
        return inner.getValue(function);
    }

    @Override
    public void execute(@Nonnull IFunction function) {
        inner.execute(function);
    }
}
