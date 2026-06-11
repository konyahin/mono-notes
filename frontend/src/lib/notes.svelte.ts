import { api } from "./api";

export type Note = {
    id: string,
    content: string,
    created: string,
}

type CreatedNote = {
    id: string,
    created: string
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
        let note = await api.post<CreatedNote>("/notes", { content: content })
        this.notes.push({
            ...note,
            content: content
        })
    }

    async search(query: string) {
        this.loading = true
        try {
            this.notes = await api.get(`/notes?q=${encodeURIComponent(query)}`)
        } finally {
            this.loading = false
        }
    }
}

export const notes = new NotesStore()