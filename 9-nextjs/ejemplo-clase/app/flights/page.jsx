import { Suspense } from "react";
import Link from "next/link";
import { PageHeader } from "@/components/page-header";
import { buttonVariants } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { cn } from "@/lib/utils";
import { FlightsTable, FlightsTableSkeleton } from "./flights-table";

// Página hecha con shadcn/ui: los componentes están copiados en components/ui
// (se agregan con `npx shadcn@latest add <componente>`) y se pueden modificar.
export default function FlightsPage() {
  return (
    // .shadcn aplica los colores de shadcn (ver globals.css)
    <div className="shadcn">
      <PageHeader title="Vuelos" description="Vuelos disponibles para reservar">
        <Link href="/reservations/new" className={cn(buttonVariants())}>
          Nueva reserva
        </Link>
      </PageHeader>

      <Card>
        <CardHeader>
          <CardTitle>Listado de vuelos</CardTitle>
          <CardDescription>
            Misma forma que la respuesta de <code>GET /api/flights</code>
          </CardDescription>
        </CardHeader>
        <CardContent>
          {/* Lo que está fuera del <Suspense> se muestra al instante;
              la tabla aparece cuando responde la API */}
          <Suspense fallback={<FlightsTableSkeleton />}>
            <FlightsTable />
          </Suspense>
        </CardContent>
      </Card>
    </div>
  );
}
