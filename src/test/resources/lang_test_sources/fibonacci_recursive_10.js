function fibonacciRecursive(n, first, second) {
    if (n > 0) {
        fibonacciRecursive(n - 1, second, first + second);
    } else {
        first;
    }
}

function fibonacci(n) {
    fibonacciRecursive(n, 1, 1);
}

main{
    fibonacci(7);
}