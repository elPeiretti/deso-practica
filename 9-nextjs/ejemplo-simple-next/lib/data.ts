import { connection } from "next/server";

// Forma de los usuarios que devuelve https://jsonplaceholder.typicode.com/users
// (solo los campos que usamos)
export type User = {
  id: number;
  name: string;
  username: string;
  email: string;
  address: {
    street: string;
    city: string;
  };
};

// Alojamientos basados en el dominio del sistema de turismo de las clases anteriores
export type Accommodation = {
  id: string;
  name: string;
  pricePerNight: number;
  location: {
    city: string;
    address: string;
  };
  amenities: string[];
};

// JSONPlaceholder no tiene alojamientos, así que los definimos acá
const accommodations: Accommodation[] = [
  {
    id: "1",
    name: "Hotel Puerto",
    pricePerNight: 85000,
    location: { city: "Santa Fe", address: "Dique 1, Puerto" },
    amenities: ["WiFi", "Desayuno", "Pileta"],
  },
  {
    id: "2",
    name: "Cabañas del Lago",
    pricePerNight: 120000,
    location: { city: "Bariloche", address: "Av. Bustillo km 8" },
    amenities: ["WiFi", "Parrilla", "Vista al lago"],
  },
  {
    id: "3",
    name: "Hostel Centro",
    pricePerNight: 30000,
    location: { city: "Córdoba", address: "Dean Funes 300" },
    amenities: ["WiFi", "Cocina compartida"],
  },
];

// Demora artificial para que se llegue a ver el estado de carga
const DELAY_MS = 1500;

function sleep(ms: number) {
  return new Promise((resolve) => setTimeout(resolve, ms));
}

export async function getUsers(): Promise<User[]> {
  // Hace que la página se renderice en cada request (y no una sola vez en el build),
  // así se ve el estado de carga
  await connection();
  await sleep(DELAY_MS);

  const res = await fetch("https://jsonplaceholder.typicode.com/users");
  if (!res.ok) {
    throw new Error(`Error al buscar usuarios: ${res.status}`);
  }
  return res.json();
}

export async function getAccommodations(): Promise<Accommodation[]> {
  await connection();
  await sleep(DELAY_MS);
  return accommodations;
}
