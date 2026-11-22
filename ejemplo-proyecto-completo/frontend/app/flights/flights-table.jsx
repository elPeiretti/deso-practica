import Link from "next/link";
import { Badge } from "@/components/ui/badge";
import { buttonVariants } from "@/components/ui/button";
import { Skeleton } from "@/components/ui/skeleton";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { getFlights } from "@/lib/api/flights";
import { formatPrice } from "@/lib/format";
import { cn } from "@/lib/utils";

function FlightsTableHeader() {
  return (
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
  );
}

// Server Component asíncrono: llama a la API en el servidor y devuelve el HTML ya armado.
// El navegador no hace ningún request a la API ni descarga este código.
export async function FlightsTable() {
  const flights = await getFlights();

  return (
    <Table>
      <FlightsTableHeader />
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
  );
}

// Se muestra mientras FlightsTable espera la respuesta de la API
export function FlightsTableSkeleton() {
  return (
    <Table>
      <FlightsTableHeader />
      <TableBody>
        {[1, 2, 3].map((i) => (
          <TableRow key={i}>
            <TableCell colSpan={6}>
              <Skeleton className="h-7" />
            </TableCell>
          </TableRow>
        ))}
      </TableBody>
    </Table>
  );
}
