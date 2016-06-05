package com.zagayevskiy.lang.runtime.types;

import org.junit.Test;

import static org.junit.Assert.*;

public class LangStringTest {

    @Test
    public void toLangStringSameInstance() {
        LangString s = LangString.from("qwertyuiop");
        assertSame(s, s.toLangString());
    }
}