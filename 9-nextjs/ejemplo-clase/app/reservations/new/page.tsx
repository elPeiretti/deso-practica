import { Suspense } from "react";
import { PageHeader } from "@/components/page-header";
import { flights, users } from "@/lib/data";
import { ReservationForm } from "./reservation-form";

// La página es un Server Component; el formulario es un Client Component
// porque maneja estado e interacción del usuario.
export default function NewReservationPage() {
  return (
    <>
      <PageHeader
        title="Nueva reserva"
        description="Formulario hecho con HeroUI. Todavía no se envía a la API."
      />
      {/* ReservationForm lee la query string (?flight=...), así que necesita un <Suspense> */}
      <Suspense>
        <ReservationForm users={users} flights={flights} />
      </Suspense>
    </>
  );
}
