package com.zagayevskiy.lang.runtime.types.classes;

import com.zagayevskiy.lang.runtime.types.function.prototype.IMethodPrototype;
import com.zagayevskiy.lang.runtime.types.methods.Methods;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class DefaultClass implements LangClass {

        public static final DefaultClass INSTANCE = new DefaultClass();

        private Map<String, IMethodPrototype> methodPrototypes = new HashMap<>();

        protected DefaultClass() {
            for (IMethodPrototype prototype: Methods.DEFAULT_METHODS) {
                registerMethodPrototype(prototype);
            }
        }

        @Override
        @Nullable
        public final IMethodPrototype getMethodPrototype(@Nonnull String name) {
            return methodPrototypes.get(name);
        }

        @Nonnull
        protected final DefaultClass registerMethodPrototype(@Nonnull IMethodPrototype methodPrototype) {
            methodPrototypes.put(methodPrototype.getName(), methodPrototype);
            return this;
        }

        @Nonnull
        @Override
        public String getLangClassName() {
            return DefaultClass.class.getSimpleName();
        }
    }