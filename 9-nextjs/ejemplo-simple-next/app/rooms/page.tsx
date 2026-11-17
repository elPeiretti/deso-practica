import { Suspense } from "react";
import RoomList from "./room-list";

// A diferencia de /users, acá usamos <Suspense> en vez de loading.tsx:
// el título se muestra al instante y solo la lista espera a los datos
export default function RoomsPage() {
  return (
    <>
      <h1 className="mb-6 text-3xl font-bold">Alojamientos</h1>
      <Suspense fallback={<RoomListSkeleton />}>
        <RoomList />
      </Suspense>
    </>
  );
}

function RoomListSkeleton() {
  return (
    <ul className="grid gap-4">
      {[1, 2, 3].map((i) => (
        <li key={i} className="h-24 animate-pulse rounded-lg bg-black/5 dark:bg-white/10" />
      ))}
    </ul>
  );
}
