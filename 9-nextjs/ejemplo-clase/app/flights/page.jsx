import Link from "next/link";
import { PageHeader } from "@/components/page-header";
import { Badge } from "@/components/ui/badge";
import { buttonVariants } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { flights, formatPrice } from "@/lib/data";
import { cn } from "@/lib/utils";

// Página hecha con shadcn/ui: los componentes están copiados en components/ui
// (se agregan con `npx shadcn@latest add <componente>`) y se pueden modificar.
export default function FlightsPage() {
  return (
    // .shadcn aplica los colores de shadcn (ver globals.css)
    <div className="shadcn">
      <PageHeader title="Vuelos" description="Vuelos disponibles para reservar">
        {/* Links con estilo de botón: buttonVariants da las clases del Button de shadcn.
            cn() resuelve los conflictos entre clases (ej. border-transparent vs border-border),
            como hace el componente Button internamente */}
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
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Número</TableHead>
                <TableHead>Compañía</TableHead>
                <TableHead>Avión</TableHead>
                <TableHead className="text-right">Distancia</TableHead>
                <TableHead className="text-right">Precio</TableHead>
                <TableHead />
              </TableRow>
            </TableHeader>
            <TableBody>
              {flights.map((flight) => (
                <TableRow key={flight.number}>
                  <TableCell className="font-mono font-medium">{flight.number}</TableCell>
                  <TableCell>
                    <Badge variant="secondary">{flight.company}</Badge>
                  </TableCell>
                  <TableCell>{flight.aircraftName}</TableCell>
                  <TableCell className="text-right">{flight.length} km</TableCell>
                  <TableCell className="text-right">{formatPrice(flight.price)}</TableCell>
                  <TableCell className="text-right">
                    <Link
                      href={`/reservations/new?flight=${flight.number}`}
                      className={cn(buttonVariants({ variant: "outline", size: "sm" }))}
                    >
                      Reservar
                    </Link>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>
    </div>
  );
}
