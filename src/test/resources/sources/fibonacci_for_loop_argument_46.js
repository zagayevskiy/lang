function fibonacci(n) {
    var first = 0, second = 1;

    for (; n > 0 ; n = n - 1) {
        var temp = second;
        second = second + first;
        first = temp;
    }

    first;
}

main{
    fibonacci(46);
}