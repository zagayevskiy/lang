package com.zagayevskiy.lang.utils;

import javax.annotation.Nonnull;
import java.io.*;

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
}
