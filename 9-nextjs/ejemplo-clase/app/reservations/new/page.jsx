import { Suspense } from "react";
import { PageHeader } from "@/components/page-header";
import { getFlights } from "@/lib/api/flights";
import { getUsers } from "@/lib/api/users";
import { ReservationForm } from "./reservation-form";

// La página es un Server Component: busca en la API los datos que necesita el formulario.
// El formulario es un Client Component porque maneja estado e interacción del usuario.
export default function NewReservationPage() {
  return (
    <>
      <PageHeader
        title="Nueva reserva"
        description="Formulario hecho con HeroUI. Todavía no se envía a la API."
      />
      {/* El título se muestra al instante; el formulario, cuando responde la API */}
      <Suspense fallback={<FormSkeleton />}>
        <ReservationFormWithData />
      </Suspense>
    </>
  );
}

async function ReservationFormWithData() {
  // Promise.all hace los dos requests en paralelo: con dos await seguidos,
  // el segundo request recién empezaría cuando termina el primero
  const [users, flights] = await Promise.all([getUsers(), getFlights()]);

  return <ReservationForm users={users} flights={flights} />;
}

function FormSkeleton() {
  return (
    <div className="grid gap-6 md:grid-cols-2">
      <div className="h-80 animate-pulse rounded-3xl bg-default" />
    </div>
  );
}
