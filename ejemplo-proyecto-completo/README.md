# Ejemplo de proyecto completo

Sistema de turismo con frontend y API integrados:

- `api/`: API REST con Spring Boot y Spring Data JPA (base H2 en memoria), en http://localhost:8080.
- `frontend/`: aplicación Next.js, en http://localhost:3000.

## Cómo levantarlo

En una terminal, la API:

```bash
cd api
./mvnw spring-boot:run
```

En otra terminal, el frontend:

```bash
cd frontend
cp .env.example .env.local
npm install
npm run dev
```

Abrir http://localhost:3000.

## Cómo se comunican

| Página | Quién llama a la API | ¿Necesita CORS? |
|---|---|---|
| `/flights` | El servidor de Next (Server Component) | No |
| `/reservations/new` | El servidor de Next (Server Action) | No |
| `/reservations` (Mis reservas) | El navegador (`fetch` en `useEffect`) | Sí |

CORS es una regla del navegador: no deja que el JavaScript de una página lea respuestas de otro origen (otro protocolo, dominio o puerto) salvo que ese servidor lo permita con el header `Access-Control-Allow-Origin`. Entre dos servidores no aplica.

La API lo configura en `api/src/main/java/com/deso/practica/config/CorsConfig.java`, con los orígenes permitidos en `app.cors.allowed-origins` (`application.properties`).

### Probar qué pasa sin CORS

1. Comentar la anotación `@Configuration` de `CorsConfig` y reiniciar la API.
2. Abrir "Mis reservas": no carga, y en la consola del navegador aparece el error *blocked by CORS policy: No 'Access-Control-Allow-Origin' header*.
3. En la pestaña Network del navegador, el request a `/api/users` tiene respuesta 200, y en el log de la API aparece el SQL que se ejecutó: el request llegó y se procesó. Lo que bloquea el navegador es que el JavaScript lea la respuesta.
4. `/flights` y `/reservations/new` siguen funcionando, porque los requests los hace el servidor de Next.
5. Volver a activar `@Configuration` y reiniciar la API: "Mis reservas" funciona.

Si en cambio CORS está configurado pero el origen no está permitido (por ejemplo, si se cambia `app.cors.allowed-origins`), Spring rechaza el request con un 403 antes de llegar al controller.
