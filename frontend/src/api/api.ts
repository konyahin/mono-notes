async function request<T>(path: string, options?: RequestInit): Promise<T> {
  const res = await fetch(`/api${path}`, options)

  if (!res.ok) throw new Error(`HTTP ${res.status} on ${path}`)
  if (res.status === 204) {
    return undefined as T
  }
  return res.json()
}

export const api = {
  get: <T>(path: string) => request<T>(path),
  post: <T>(path: string, data: unknown) =>
    request<T>(path, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
    }),
  delete: <T>(path: string) =>
    request<T>(path, { method: 'DELETE' }),
}
