import { getUsers } from "@/lib/data";

// Server Component asíncrono: los datos se buscan en el servidor al entrar a /users.
// Mientras se espera, Next.js muestra app/users/loading.jsx
export default async function UsersPage() {
  const users = await getUsers();

  return (
    <>
      <h1 className="mb-6 text-3xl font-bold">Usuarios</h1>
      <table className="w-full text-left">
        <thead>
          <tr className="border-b border-black/10 dark:border-white/15">
            <th className="py-2">Usuario</th>
            <th className="py-2">Nombre</th>
            <th className="py-2">Email</th>
            <th className="py-2">Ciudad</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => (
            <tr key={user.id} className="border-b border-black/5 dark:border-white/10">
              <td className="py-2 font-mono">{user.username}</td>
              <td className="py-2">{user.name}</td>
              <td className="py-2">{user.email}</td>
              <td className="py-2">{user.address.city}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </>
  );
}
