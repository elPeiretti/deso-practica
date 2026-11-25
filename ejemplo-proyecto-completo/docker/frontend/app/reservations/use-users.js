import { useEffect, useState } from "react";
import { fetchFromApi } from "@/lib/browser-api";

// Custom hook: React permite crear hooks propios que combinan otros hooks (useState, useEffect)
// para sacar la lógica del componente y poder reutilizarla.
export function useUsers() {
  const [users, setUsers] = useState([]);
  const [error, setError] = useState(null);

  // Se ejecuta después del primer render y trae los usuarios para el select.
  useEffect(() => {
    const controller = new AbortController();
    fetchFromApi("/api/users", controller.signal)
      .then(setUsers)
      .catch((error) => {
        if (error.name !== "AbortError") setError(error.message);
      });

    // Cleanup: si el componente se desmonta (o se oculta) antes de que llegue la respuesta,
    // se cancela el request
    return () => controller.abort();
  }, []);

  return { users, error };
}
