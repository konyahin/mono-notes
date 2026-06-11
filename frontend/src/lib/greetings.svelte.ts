import { api } from "./api";

class GreetingsStore {
    greetings = $state<string[]>([])
    loading = $state(false)

    async refresh() {
        this.loading = true
        try {
            this.greetings = await api.get("/greetings")
        } finally {
            this.loading = false
        }
    }

    async add(greeting: string) {
        await api.post("/greetings", { body: greeting })
        await this.refresh()
    }
}

export const greetings = new GreetingsStore()