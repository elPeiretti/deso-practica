"use server";

// Server Action: función que se ejecuta en el servidor y se puede llamar desde un Client Component.
// El formulario no accede a los datos directamente: llama a esta acción, y la acción usa lib/api.

import { createReservation } from "@/lib/api/reservations";

// Recibe el estado anterior (por useActionState) y los datos del formulario.
// Devuelve el nuevo estado, que el formulario usa para mostrar el resultado o los errores.
export async function createReservationAction(prevState, formData) {
  const request = {
    username: String(formData.get("username") ?? ""),
    flightNumber: String(formData.get("flightNumber") ?? ""),
    date: String(formData.get("date") ?? ""),
  };

  // Cualquiera puede llamar a una Server Action con un POST, salteando la validación
  // del navegador. Por eso los datos se vuelven a validar acá.
  const errors = {};
  if (!request.username) errors.username = "Elegí un usuario.";
  if (!request.flightNumber) errors.flightNumber = "Elegí un vuelo.";
  if (!request.date || Number.isNaN(Date.parse(request.date))) errors.date = "Elegí una fecha.";
  if (Object.keys(errors).length > 0) {
    return { errors };
  }

  try {
    // El request a POST /api/reservations lo hace el servidor de Next, no el navegador:
    // por eso este formulario funciona aunque la API no tenga CORS configurado
    const reservation = await createReservation(request);
    return { reservation };
  } catch (error) {
    return { message: error.message };
  }
}
