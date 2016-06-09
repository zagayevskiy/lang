package com.zagayevskiy.lang.runtime.types.primitive;

import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.*;

public class LangBooleanTest {

    @Test
    public void sameInstances() {
        assertThat(LangBoolean.from(true), sameInstance(LangBoolean.TRUE));
        assertThat(LangBoolean.from(false), sameInstance(LangBoolean.FALSE));
    }

    @Test
    public void operations() {
        assertThat(LangBoolean.TRUE.not(), sameInstance(LangBoolean.FALSE));
        assertThat(LangBoolean.FALSE.not(), sameInstance(LangBoolean.TRUE));
        
        assertThat(LangBoolean.TRUE.and(LangBoolean.FALSE), sameInstance(LangBoolean.FALSE));
        assertThat(LangBoolean.FALSE.and(LangBoolean.FALSE), sameInstance(LangBoolean.FALSE));
        assertThat(LangBoolean.FALSE.and(LangBoolean.TRUE), sameInstance(LangBoolean.FALSE));
        assertThat(LangBoolean.TRUE.and(LangBoolean.TRUE), sameInstance(LangBoolean.TRUE));


        assertThat(LangBoolean.FALSE.or(LangBoolean.TRUE), sameInstance(LangBoolean.TRUE));
        assertThat(LangBoolean.TRUE.or(LangBoolean.TRUE), sameInstance(LangBoolean.TRUE));
        assertThat(LangBoolean.TRUE.or(LangBoolean.FALSE), sameInstance(LangBoolean.TRUE));
        assertThat(LangBoolean.FALSE.or(LangBoolean.FALSE), sameInstance(LangBoolean.FALSE));
    }
}