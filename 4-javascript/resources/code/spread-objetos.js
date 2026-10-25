const persona = {
    nombre: "Ricardo",
    apellido: "Sanchez",
    cuil: 20388888885,
    intereses: ["programar en javascript", "quejarse de javascript"],
};

// Copiando un objeto
const personaCopia = { ...persona };

// Extrayendo keys del objeto
const { nombre, intereses } = persona;

console.log(nombre); // Imprime: "Ricardo"
console.log(intereses); // Imprime: ["programar en javascript", "quejarse de javascript"]
