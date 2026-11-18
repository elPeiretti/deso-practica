// Componente propio: no usa ninguna librería, solo Tailwind.
// Recibe un título, una descripción opcional y un `children` opcional para las acciones de la derecha.
export function PageHeader({ title, description, children }) {
  return (
    <div className="mb-8 flex flex-wrap items-end justify-between gap-4 border-b border-border pb-4">
      <div>
        <h1 className="text-3xl font-bold tracking-tight">{title}</h1>
        {description && <p className="mt-1 text-sm text-muted-foreground">{description}</p>}
      </div>
      {children && <div className="flex gap-2">{children}</div>}
    </div>
  );
}
