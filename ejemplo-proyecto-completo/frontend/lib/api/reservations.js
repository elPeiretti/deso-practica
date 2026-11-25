import "server-only";
import { apiFetch } from "./client";

// POST /api/reservations
// Recibe { username, flightNumber, date } y devuelve { reservationId, ownerUsername, serviceName, price, date }.
// Si el usuario o el vuelo no existen, la API responde 404 y se lanza un error con su mensaje.
export async function createReservation({ username, flightNumber, date }) {
  return apiFetch("/api/reservations", {
    method: "POST",
    body: { username, flightNumber, date },
  });
}
