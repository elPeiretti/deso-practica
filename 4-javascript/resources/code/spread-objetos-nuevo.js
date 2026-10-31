// Crear un nuevo objeto modificando keys pero copiando los demas valores
const personaNueva = { ...persona, nombre: "Juan" };

console.log(persona.nombre); // Imprime: "Ricardo"
console.log(persona.apellido); // Imprime: "Sanchez"
console.log(personaNueva.nombre); // Imprime: "Juan"
console.log(personaNueva.apellido); // Imprime: "Sanchez"

// Crear un nuevo objeto agregando keys pero copiando los demás valores
const personaNueva = { ...persona, copado: true };

console.log(!!personaNueva.copado) // Imprime: true
console.log(!!persona.copado) // Imprime: false
