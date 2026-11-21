"use client"; // Los error boundaries tienen que ser Client Components

// Se muestra si una página lanza un error, por ejemplo si la API no responde o devuelve un 500.
// El layout (la barra de navegación) se sigue viendo.
// En producción, error.message no trae el mensaje original para no exponer detalles internos:
// el detalle queda en los logs del servidor.
export default function Error({ retry }) {
  return (
    <div className="rounded-xl border border-border p-6">
      <h1 className="text-xl font-semibold">No se pudieron cargar los datos</h1>
      <p className="mt-1 text-sm text-muted-foreground">
        Puede que la API no esté disponible. Revisá que esté corriendo y probá de nuevo.
      </p>
      <button
        type="button"
        onClick={() => retry()}
        className="mt-4 rounded-lg bg-black px-3 py-1.5 text-sm font-medium text-white hover:bg-black/80"
      >
        Reintentar
      </button>
    </div>
  );
}
