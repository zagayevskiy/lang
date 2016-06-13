package com.zagayevskiy.lang.utils;

import com.zagayevskiy.lang.runtime.operand.Operand;
import com.zagayevskiy.lang.runtime.types.IContext;

import javax.annotation.Nonnull;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertNull;

public class TestUtils {

    public static InputStream stringStream(@Nonnull String from) {
        return new ByteArrayInputStream(from.getBytes());
    }

    public static InputStream fileStream(@Nonnull String file) {
        return TestUtils.class.getClassLoader().getResourceAsStream(file);
    }

    public static void assertOperandsStackEmpty(@Nonnull String message, @Nonnull IContext context) {
        Operand operand = null;
        try {
            operand = context.popOperand();
        } catch (NoSuchElementException ignored) {
        }

        assertNull("Operands stack not empty. " + message, operand);

    }
}
