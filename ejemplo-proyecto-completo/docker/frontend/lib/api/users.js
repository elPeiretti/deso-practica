import "server-only";
import { apiFetch } from "./client";

// GET /api/users -> [{ id, username }]
export async function getUsers() {
  return apiFetch("/api/users");
}
