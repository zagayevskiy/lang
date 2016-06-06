package com.zagayevskiy.lang.utils;

import com.zagayevskiy.lang.runtime.IFunction;
import com.zagayevskiy.lang.runtime.operand.Operand;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class TestUtils {

    public static InputStream stringStream(@Nonnull String from) {
        return new ByteArrayInputStream(from.getBytes());
    }

    public static InputStream fileStream(@Nonnull String file) {
        try {
            return new FileInputStream(new File(TestUtils.class.getClassLoader().getResource(file).getFile()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void assertOperandsStachEmpty(@Nonnull IFunction function) {
        Operand operand;
        try {
            operand = function.popOperand();
        } catch (NoSuchElementException e) {
            return;
        }

        assertNull("Operands stack not empty.", operand);

    }
}
