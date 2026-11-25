# Etapa 1: instala las dependencias (se reutiliza mientras no cambie package-lock.json)
FROM node:24-alpine AS deps
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm ci

# Etapa 2: compila la aplicación
FROM node:24-alpine AS build
WORKDIR /app
COPY --from=deps /app/node_modules ./node_modules
COPY . .
# Next copia las variables NEXT_PUBLIC_ en el JavaScript del navegador al compilar,
# por eso esta llega como argumento del build y no como variable de entorno del contenedor
ARG NEXT_PUBLIC_API_URL
RUN npm run build

# Etapa 3: la imagen final solo lleva el servidor standalone y los archivos estáticos
FROM node:24-alpine
WORKDIR /app
ENV NODE_ENV=production
# Escuchar en todas las interfaces para que se pueda acceder desde fuera del contenedor
ENV HOSTNAME=0.0.0.0
COPY --from=build /app/.next/standalone ./
COPY --from=build /app/.next/static ./.next/static
EXPOSE 3000
CMD ["node", "server.js"]
