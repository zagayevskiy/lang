function sum(n) {
    var sum = 0;
    for (; n > 0; n = n - 1) sum = sum + n;
    return sum;
}

main {
    sum(10);
}