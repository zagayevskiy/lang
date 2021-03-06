package com.zagayevskiy.lang.runtime.types.primitive;

import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.base.AbsLangObject;
import com.zagayevskiy.lang.runtime.types.base.LangObject;
import com.zagayevskiy.lang.runtime.types.primitive.string.LangString;

import javax.annotation.Nonnull;

public class LangInteger extends AbsLangObject implements Operand, Comparable<LangInteger> {

    public static final LangInteger NaN = new LangInteger(0, true);

    static final LangInteger[] CACHE = new LangInteger[256];

    @Nonnull
    public static LangInteger from(int value) {
        if (0 <= value && value < CACHE.length) {
            LangInteger cache = CACHE[value];
            if (cache == null) {
                cache = new LangInteger(value);
                CACHE[value] = cache;
            }
            return cache;
        }
        return new LangInteger(value);
    }

    @Nonnull
    public static LangInteger from(int value, boolean isNan) {
        return isNan ? NaN : from(value);
    }

    public final int intValue;
    public final boolean isNan;

    private LangInteger(int intValue) {
        this(intValue, false);
    }

    private LangInteger(int intValue, boolean isNan) {
        this.intValue = intValue;
        this.isNan = isNan;
    }

    @Nonnull
    @Override
    public LangBoolean toLangBoolean() {
        return LangBoolean.from(intValue != 0);
    }

    @Nonnull
    @Override
    public LangInteger toLangInteger() {
        return this;
    }

    @Nonnull
    @Override
    public LangString toLangString() {
        return LangString.from(String.valueOf(intValue));
    }

    @Nonnull
    @Override
    public LangObject plus(@Nonnull LangObject other) {
        if (!(other instanceof LangInteger)) {
            return super.plus(other);
        }

        final LangInteger otherInt = other.toLangInteger();
        return from(intValue + otherInt.intValue, isNan || otherInt.isNan);
    }

    @Override
    public String toString() {
        return "i:" + (isNan ? "NaN:" : "")  + intValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LangInteger that = (LangInteger) o;

        return (isNan == that.isNan) && (intValue == that.intValue);

    }

    @Override
    public int hashCode() {
        int result = intValue;
        result = 31 * result + (isNan ? 1 : 0);
        return result;
    }

    @Override
    public int compareTo(@Nonnull LangInteger other) {
        return intValue - other.intValue;
    }

    @Nonnull
    public LangInteger shiftLeft(@Nonnull LangInteger by) {
        return from(intValue << by.intValue, isNan || by.isNan);
    }

    @Nonnull
    public LangInteger shiftRight(@Nonnull LangInteger by) {
        return from(intValue >> by.intValue, isNan || by.isNan);
    }

    @Nonnull
    public LangInteger multiply(@Nonnull LangInteger by) {
        return from(intValue * by.intValue, isNan || by.isNan);
    }

    @Nonnull
    public LangInteger mod(@Nonnull LangInteger by) {
        return by.intValue == 0
                ? NaN
                : from(intValue % by.intValue, isNan || by.isNan);
    }

    @Nonnull
    public LangInteger divide(@Nonnull LangInteger by) {
        return by.intValue == 0
                ? NaN
                : from(intValue / by.intValue, isNan || by.isNan);
    }

    @Nonnull
    public LangInteger bitAnd(@Nonnull LangInteger with) {
        return from(intValue & with.intValue, isNan || with.isNan);
    }

    @Nonnull
    public LangInteger bitNot() {
        return from(~intValue, isNan);
    }

    @Nonnull
    public LangInteger bitOr(@Nonnull LangInteger with) {
        return from(intValue | with.intValue, isNan || with.isNan);
    }

    @Nonnull
    public LangInteger bitXor(@Nonnull LangInteger with) {
        return from(intValue ^ with.intValue, isNan || with.isNan);
    }

    @Nonnull
    public LangInteger minus(@Nonnull LangInteger right) {
        return from(intValue - right.intValue, isNan || right.isNan);
    }

    @Nonnull
    public LangInteger negate() {
        return from(-intValue, isNan);
    }
}
