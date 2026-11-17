// Datos de ejemplo con la misma forma que las respuestas de la API de las clases 7 y 8.
// Más adelante se reemplazan por llamadas reales a la API.

// UserResponse (GET /api/users)
export type User = {
  id: string;
  username: string;
};

// FlightResponse (GET /api/flights)
export type Flight = {
  number: string;
  company: string;
  aircraftName: string;
  length: number;
  price: number;
};

// CreateReservationRequest (POST /api/reservations)
export type CreateReservationRequest = {
  username: string;
  flightNumber: string;
  date: string; // Instant en formato ISO-8601
};

// Mismos datos que data.sql
export const users: User[] = [
  { id: "d5c88ca9-5235-4006-83a6-344775c41281", username: "karen" },
  { id: "e5b88627-79da-4ca0-bc53-175bcb825710", username: "fede" },
];

// price = FUEL_PRICE_PER_LITER (1.32) * length * litersPerKm, como en Flight.calculatePrice()
export const flights: Flight[] = [
  { number: "ABC123", company: "FlyDeso", aircraftName: "Boeing 747", length: 1200, price: 17424 },
  { number: "XYZ789", company: "FlyDeso", aircraftName: "Airbus A320", length: 600, price: 1980 },
  { number: "LAT456", company: "LatAir", aircraftName: "Embraer 190", length: 900, price: 3682.8 },
];

export const formatPrice = (price: number) =>
  new Intl.NumberFormat("es-AR", { style: "currency", currency: "USD" }).format(price);
