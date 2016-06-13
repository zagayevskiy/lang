package com.zagayevskiy.lang.runtime.types.methods;

import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.function.IFunction;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public abstract class AbsBuiltInMethod implements IFunction {

    @Nonnull
    private final String name;

    @Nonnull
    private final LangObject self;

    @Nonnull
    private final List<LangObject> arguments;

    public AbsBuiltInMethod(@Nonnull String name, @Nonnull List<LangObject> arguments) {
        this.name = name;
        this.self = arguments.get(0);
        if (arguments.size() > 1) {
            this.arguments = arguments.subList(1, arguments.size());
        } else {
            this.arguments = Collections.emptyList();
        }
    }

    @Nonnull
    @Override
    public final String getName() {
        return name;
    }

    @Nonnull
    @Override
    public final LangObject call() {
        return call(self, arguments);
    }

    @Nonnull
    protected abstract LangObject call(@Nonnull LangObject self, @Nonnull List<LangObject> arguments);
}
