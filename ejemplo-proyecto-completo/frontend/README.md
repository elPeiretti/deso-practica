# Proyecto completo - Frontend (Next.js)

Frontend del sistema de turismo. Consume la API de Spring Boot de `../api`, que tiene que estar corriendo en http://localhost:8080.

```bash
cp .env.example .env.local
npm install
npm run dev
```

Abrir http://localhost:3000.

## Variables de entorno

Están en `.env.example`, que se copia como `.env.local` (este último no se sube al repositorio):

- `API_URL`: URL de la API para los requests que hace el servidor de Next. Solo existe en el servidor.
- `NEXT_PUBLIC_API_URL`: URL de la API para los requests que hace el navegador. El prefijo `NEXT_PUBLIC_` hace que se incluya en el JavaScript del navegador.

## Páginas

- `/flights`: listado de vuelos hecho con [shadcn/ui](https://ui.shadcn.com) (`Table`, `Card`, `Badge`, `Button`). Los datos los pide un Server Component.
- `/reservations/new`: formulario de reserva hecho con [HeroUI](https://heroui.com) (ex NextUI): `Select`, `DatePicker`, `Form`, `Alert`. Se envía con una Server Action que llama a `POST /api/reservations` y muestra la reserva creada. El botón "Reservar" de `/flights` preselecciona el vuelo con `?flight=`.
- `/reservations`: "Mis reservas". Un Client Component pide los usuarios y las reservas desde el navegador, con `fetch` dentro de `useEffect`. Es la única página que necesita CORS en la API.

## Dos formas de pedir datos a la API

- **Desde el servidor de Next** (`lib/api`, Server Components y Server Actions): el navegador solo le habla a Next, y Next le habla a la API. Es un request entre servidores, así que CORS no aplica. La URL de la API no llega al navegador.
- **Desde el navegador** (`app/reservations/my-reservations.jsx`): el navegador le habla directo a la API, que está en otro origen (`localhost:8080`). La API tiene que responder con el header `Access-Control-Allow-Origin` (ver `CorsConfig` en la API). El estado de carga y los errores se manejan a mano con `useState`.

## Componentes

- **shadcn/ui**: no es una dependencia, sino que copia el código de cada componente en `components/ui`, así que se puede modificar. Se agregan con `npx shadcn@latest add <componente>`.
- **HeroUI**: es una dependencia normal (`@heroui/react`); los componentes se importan desde el paquete.
- **Componente propio**: `components/page-header.jsx`, hecho solo con Tailwind y usado en todas las páginas.

Las dos librerías usan algunas variables CSS con el mismo nombre (`--muted`, `--accent`, ...) con distinto significado. En `app/globals.css` está explicado cómo conviven: los valores de shadcn se aplican dentro de un elemento con la clase `.shadcn`.
