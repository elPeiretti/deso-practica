import { Suspense } from "react";
import { PageHeader } from "@/components/page-header";
import { MyReservations } from "./my-reservations";

// A diferencia de /flights y /reservations/new, esta página no busca datos en el servidor:
// los pide el navegador con fetch dentro de un useEffect (ver my-reservations.jsx).
// Por eso necesita que la API tenga CORS configurado.
export default function ReservationsPage() {
  return (
    // .shadcn aplica los colores de shadcn (ver globals.css)
    <div className="shadcn">
      <PageHeader
        title="Mis reservas"
        description="Los datos se piden desde el navegador con useEffect: requiere CORS en la API."
      />
      {/* MyReservations lee ?username= con useSearchParams, que necesita un <Suspense> */}
      <Suspense>
        <MyReservations />
      </Suspense>
    </div>
  );
}
