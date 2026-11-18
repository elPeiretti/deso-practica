import { getAccommodations } from "@/lib/data";

const currency = new Intl.NumberFormat("es-AR", { style: "currency", currency: "ARS" });

// Server Component asíncrono que busca los alojamientos
export default async function RoomList() {
  const accommodations = await getAccommodations();

  return (
    <ul className="grid gap-4">
      {accommodations.map((room) => (
        <li key={room.id} className="rounded-lg border border-black/10 p-4 dark:border-white/15">
          <div className="flex items-baseline justify-between gap-4">
            <h2 className="text-lg font-semibold">{room.name}</h2>
            <span className="font-mono">{currency.format(room.pricePerNight)} / noche</span>
          </div>
          <p className="text-sm opacity-70">
            {room.location.address}, {room.location.city}
          </p>
          <p className="mt-2 text-sm">{room.amenities.join(" · ")}</p>
        </li>
      ))}
    </ul>
  );
}
