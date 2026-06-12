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
    lastAdded = $state(Date.now())

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
        this.lastAdded = Date.now()
    }

    async search(query: string, archived: boolean = false) {
        this.notes = await api.get(`/notes?q=${encodeURIComponent(query)}&archived=${archived}`)
    }

    async archive(id: string) {
        await api.post(`/notes/${id}/archive`, null)

        var index = this.notes.findIndex((n) => n.id === id)
        if (index !== -1) {
            this.notes.splice(index, 1);
        }
    }
}

export const notes = new NotesStore()