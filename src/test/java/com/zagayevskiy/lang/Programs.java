package com.zagayevskiy.lang;

import com.zagayevskiy.lang.runtime.types.*;
import com.zagayevskiy.lang.runtime.types.classes.LangStructClass;
import javafx.util.Pair;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Programs {
    public static final List<Pair<String, LangObject>> PROGRAMS = new ArrayList<>();

    static {
//        p("main{;}", LangUndefined.INSTANCE);
        p("main {{{{{{{{{{13579;}}}}}}}}}}", 13579);
        p("main { 2 + 3 * (1 - 17 + (1 << 1) * 2) - 22; }", 2 + 3 * (1 - 17 + (1 << 1) * 2) - 22);
        p("main {var a = 1, b = 2, c = 7, x = a, z = b, y = c;} ", 7);
        p("main { var x, y, z=123, t, qwerty = 971,\n asdf=345; var g, h, k; \n var temp = qwerty; }", 971);
        p("main { 1; var x = 2; var y = (x + 1), z = y + 2;  x * 10; var result = x<<y<<z<<4; }", 2 << 3 << 5 << 4);
        p("main { var x = 10; var y = x + 5; }", 15);
        p("main { var x = 7; var y = x + 9; x = y + 123; }", 139);
        p("main { true; false; }", false);
        p("main { true || false; }", true);
        p("main { var a = true, b = true, c = false; var AorB = a || b, BandA = b && a, notC = !c; AorB && BandA && notC; }", true);
        p("main { var a = true, b = true, c = false; var AorB = a || b, BandA = b && a; c = c; AorB && BandA && c; }", false);
        p("main { if (true) 1; else 2; }", 1);
        p("main { if (true) 3; }", 3);
        p("main { if (false) 4; }", LangUndefined.INSTANCE);
        p("main { if (true) 100 + 200; else 300*700; }", 100 + 200);
        p("main { if (false) 10 * 222; else {3012*70013;} }", 3012 * 70013);
        p("main { if (1 + 3 - 2 * 2) { var x = 123 } else { var y = 321 }; true; false; 4223; x + y; }", LangUndefined.STRING_VALUE + 321);
        p("main { []; }", new LangArray());
        p("main { var x, array; array = [x, x, x]; }", LangUndefined.INSTANCE, LangUndefined.INSTANCE, LangUndefined.INSTANCE);
        p("main { [1, true, 2, 3, false, 4, 5, 6];}", 1, true, 2, 3, false, 4, 5, 6);
        p("main { var x = 10, y; y = [x, [x, [x]]]; var z = [y, [y, [y, [y]]]]; x = 1; z; }", crazyArray(crazyArray(LangInteger.from(10), 3), 4));
        p("main {var x = [111, 222, 333], y = 1; y = x[2];}", 333);
        p("main { var x = 0, y; y = [x, [x]]; var z = [y, [y, [y, [y]]]]; x = 1; y[1]; }", crazyArray(LangInteger.from(0), 1));
        p("main { var x = 0, y; y = [x, [x, [x]]]; var z = [y, [y, [y, [y]]]]; x = 1; z[0]; }", crazyArray(LangInteger.from(0), 3));
        p("main { var x = [0], y; y = [x, [x, [x]]]; var z = [y, [y, [y, [y]]]]; x[0] = 100500; z; }", crazyArray(crazyArray(array(LangInteger.from(100500)), 3), 4));
        p("struct t{ x, y, z} main { 1;}", 1);
        p("struct x{x} main{var x = new x(x);}",
                new LangStructClass("x")
                        .addProperty("x")
                        .newInstanceBuilder(1)
                        .withArgument(LangUndefined.INSTANCE)
                        .build());
        p("struct t{ x, y, z} main { var x = new t(1, 2, 3); }",
                new LangStructClass("t")
                        .addProperty("x")
                        .addProperty("y")
                        .addProperty("z")
                        .newInstanceBuilder(3)
                        .withArgument(LangInteger.from(3))
                        .withArgument(LangInteger.from(2))
                        .withArgument(LangInteger.from(1))
                        .build());
        p("struct x{ a } struct y{b} main {var y = new x(1), x = new y(y); 1;}", 1);
    }

    private static void p(String s, Object... args) {
        LangArray array = new LangArray();
        for (Object arg: args) {
            LangObject obj;
            final Class<?> clazz = arg.getClass();
            if (clazz == Integer.class) {
                obj = LangInteger.from((Integer)arg);
            } else if (clazz == Boolean.class) {
                obj = LangBoolean.from((Boolean) arg);
            } else obj = LangString.from(clazz.toString());
            array.add(obj);
        }
        p(s, array);
    }

    private static void p(String s, LangObject... args) {
        LangArray array = new LangArray();
        array.addAll(Arrays.asList(args));
        p(s, array);
    }

    private static void p(String s, boolean b) {
        p(s, LangBoolean.from(b));
    }

    private static void p(String s, String r) {
        p(s, LangString.from(r));
    }

    private static void p(String s, int i) {
        p(s, LangInteger.from(i));
    }

    private static void p(String s, LangObject o) {
        PROGRAMS.add(new Pair<>(s, o));
    }

    /**
     *
     * array "crazy" when it [obj, [obj, obj, ..x times.. [obj]]..]
     */
    @Nonnull
    private static LangArray crazyArray(@Nonnull LangObject obj, int count) {
        LangArray current = new LangArray();
        current.add(obj);

        for (int i = 0; i < count - 1; ++i) {
            final LangArray array = new LangArray();
            array.add(0, obj);
            array.add(1, current);
            current = array;
        }

        return current;
    }

    private static LangArray array(@Nonnull LangObject obj) {
        LangArray array = new LangArray();
        array.add(obj);
        return array;
    }
}
