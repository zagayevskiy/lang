package com.zagayevskiy.lang.runtime.types.classes;

import com.zagayevskiy.lang.runtime.types.*;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LangStructClass extends LangObject implements LangClass {

    public final class InstanceBuilder {
        private final HashMap<String, LangObject> properties = new HashMap<>();
        private int cursor;
        private InstanceBuilder(int argsCount) {
            cursor = argsCount - 1;
        }

        /**
         *
         * arguments must be passed in reverse order (as they presented in stack).
         * must be called exactly argsCount times
         * throws {@link IllegalStateException} after more than argsCount calls.
         */
        @Nonnull
        public InstanceBuilder withArgument(@Nonnull LangObject arg) {
            if (cursor < 0) {
                throw new IllegalStateException("withArgument called more times than expected.");
            }
            if (cursor >= propertiesNames.size()) {
                --cursor;
                return this;
            }

            properties.put(propertiesNames.get(cursor), arg);
            --cursor;
            return this;
        }

        @Nonnull
        public LangStruct build() {
            if (cursor != -1) {
                throw new IllegalStateException("withArgument not called exactly argsCount times.");
            }
            return new LangStruct(LangStructClass.this, properties);
        }
    }

    @Nonnull
    private final List<String> propertiesNames = new ArrayList<>();
    @Nonnull
    private final String name;

    public LangStructClass(@Nonnull String name) {
        this.name = name;
    }

    @Nonnull
    public LangStructClass addProperty(@Nonnull String name) {
        propertiesNames.add(name);
        return this;
    }

    public boolean hasProperty(@Nonnull String name) {
        return propertiesNames.contains(name);
    }

    public int getPropertiesCount() {
        return propertiesNames.size();
    }

    @Nonnull
    public InstanceBuilder newInstanceBuilder(int argsCount) {
        return new InstanceBuilder(argsCount);
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
        return LangString.from("classes class " + name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LangStructClass that = (LangStructClass) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Nonnull
    @Override
    public String getLangClassName() {
        return "struct@" + name;
    }

    @Nonnull
    public String getName() {
        return name;
    }
}
