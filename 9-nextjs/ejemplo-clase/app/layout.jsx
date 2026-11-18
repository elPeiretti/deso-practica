import Link from "next/link";
import { Geist, Geist_Mono } from "next/font/google";
import "./globals.css";

const geistSans = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});

export const metadata = {
  title: "Turismo DESO",
  description: "Ejemplo de clase 9 - Next.js con shadcn/ui y HeroUI",
};

export default function RootLayout({ children }) {
  return (
    <html lang="es" className={`${geistSans.variable} ${geistMono.variable} h-full antialiased`}>
      <body className="flex min-h-full flex-col">
        <header className="border-b border-border bg-white">
          <nav className="mx-auto flex max-w-4xl items-center gap-6 px-4 py-4 text-sm">
            <Link href="/" className="text-base font-semibold">
              ✈️ Turismo DESO
            </Link>
            <Link href="/flights" className="hover:underline">
              Vuelos
            </Link>
            <Link href="/reservations/new" className="hover:underline">
              Nueva reserva
            </Link>
          </nav>
        </header>
        <main className="mx-auto w-full max-w-4xl flex-1 px-4 py-8">{children}</main>
      </body>
    </html>
  );
}
