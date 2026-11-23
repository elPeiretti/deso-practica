# Ejemplo de proyecto completo con Docker

El mismo sistema de turismo de `ejemplo-proyecto-completo`, pero levantado con Docker y con Postgres como base de datos en lugar de H2.

Cada parte corre en su propio contenedor, definidos en `compose.yaml`:

| Servicio | Imagen | Puerto |
|---|---|---|
| `db` | `postgres:18` (imagen oficial) | 5432 |
| `api` | Se construye con `api/Dockerfile` | 8080 |
| `frontend` | Se construye con `frontend/Dockerfile` | 3000 |

## Cómo levantarlo

Hace falta tener instalado [Docker Desktop](https://www.docker.com/products/docker-desktop/).

```bash
cp .env.example .env
docker compose up --build
```

Abrir http://localhost:3000.

- `docker compose logs -f api`: ver los logs de un servicio.
- `docker compose down`: frena y borra los contenedores. Los datos de Postgres quedan en el volumen `db-data`.
- `docker compose down -v`: también borra el volumen, y con él los datos.

## Qué cambia respecto del ejemplo sin Docker

### API

- `pom.xml`: el driver de Postgres reemplaza a H2, que queda solo para los tests (`src/test/resources/application-test.properties`), así `./mvnw test` no necesita Docker.
- `application.properties`: apunta a Postgres, y la configuración llega por variables de entorno de dos formas:
  - `${DB_USERNAME}` y `${DB_PASSWORD}`: las credenciales no se guardan en el repositorio. No tienen valor por defecto, así que si faltan la API no arranca.
  - `SPRING_DATASOURCE_URL`: Spring Boot convierte automáticamente cada variable de entorno en la propiedad equivalente (`SPRING_DATASOURCE_URL` → `spring.datasource.url`), así que no hace falta nombrarla en `application.properties`.
- Para correr la API fuera de Docker, con un Postgres local, hay que definir `DB_USERNAME` y `DB_PASSWORD`, por ejemplo en la configuración de ejecución de IntelliJ.
- `ddl-auto=update` en lugar de `create-drop`, para no borrar los datos al reiniciar.
- `data.sql`: `MERGE INTO ... KEY` es sintaxis de H2; en Postgres se usa `INSERT ... ON CONFLICT DO NOTHING`. Además hace falta `spring.sql.init.mode=always`, porque Spring solo ejecuta `data.sql` por defecto con bases embebidas.

### Frontend

- `next.config.mjs`: `output: "standalone"` genera un servidor con solo lo necesario para correrlo, y la imagen queda más chica.
- Las dos URLs de la API tienen valores distintos:
  - `API_URL=http://api:8080`: la usa el servidor de Next, que corre dentro de la red de Docker. Ahí cada servicio se encuentra por su nombre, así que `localhost` sería el propio contenedor del frontend.
  - `NEXT_PUBLIC_API_URL=http://localhost:8080`: la usa el navegador, que corre fuera de Docker y llega a la API por el puerto publicado. Next la copia en el JavaScript al compilar, por eso en `compose.yaml` va en `build.args` y no en `environment`.

## Consultar la base de datos

En lugar de la consola de H2:

```bash
docker compose exec db psql -U deso -d reservations_db
```

Por ejemplo, `select * from reservations;`. Con `\dt` se listan las tablas y con `\q` se sale.

## Probar que los datos persisten

1. Crear una reserva desde http://localhost:3000.
2. `docker compose down` y `docker compose up`.
3. La reserva sigue en "Mis reservas". Con `docker compose down -v` se borra.
