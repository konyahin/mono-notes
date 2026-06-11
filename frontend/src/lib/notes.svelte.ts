import { api } from "./api";

export type Note = {
    id: string,
    content: string,
}

type CreatedNote = {
    id: string
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
}

export const notes = new NotesStore()