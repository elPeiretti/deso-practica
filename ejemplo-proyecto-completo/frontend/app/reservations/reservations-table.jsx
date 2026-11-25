import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { formatDate, formatPrice } from "@/lib/format";

export function ReservationsTable({ reservations }) {
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
        {reservations.map((reservation) => (
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
