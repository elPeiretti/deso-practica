class Persona {

    constructor(nombre, edad) {
      this.nombre = nombre;
      this.edad = edad;
    }

    saludar() {
      console.log(`Hola, soy ${this.nombre}`);
    }
}

let persona = new Persona("Juan", 66);
persona.saludar(); // Hola, soy Juan

// a este tipo de objetos
// tambien podemos agregarle atributos en runtime
persona.apellido = "Pepe";
console.log(persona.apellido);
