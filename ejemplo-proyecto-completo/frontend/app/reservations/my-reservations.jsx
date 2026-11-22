"use client";

// Ejemplo de datos pedidos desde el navegador, en contraste con el resto de la app,
// donde los pide el servidor de Next (Server Components y Server Actions).
// Se debe configurar CORS correctamente para que la página funcione.

import { useEffect, useState } from "react";
import { useRouter, useSearchParams } from "next/navigation";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { formatDate, formatPrice } from "@/lib/format";

// Solo las variables con prefijo NEXT_PUBLIC_ llegan al navegador
const API_URL = process.env.NEXT_PUBLIC_API_URL;

// fetch desde el navegador. Si falla la conexión (API caída o error de CORS),
// fetch lanza un TypeError sin más detalle: el motivo solo aparece en la consola del navegador.
async function fetchFromApi(path, signal) {
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

export function MyReservations() {
  const router = useRouter();

  // El usuario elegido sale de la URL (?username=).
  const username = useSearchParams().get("username") ?? "";

  const [users, setUsers] = useState([]);
  const [usersError, setUsersError] = useState(null);
  // Última respuesta de la API: { username, reservations } o { username, message } si hubo error.
  // Guarda el username para saber si corresponde al usuario elegido ahora.
  const [result, setResult] = useState(null);

  // Efecto 1: se ejecuta después del primer render y trae los usuarios para el select.
  useEffect(() => {
    const controller = new AbortController();
    fetchFromApi("/api/users", controller.signal)
      .then(setUsers)
      .catch((error) => {
        if (error.name !== "AbortError") setUsersError(error.message);
      });

    // Cleanup: si el componente se desmonta (o se oculta) antes de que llegue la respuesta,
    // se cancela el request
    return () => controller.abort();
  }, []);

  // Efecto 2: se ejecuta cada vez que cambia username y trae sus reservas
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

  // Elegir un usuario cambia la URL; eso cambia username y dispara el efecto 2.
  // replace reemplaza la entrada actual del historial en vez de agregar una nueva
  // (como router.push), así el botón Atrás no pasa por cada usuario elegido.
  const onUserChange = (e) => {
    const value = e.target.value;
    router.replace(value ? `/reservations?username=${encodeURIComponent(value)}` : "/reservations", {
      scroll: false,
    });
  };

  // El estado se deriva de username y result: si la última respuesta es de otro usuario,
  // todavía se está cargando la del usuario elegido
  let status;
  if (!username) status = "idle";
  else if (result?.username !== username) status = "loading";
  else if (result.message) status = "error";
  else status = "success";

  return (
    <Card>
      <CardHeader>
        <CardTitle>Reservas por usuario</CardTitle>
        <CardDescription>
          Datos de <code>GET /api/reservations?username=…</code>, pedidos desde el navegador
        </CardDescription>
      </CardHeader>
      <CardContent className="flex flex-col gap-6">
        <div className="flex flex-col gap-1.5">
          <label htmlFor="username" className="text-sm font-medium">
            Usuario
          </label>
          <select
            id="username"
            value={username}
            onChange={onUserChange}
            disabled={users.length === 0}
            className="h-9 w-full max-w-xs rounded-md border border-input bg-transparent px-3 text-sm disabled:opacity-50"
          >
            <option value="">
              {usersError ? "Sin usuarios" : users.length === 0 ? "Cargando usuarios..." : "Elegí un usuario"}
            </option>
            {users.map((user) => (
              <option key={user.id} value={user.username}>
                {user.username}
              </option>
            ))}
          </select>
          {usersError && <p className="text-sm text-destructive">{usersError}</p>}
        </div>

        <ReservationsResult status={status} result={result} />
      </CardContent>
    </Card>
  );
}

function ReservationsResult({ status, result }) {
  if (status === "idle") {
    return <p className="text-sm text-muted-foreground">Elegí un usuario para ver sus reservas.</p>;
  }
  if (status === "loading") {
    return <div className="h-24 animate-pulse rounded-md bg-muted" />;
  }
  if (status === "error") {
    return <p className="text-sm text-destructive">{result.message}</p>;
  }
  if (result.reservations.length === 0) {
    return <p className="text-sm text-muted-foreground">Este usuario todavía no tiene reservas.</p>;
  }

  return (
    <Table>
      <TableHeader>
        <TableRow>
          <TableHead>Número</TableHead>
          <TableHead>Servicio</TableHead>
          <TableHead>Fecha</TableHead>
          <TableHead className="text-right">Precio</TableHead>
        </TableRow>
      </TableHeader>
      <TableBody>
        {result.reservations.map((reservation) => (
          <TableRow key={reservation.reservationId}>
            <TableCell className="font-mono">{reservation.reservationId}</TableCell>
            <TableCell>{reservation.serviceName}</TableCell>
            <TableCell>{formatDate(reservation.date)}</TableCell>
            <TableCell className="text-right">{formatPrice(reservation.price)}</TableCell>
          </TableRow>
        ))}
      </TableBody>
    </Table>
  );
}
