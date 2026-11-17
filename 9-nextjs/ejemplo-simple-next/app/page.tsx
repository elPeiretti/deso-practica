import Link from "next/link";

const options = [
  { href: "/users", title: "Ver usuarios", description: "Listado de usuarios registrados" },
  { href: "/rooms", title: "Ver alojamientos", description: "Listado de alojamientos disponibles" },
];

export default function Home() {
  return (
    <>
      <h1 className="mb-6 text-3xl font-bold">Menú principal</h1>
      <div className="grid gap-4 sm:grid-cols-2">
        {options.map((option) => (
          <Link
            key={option.href}
            href={option.href}
            className="rounded-lg border border-black/10 p-6 transition-colors hover:bg-black/5 dark:border-white/15 dark:hover:bg-white/10"
          >
            <h2 className="text-xl font-semibold">{option.title}</h2>
            <p className="mt-1 text-sm opacity-70">{option.description}</p>
          </Link>
        ))}
      </div>
    </>
  );
}
