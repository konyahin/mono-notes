import { api } from "./api";

export type Note = {
    id: string,
    content: string,
}

class NotesStore {
    notes = $state<Note[]>([])
    loading = $state(false)

    async refresh() {
        this.loading = true
        try {
            this.notes = await api.get("/notes")
        } finally {
            this.loading = false
        }
    }

    async add(content: string) {
        await api.post("/notes", { content: content })
        await this.refresh()
    }
}

export const notes = new NotesStore()