for (let i=0; i<100; i++) {
    console.log(i);
}

let x = 0;
while (x < 100) {
    if (x == 32) {
        continue;
    }
    console.log(x);
    x++;
}

x = 0;
do {
    if (x == 32) {
        break;
    }
    console.log(x);
    x++;
} while (x < 100);
 
