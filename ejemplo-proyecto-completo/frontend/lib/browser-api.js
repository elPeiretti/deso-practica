// A diferencia de lib/api (solo servidor), se usa desde Client Components.

// Solo las variables con prefijo NEXT_PUBLIC_ llegan al navegador
const API_URL = process.env.NEXT_PUBLIC_API_URL;

// fetch desde el navegador. Si falla la conexión (API caída o error de CORS),
// fetch lanza un TypeError sin más detalle: el motivo solo aparece en la consola del navegador.
export async function fetchFromApi(path, signal) {
  let response;
  try {
    response = await fetch(`${API_URL}${path}`, { signal });
  } catch (error) {
    if (error.name === "AbortError") throw error;
    throw new Error(
      "No se pudo conectar con la API. Si está corriendo, revisá la consola del navegador: puede ser un error de CORS.",
    );
  }

  const data = await response.json().catch(() => null);
  if (!response.ok) {
    throw new Error(data?.message ?? `La API respondió con error ${response.status}`);
  }
  return data;
}
