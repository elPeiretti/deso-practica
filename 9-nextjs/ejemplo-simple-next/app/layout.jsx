import Link from "next/link";
import { Geist } from "next/font/google";
import "./globals.css";

const geistSans = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

export const metadata = {
  title: "Ejemplo simple Next.js",
  description: "Ejercicio 3 - Clase 9 DESO",
};

export default function RootLayout({ children }) {
  return (
    <html lang="es" className={`${geistSans.variable} h-full antialiased`}>
      <body className="min-h-full flex flex-col font-sans">
        {/* El layout se mantiene entre navegaciones: solo cambia {children} */}
        <header className="border-b border-black/10 dark:border-white/15">
          <nav className="mx-auto flex max-w-3xl gap-6 px-4 py-4">
            <Link href="/" className="font-semibold">
              Turismo DESO
            </Link>
            <Link href="/users" className="hover:underline">
              Usuarios
            </Link>
            <Link href="/rooms" className="hover:underline">
              Alojamientos
            </Link>
          </nav>
        </header>
        <main className="mx-auto w-full max-w-3xl flex-1 px-4 py-8">{children}</main>
      </body>
    </html>
  );
}
