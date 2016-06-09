package com.zagayevskiy.lang.runtime.types;

import com.zagayevskiy.lang.runtime.types.classes.LangStructClass;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.LangString;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public final class LangStruct extends AbsLangObject {

    private final LangStructClass langClass;
    private final Map<String, LangObject> properties;

    public LangStruct(@Nonnull LangStructClass langClass, @Nonnull Map<String, LangObject> properties) {
        this.langClass = langClass;
        this.properties = properties;
    }

    public void setPropertyValue(@Nonnull String name, @Nonnull LangObject value) {
        properties.put(name, value);
    }

    @Nullable
    public LangObject getPropertyValue(@Nonnull String name) {
        return properties.get(name);
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

        StringBuilder builder = new StringBuilder(langClass.getLangClassName()).append("{ ");
        for (Map.Entry<String, LangObject> entry: properties.entrySet()) {
            builder.append(entry.getKey())
                    .append(" = ")
                    .append(entry.getValue().toLangString().stringValue)
                    .append("; ");
        }

        builder.append("}");

        return LangString.from(builder.toString());
    }

    @Override
    public String toString() {
        return toLangString().stringValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LangStruct that = (LangStruct) o;

        return langClass.equals(that.langClass) && properties.equals(that.properties);
    }

    @Override
    public int hashCode() {
        int result = langClass.hashCode();
        result = 31 * result + properties.hashCode();
        return result;
    }
}