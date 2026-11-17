// Next.js envuelve automáticamente page.tsx en un <Suspense> con este componente como fallback
export default function Loading() {
  return (
    <div className="flex items-center gap-3">
      <div className="h-5 w-5 animate-spin rounded-full border-2 border-current border-t-transparent" />
      <p>Cargando usuarios...</p>
    </div>
  );
}
