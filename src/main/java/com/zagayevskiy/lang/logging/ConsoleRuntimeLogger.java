package com.zagayevskiy.lang.logging;

import javax.annotation.Nonnull;

public class ConsoleRuntimeLogger implements RuntimeLogger {

    public static final ConsoleRuntimeLogger INSTANCE = new ConsoleRuntimeLogger();

    @Override
    public void println(@Nonnull String message) {
        System.out.println(message);
    }

    @Override
    public void warning(@Nonnull String message) {
        System.out.print("warning: ");
        System.out.println(message);
    }
}
