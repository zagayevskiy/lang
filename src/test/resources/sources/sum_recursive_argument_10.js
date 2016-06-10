function sum(n) {
    if (n > 0) {
        n + sum (n - 1);
    } else {
        0;
    }
}

main{
    sum(10);
}