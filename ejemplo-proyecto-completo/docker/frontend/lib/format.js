// Funciones de formato. No acceden a la API, así que se pueden usar también en Client Components.
export const formatPrice = (price) =>
  new Intl.NumberFormat("es-AR", { style: "currency", currency: "USD" }).format(price);

// La API devuelve las fechas como Instant en ISO-8601 (por ejemplo "2026-12-01T03:00:00Z").
// Se muestran en la zona horaria de quien las ve: el formulario guarda la medianoche local.
export const formatDate = (isoDate) =>
  new Intl.DateTimeFormat("es-AR", { dateStyle: "long" }).format(new Date(isoDate));
