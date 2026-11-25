import { useEffect, useState } from "react";
import { fetchFromApi } from "@/lib/browser-api";

// Custom hook: React permite crear hooks propios que combinan otros hooks (useState, useEffect)
// para sacar la lógica del componente y poder reutilizarla.
export function useReservations(username) {
  // Última respuesta de la API: { username, reservations } o { username, message } si hubo error.
  // Guarda el username para saber si corresponde al usuario elegido ahora.
  const [result, setResult] = useState(null);

  // Se ejecuta cada vez que cambia username y trae sus reservas
  useEffect(() => {
    if (!username) return;

    const controller = new AbortController();
    fetchFromApi(`/api/reservations?username=${encodeURIComponent(username)}`, controller.signal)
      .then((reservations) => setResult({ username, reservations }))
      .catch((error) => {
        if (error.name !== "AbortError") setResult({ username, message: error.message });
      });

    // Cleanup: corre antes de la próxima ejecución del efecto. Si se cambia de usuario
    // antes de que llegue la respuesta anterior, se cancela ese request; si no, podría
    // llegar después y mostrar las reservas del usuario equivocado.
    return () => controller.abort();
  }, [username]);

  // El estado se deriva de username y result: si la última respuesta es de otro usuario,
  // todavía se está cargando la del usuario elegido
  if (!username) return { status: "idle" };
  if (result?.username !== username) return { status: "loading" };
  if (result.message) return { status: "error", message: result.message };
  return { status: "success", reservations: result.reservations };
}
