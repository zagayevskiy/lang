package com.zagayevskiy.lang.logging;

import javax.annotation.Nonnull;

public class ConsoleLogger implements Logger {
    @Override
    public void logError(@Nonnull String message) {
        System.err.println(message);
    }
}
