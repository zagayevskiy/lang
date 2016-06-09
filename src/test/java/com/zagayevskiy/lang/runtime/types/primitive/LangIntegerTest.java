package com.zagayevskiy.lang.runtime.types.primitive;

import com.zagayevskiy.lang.runtime.types.LangObject;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.LangString;
import com.zagayevskiy.lang.utils.DummyLangObject;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class LangIntegerTest {

    @Test
    public void fromNanSameInstance () {
        assertSame(LangInteger.NaN, LangInteger.from(1000, true));
    }

    @Test
    public void cacheSameInstance () {
        for (int i = 0; i < LangInteger.CACHE.length; ++i) {
            LangInteger i1 = LangInteger.from(i), i2 = LangInteger.from(i);
            assertSame(i1, i2);
        }
    }

    @Test
    public void toLangIntegerSameInstance() {
        LangInteger i = LangInteger.from(100500);
        assertEquals(i, i.toLangInteger());
    }

    @Test
    public void toLangString() {
        LangInteger i = LangInteger.from(100500);
        assertEquals(i.intValue, Integer.parseInt(i.toLangString().stringValue));
    }

    @Test
    public void plusInteger() {
        LangInteger i1 = LangInteger.from(200600), i2 = LangInteger.from(100500);
        assertEquals(200600 + 100500, i1.plus(i2).toLangInteger().intValue);
    }

    @Test
    public void plusOther() {
        LangInteger i = LangInteger.from(123456);

        LangObject sum = i.plus(new DummyLangObject());

        assertThat(sum, instanceOf(LangString.class));

        sum = new DummyLangObject().plus(i);

        assertThat(sum, instanceOf(LangString.class));
    }

    @Test
    public void plusNan() {
        assertEquals(LangInteger.NaN, LangInteger.NaN.plus(LangInteger.from(654321)));
        assertEquals(LangInteger.NaN, LangInteger.from(987654).plus(LangInteger.NaN));
    }

    @Test
    public void shiftLeft() {
        LangInteger i1 = LangInteger.from(147835), i2 = LangInteger.from(7);
        assertEquals(i1.intValue << i2.intValue, i1.shiftLeft(i2).intValue);
    }

    @Test
    public void shiftRight() {
        LangInteger i1 = LangInteger.from(1478305), i2 = LangInteger.from(7);
        assertEquals(i1.intValue >> i2.intValue, i1.shiftRight(i2).intValue);
    }

    @Test
    public void multiply() {
        LangInteger i1 = LangInteger.from(600), i2 = LangInteger.from(800);
        assertEquals(i1.intValue * i2.intValue, i1.multiply(i2).intValue);
    }
}