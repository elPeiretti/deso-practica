"use client";

// Ejemplo de datos pedidos desde el navegador, en contraste con el resto de la app,
// donde los pide el servidor de Next (Server Components y Server Actions).
// Se debe configurar CORS correctamente para que la página funcione.

import { useRouter, useSearchParams } from "next/navigation";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Skeleton } from "@/components/ui/skeleton";
import { ReservationsTable } from "./reservations-table";
import { useReservations } from "./use-reservations";
import { useUsers } from "./use-users";

export function MyReservations() {
  const router = useRouter();

  // El usuario elegido sale de la URL (?username=).
  const username = useSearchParams().get("username") ?? "";

  const { users, error: usersError } = useUsers();
  const reservations = useReservations(username);

  // Elegir un usuario cambia la URL; eso cambia username y dispara el efecto de useReservations.
  // replace reemplaza la entrada actual del historial en vez de agregar una nueva
  // (como router.push), así el botón Atrás no pasa por cada usuario elegido.
  const onUserChange = (e) => {
    const value = e.target.value;
    router.replace(value ? `/reservations?username=${encodeURIComponent(value)}` : "/reservations", {
      scroll: false,
    });
  };

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

        <ReservationsResult {...reservations} />
      </CardContent>
    </Card>
  );
}

function ReservationsResult({ status, reservations, message }) {
  if (status === "idle") {
    return <p className="text-sm text-muted-foreground">Elegí un usuario para ver sus reservas.</p>;
  }
  if (status === "loading") {
    return <Skeleton className="h-24" />;
  }
  if (status === "error") {
    return <p className="text-sm text-destructive">{message}</p>;
  }
  if (reservations.length === 0) {
    return <p className="text-sm text-muted-foreground">Este usuario todavía no tiene reservas.</p>;
  }
  return <ReservationsTable reservations={reservations} />;
}
