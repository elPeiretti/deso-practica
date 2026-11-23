// Funciones de formato. No acceden a la API, así que se pueden usar también en Client Components.
export const formatPrice = (price) =>
  new Intl.NumberFormat("es-AR", { style: "currency", currency: "USD" }).format(price);
