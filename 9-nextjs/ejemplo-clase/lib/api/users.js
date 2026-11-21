import "server-only";
import { users } from "@/lib/data";

// GET /api/users -> [{ id, username }]
export async function getUsers() {
  return users;
}
