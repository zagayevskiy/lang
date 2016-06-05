package com.zagayevskiy.lang.utils;

import com.zagayevskiy.lang.logging.Logger;

import javax.annotation.Nonnull;

public class ThrowLogger implements Logger {

    public static final Logger INSTANCE = new ThrowLogger();

    @Override
    public void logError(@Nonnull String message) {
        throw new RuntimeException(message);
    }
}
