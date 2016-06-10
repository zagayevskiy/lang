function fibonacci(number) {
    var fibonacciRecursive = \(self, n, first, second) {
        if (n > 0) {
            self(self, n - 1, second, first + second);
        } else {
            first;
        }
    };

    if (number > 0) {
        fibonacciRecursive(fibonacciRecursive, number - 1, 1, 1);
    } else {
        0;
    }
}

main{
    fibonacci(46);
}