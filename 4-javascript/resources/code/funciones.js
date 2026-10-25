function suma(a, b) {
    return a + b;
}

const arrowFunc = (a, b) => a + b;

const multipleLineFunc = () => {
    console.log("this is a multiple line arrow function");
    console.log("very useful :)");
};

// declaring and calling a function in the same line
let sqrtDe144 = (num => Math.sqrt(num))(144);
console.log(sqrtDe144); // 12

// you can use functions before declaring them
saludar();

function saludar() {
    console.log("hola");
}
