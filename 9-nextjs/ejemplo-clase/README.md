# Ejemplo de clase - Next.js

Frontend del sistema de turismo de las clases anteriores. Todavía no consume la API: usa datos de ejemplo (`lib/data.js`) con la misma forma que las respuestas de la API de las clases 7 y 8.

```bash
npm install
npm run dev
```

Abrir http://localhost:3000.

## Páginas

- `/flights`: listado de vuelos hecho con [shadcn/ui](https://ui.shadcn.com) (`Table`, `Card`, `Badge`, `Button`).
- `/reservations/new`: formulario de reserva hecho con [HeroUI](https://heroui.com) (ex NextUI): `Select`, `DatePicker`, `Form`, `Alert`. Al enviarlo muestra el body que se mandaría a `POST /api/reservations`. El botón "Reservar" de `/flights` preselecciona el vuelo con `?flight=`.

## Componentes

- **shadcn/ui**: no es una dependencia, sino que copia el código de cada componente en `components/ui`, así que se puede modificar. Se agregan con `npx shadcn@latest add <componente>`.
- **HeroUI**: es una dependencia normal (`@heroui/react`); los componentes se importan desde el paquete.
- **Componente propio**: `components/page-header.jsx`, hecho solo con Tailwind y usado en las dos páginas.

Las dos librerías usan algunas variables CSS con el mismo nombre (`--muted`, `--accent`, ...) con distinto significado. En `app/globals.css` está explicado cómo conviven: los valores de shadcn se aplican dentro de un elemento con la clase `.shadcn`.
