/** @type {import('next').NextConfig} */
const nextConfig = {
  // Genera en .next/standalone un servidor con solo los archivos necesarios para la imagen de Docker
  output: "standalone",
  // Evita que `next dev` genere AGENTS.md
  agentRules: false,
  cacheComponents: true,
  partialPrefetching: true,
  turbopack: {
    rules: {
      "*.css": {
        loaders: ["@tailwindcss/turbopack"],
        as: "*.css",
      },
    },
  },
};

export default nextConfig;
