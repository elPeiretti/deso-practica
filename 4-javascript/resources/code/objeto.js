let persona = {
    nombre: "Bombardino",
    apellido: "Crocodilo",
    edad: 18,
    direccion: {
        calle: "Calle falsa 123",
        ciudad: "Santa Fe"
    },
    saludar: () => console.log(`hola soy ${this.nombre}`)
};

// acceso a sus atributos y metodos
console.log(persona.apellido); // Crocodilo
console.log(persona["edad"]);  // 18
persona.saludar();             // hola soy Bombardino

// a este tipo de objetos le podemos agregar atributos
persona["signo"] = "escorpio";
persona.altura = 180;
console.log(persona.signo);  // escorpio
console.log(persona.altura); // 180

// otra manera de crear objetos
let persona2 = new Object();
persona2.edad = 25;
console.log(persona2.edad); // 25
