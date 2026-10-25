var noDefinida;
var variable = 123;
const constante = "constante";
let variableLocal = '123';

console.log(variable) // 123
console.log(variableLocal == variable)  // true
console.log(variableLocal === variable) // false

console.log("la variable tiene algun valor? ", !!variable); // la variable tiene algun valor? true
variable = null
console.log("la variable tiene algun valor? ", !!variable); // la variable tiene algun valor? false

console.log(noDefinida === variable) // false
console.log(`variable=${variable}, noDefinida=${noDefinida}`); // variable=null, noDefinida=undefined

// se puede asignar cualquier cosa a una misma variable
variableLocal = 200
variableLocal = false
varialbeLocal = new BigInt(500)
