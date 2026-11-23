// Cliente HTTP para la API de Spring Boot. Lo usan las funciones de lib/api.
//
// Corre solo en el servidor de Next (Server Components y Server Actions): el navegador
// nunca le habla a la API directamente, así que acá no hace falta CORS.
// La página "Mis reservas" es la excepción: llama a la API desde el navegador (ver app/reservations).
import "server-only";
import { connection } from "next/server";

// Sin el prefijo NEXT_PUBLIC_, la variable solo existe en el servidor y no llega al navegador
const API_URL = process.env.API_URL;

// Hace el request y devuelve el body ya parseado.
// Si la API responde con un error (4xx o 5xx), lanza un Error con el message del ErrorResponse.
export async function apiFetch(path, { body, ...options } = {}) {
  // Los datos se piden siempre al recibir el request, nunca durante `next build`:
  // en Docker, API_URL recién existe cuando arranca el contenedor
  await connection();

  if (!API_URL) {
    throw new Error("Falta la variable de entorno API_URL (ver .env.example)");
  }

  let response;
  try {
    response = await fetch(`${API_URL}${path}`, {
      ...options,
      headers: body === undefined ? undefined : { "Content-Type": "application/json" },
      body: body === undefined ? undefined : JSON.stringify(body),
    });
  } catch {
    // fetch solo lanza un error si no hubo respuesta, por ejemplo si la API no está corriendo
    throw new Error("No se pudo conectar con la API");
  }

  // Si la respuesta no trae JSON (por ejemplo, un error del servidor), data queda en null
  const data = await response.json().catch(() => null);
  if (!response.ok) {
    throw new Error(data?.message ?? `La API respondió con error ${response.status}`);
  }
  return data;
}
