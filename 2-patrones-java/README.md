# Desarrollo de Software

## Clase práctica 3

El líder técnico de su equipo ha revisado el progreso del desarrollo del sistema para la empresa de turismo y, a partir de su experiencia, ha realizado una serie de sugerencias para mejorar la solución implementada. Su trabajo consistirá en incorporar estas mejoras al sistema, adaptando el diseño existente e implementando los cambios necesarios para responder a los nuevos requerimientos no funcionales.

![](img/image1.png)

Recuerde que ya se posee un archivo reservations.csv que almacena reservas, y el archivo users.txt que contiene usuarios con los siguientes formatos.

**reservations.csv**

```
<reservationId>,<serviceId>,<ownerId>,<date>
```

**users.txt**

```
550e8400-e29b-41d4-a716-446655440000 juan
f47ac10b-58cc-4372-a567-0e02b2c3d479 maria
```

Considerando que se desea separar la lógica de acceso a datos de la lógica de negocio y presentación, y que desea simplificar la construcción de objetos:

1. Desarrolle el **patrón DAO** para gestionar la persistencia de reservas (clase **Reservation**). Para esto, debe declarar el método **save(Reservation reservation)** y su implementación deberá guardar la reserva en el archivo **reservations.csv**.

   1. Ante cualquier tipo de excepción que pueda ocurrir, deberá wrappearla en una **DataAccessException**.

2. Implemente el **patrón DAO** para gestionar la búsqueda de usuarios a través del método **findByUsername(String username)**.

   1. Aplique el uso de la clase **Optional** para el objeto de retorno.

   2. Ante cualquier tipo de excepción que pueda ocurrir, deberá wrappearla en una **DataAccessException**.

3. Aplique el **patrón** **Singleton**: oculte el constructor por defecto y desarrolle dicho patrón en las implementaciones de los DAO.

   1. ¿Qué ventajas brinda utilizar el patrón singleton? ¿Tiene alguna desventaja?

4. Aplique el **patrón Builder**: Agregue a la clase de dominio Reservation la implementación de dicho patrón.

   1. Validar que service, owner y date no sean null; lanzar IllegalStateException con un mensaje descriptivo en caso de que alguno lo sea.

5. Extraiga la lógica de negocio a un nuevo paquete denominado **service** dentro del cual deberán existir dos clases; **UserService** y **ReservationService**.

   1. **UserService** debe: exponer el método **getByUsername(String username)** que utilice UserDao para buscar un usuario por su nombre. En caso de no encontrar el usuario debe lanzar **UserNotFoundException**.

   2. **ReservationService** debe: exponer el método **createReservation(User owner, TouristService service, Instant date)** que permita persistir una nueva reserva.

      1. Utilice el **builder** de Reservation para instanciar el objeto.

      2. No se olvide de validar que la fecha que recibe como parámetro sea mayor a hoy.

6. Implemente en una nueva clase llamada **MainClase3** un método main que:

   1. Instancie los DAOs, construya los servicios correspondientes y un objeto **Flight** con un **Aircraft** configurado.

   2. Solicite al usuario que ingrese su nombre de usuario por consola y obtenga el User correspondiente a través de **UserService**.

   3. Consulte al usuario por consola si desea reservar el vuelo. Si responde "si", utilice el ReservationService para persistir la reserva e imprima *"Reserva guardada con éxito. ID: \<id\>"*. Caso contrario imprima "Operación cancelada" y finalice la ejecución.

   * Aplique el uso de DTOs para la transferencia de datos entre el método main y los servicios ¿Qué ventajas tiene este enfoque?

   * En caso de que no se encuentre un usuario, deberá imprimir *"Usuario no encontrado"*.

   * Ante cualquier error inesperado del sistema se deberá imprimir *"Error en el sistema: \<mensaje\>"* y finalizar la ejecución.

7. Analice el diseño resultante:

   1. Compare **MainClase2** con **MainClase3**: ¿Qué responsabilidades se extrajeron de Main y hacia dónde se movieron?

   2. **UserService** y **ReservationService** dependen de las interfaces **UserDao** y **ReservationDao** respectivamente y no de las implementaciones concretas. ¿Qué ventaja tiene esto si se quisiera cambiar el mecanismo de persistencia (por ejemplo, pasar de archivos a una base de datos)?

   3. La clase **Reservation** se construye con un Builder en lugar de un constructor con parámetros. ¿Qué ventaja aporta este patrón?

8. Desarrolle una nueva implementación del DAO de reservas llamada **ReservationDaoMemoryImpl** que almacene las reservas en una lista en memoria en lugar de un archivo. Verifique que puede usarla en MainClase3 correctamente.
