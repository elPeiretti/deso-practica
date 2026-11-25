// Capa de acceso a datos de vuelos. Las páginas y componentes llaman a estas funciones
// y no saben cómo se obtienen los datos.

// server-only hace que el build falle si un Client Component importa este archivo,
// para que el acceso a datos quede siempre en el servidor
import "server-only";
import { apiFetch } from "./client";

// GET /api/flights -> [{ number, company, aircraftName, length, price }]
export async function getFlights() {
  return apiFetch("/api/flights");
}
