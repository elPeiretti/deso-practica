import "server-only";
import { flights, users } from "@/lib/data";

// POST /api/reservations
// Recibe { username, flightNumber, date } y devuelve { reservationId, ownerUsername, serviceName, price, date }.
// Igual que la API, si el usuario o el vuelo no existen lanza un error con el mensaje del 404.
export async function createReservation({ username, flightNumber, date }) {
  const user = users.find((u) => u.username === username);
  if (!user) {
    throw new Error(`User with ${username} username not found`);
  }
  const flight = flights.find((f) => f.number === flightNumber);
  if (!flight) {
    throw new Error(`Flight with ${flightNumber} number not found`);
  }

  return {
    reservationId: crypto.randomUUID(),
    ownerUsername: user.username,
    serviceName: `Vuelo ${flight.number} de ${flight.company}`,
    price: flight.price,
    date,
  };
}
