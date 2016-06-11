package com.zagayevskiy.lang;

import com.zagayevskiy.lang.runtime.types.*;
import com.zagayevskiy.lang.runtime.types.array.LangArray;
import com.zagayevskiy.lang.runtime.types.classes.LangStructClass;
import com.zagayevskiy.lang.runtime.types.primitive.LangBoolean;
import com.zagayevskiy.lang.runtime.types.primitive.LangInteger;
import com.zagayevskiy.lang.runtime.types.primitive.LangString;
import com.zagayevskiy.lang.runtime.types.primitive.LangUndefined;
import javafx.util.Pair;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class Programs {
    public static final List<Pair<String, LangObject>> PROGRAMS = new ArrayList<>();
    public static final List<Pair<String, LangObject>> FILES = new ArrayList<>();

    static {
        f("sources/sum_recursive_argument_10.js", 55);
        f("sources/fibonacci_recursive_argument_46.js", 1836311903);
        f("sources/fibonacci_recursive_with_lambda_argument_46.js", 1836311903);
    }

    static {
//        p("main{;}", LangUndefined.INSTANCE);
        p("main {{{{{{{{{{13579;}}}}}}}}}}", 13579);
        p("main { 2 + 3 * (1 - 17 + (1 << 1) * 2) - 22; }", 2 + 3 * (1 - 17 + (1 << 1) * 2) - 22);
        p("main { 1024 >> 1 >> 3 << 10; }", 1024 >> 1 >> 3 << 10);
        p("main { var x = 2; x == 2 && x != 3; }", true);
        p("main { var x = 2; x != 2 && x == 3; }", false);
        p("main { var x = 2; x != 2 || x != 3; }", true);
        p("main { var x = 2; x == 2 || x == 3; }", true);
        p("main { var x = 2; x || x == 3; }", true);
        p("main { var x = 0; x || x == 3; }", false);
        p("main { var x = false; x || !x; }", true);
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
        p("main { if (1 + 3 - 2 * 2) { var x = 123; } else { var y = 321; }; true; false; 4223; x + y; }", LangUndefined.STRING_VALUE + 321);
        p("main { []; }", new LangArray());
        p("main { var x, array; array = [x, x, x]; }", array(LangUndefined.INSTANCE, LangUndefined.INSTANCE, LangUndefined.INSTANCE));
        p("main { [1, true, 2, 3, false, 4, 5, 6];}", array(1, true, 2, 3, false, 4, 5, 6));
        p("main { var x = 10, y; y = [x, [x, [x]]]; var z = [y, [y, [y, [y]]]]; x = 1; z; }", crazyArray(crazyArray(LangInteger.from(10), 3), 4));
        p("main {var x = [111, 222, 333], y = 1; y = x[2];}", 333);
        p("main { var x = 0, y; y = [x, [x]]; var z = [y, [y, [y, [y]]]]; x = 1; y[1]; }", crazyArray(LangInteger.from(0), 1));
        p("main { var x = 0, y; y = [x, [x, [x]]]; var z = [y, [y, [y, [y]]]]; x = 1; z[0]; }", crazyArray(LangInteger.from(0), 3));
        p("main { var x = [0], y; y = [x, [x, [x]]]; var z = [y, [y, [y, [y]]]]; x[0] = 100500; z; }", crazyArray(crazyArray(array(LangInteger.from(100500)), 3), 4));
        p("main { var x = [], y = x; x[3] = 5; y[1] = 7; x; }", array(LangUndefined.INSTANCE, 7, LangUndefined.INSTANCE, 5));
        p("struct t{ x, y, z} main { 1;}", 1);
        p("struct x{x} main{var x = new x(x);}",
                new LangStructClass("x")
                        .addProperty("x")
                        .newInstanceBuilder(1)
                        .withArgument(LangUndefined.INSTANCE)
                        .build());
        p("struct t{ x, y, z} main { var x = new t(1, [19, 37, 143], 3); }",
                new LangStructClass("t")
                        .addProperty("x")
                        .addProperty("y")
                        .addProperty("z")
                        .newInstanceBuilder(3)
                        .withArgument(LangInteger.from(3))
                        .withArgument(array(19, 37, 143))
                        .withArgument(LangInteger.from(1))
                        .build());
        p("struct x{ a } struct y{b} main {var y = new x(1), x = new y(y); 1;}", 1);
        p("struct x{ a } main { var y = new x(123); y->a = new x(999); y->a->a; }", 999);
        p("struct x{ a } main { var y = new x(123); y->a = [234]; y->a[0]; }", 234);
        p("struct t{ x, y, z} struct r{i} main { var x = new t(1, [19, new r(1), 143], 3); x->y[1]->i = 1000; x; }",
                new LangStructClass("t")
                        .addProperty("x")
                        .addProperty("y")
                        .addProperty("z")
                        .newInstanceBuilder(3)
                        .withArgument(LangInteger.from(3))
                        .withArgument(array(19, new LangStructClass("r")
                                .addProperty("i")
                                .newInstanceBuilder(1)
                                .withArgument(LangInteger.from(1000))
                                .build(), 143))
                        .withArgument(LangInteger.from(1))
                        .build());
        p("function f(x, y, z){ 1; } main{ 2; }", 2);
        p("function f(x, y, z){ 1; } main{ 123; f(2, 3, 4); 5; }", 5);
        p("function f(x, y, z){ var inner = 5*7*237; 10101; inner; } main{ 123; f(2, 3, 5, 6, 7); }", 5*7*237);
        p("function f(x, y, z){ 10101; } main{ 123; f(2, 3, 5, 6, 7); }", 10101);
        p("function f(x, y, z, asdf) { var t, tt, ttt = x + y * z + asdf; ttt; } main{ 123; f(2, 3, 5, 6, 7); }", 2 + 3 * 5 + 6);
        p("function f(y){ y; } struct y{y} main{ var y = new y(f(new y(9))); f(y->y->y); }", 9);
        p("struct str {s, u, v} function f(strInstance){ var s = strInstance; s->s + s->u + s->v; } main{ f(new str(3, 4, 5)); }", 3+4+5);
        p("function sum (x, y) { x + y; } main {var s = sum(2); [s(1), s(2), s(3), s(true), s(100)]; }", array(3, 4, 5, "2true", 102));
        p("main { var lambda = \\(x,y,z) x + y + z; lambda(1, 2, 3); }", 1 + 2 + 3);
        p("main { \\(x, y)(x + y)(10, 20); }", 10 + 20);
        p("main { \\(x, y){ var z = x + y; z*50; }(10, 20); }", (10 + 20)*50);
        p("main { var x = { 11 + 12 + 13; }; } ", 11 + 12 + 13);
    }

    private static void f(String s, Object o) {
        f(s, toLangObject(o));
    }

    private static void f(String s, LangObject o) {
        FILES.add(new Pair<>(s, o));
    }

    private static void p(String s, Object o) {
        p(s, toLangObject(o));
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

    private static LangArray array(Object... args) {
        LangArray array = new LangArray();
        for (Object arg: args) {
            array.add(toLangObject(arg));
        }
        return array;
    }

    @Nonnull
    private static LangObject toLangObject(@Nonnull Object arg) {
        LangObject obj;
        final Class<?> clazz = arg.getClass();
        if (clazz == Integer.class) {
            obj = LangInteger.from((Integer)arg);
        } else if (clazz == Boolean.class) {
            obj = LangBoolean.from((Boolean) arg);
        } else if (clazz == String.class) {
            obj = LangString.from((String) arg);
        } else if (arg instanceof LangObject) {
            obj = (LangObject) arg;
        } else {
            obj = LangString.from(clazz.toString());
        }
        return obj;
    }
}
