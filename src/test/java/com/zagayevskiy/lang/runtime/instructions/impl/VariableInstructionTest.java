package com.zagayevskiy.lang.runtime.instructions.impl;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class VariableInstructionTest {

    @Test
    public void cacheSameIds() {
        final int id = 123123;
        assertThat(VariableInstruction.from(id, ""), equalTo(VariableInstruction.from(id, "")));
    }

}