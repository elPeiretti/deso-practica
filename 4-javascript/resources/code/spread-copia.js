// Copiando un array
const nuevoArray = [...muchosNumeros]

console.log(nuevoArray) // Imprime: [10, 20, 30, 40, 50, 60]
console.log(muchosNumeros) // Imprime: [10, 20, 30, 40, 50, 60]

nuevoArray.pop() // Removemos el último elemento de la copia

console.log(nuevoArray) // Imprime: [10, 20, 30, 40, 50]
console.log(muchosNumeros) // Imprime: [10, 20, 30, 40, 50, 60]
