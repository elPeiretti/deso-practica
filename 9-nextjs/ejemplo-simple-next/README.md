# Ejemplo simple Next.js

Solución del ejercicio 3 de la clase 9: un menú principal con "Ver usuarios" y "Ver alojamientos" que, al entrar a `/users` o `/rooms`, busca los datos y los muestra.

```bash
npm install
npm run dev
```

Abrir http://localhost:3000.

## Estructura

- `app/layout.tsx`: layout compartido con la navegación.
- `app/page.tsx`: menú principal.
- `app/users/page.tsx`: Server Component asíncrono que busca los usuarios en [JSONPlaceholder](https://jsonplaceholder.typicode.com/users).
- `app/users/loading.tsx`: estado de carga de toda la página (Next.js lo envuelve en un `<Suspense>`).
- `app/rooms/page.tsx`: usa `<Suspense>` explícito; el título se muestra al instante y solo la lista (`room-list.tsx`) espera los datos.
- `lib/data.ts`: funciones de acceso a datos. Los alojamientos están definidos localmente porque JSONPlaceholder no los tiene. Ambas funciones agregan una demora de 1,5 s para que se vea el estado de carga.
