function fibonacciRecursive(n, first, second) {
    if (n > 0) {
        fibonacciRecursive(n - 1, second, first + second);
    } else {
        first;
    }
}

function fibonacci(n) {
    if (n > 0) {
        fibonacciRecursive(n - 1, 1, 1);
    } else {
        0;
    }
}

main{
    fibonacci(46);
}