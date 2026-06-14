import { api } from "./api";
import { db } from "./db";

export type Note = {
    id: string,
    content: string,
    created: string,
    isArchived: boolean,
}

type CreatedNote = {
    id: string,
    created: string
}

class NotesStore {
    notes = $state<Note[]>([])
    loading = $state(false)
    lastAdded = $state(Date.now())

    async refresh(archived: boolean = false) {
        if (archived) {
            this.loading = true;
            this.notes = [];
            try {
                this.notes = await api.get("/notes?archived=true");
            } finally {
                this.loading = false;
            }
            return;
        }

        this.notes = await db.allNotes();

        try {
            let serverNotes = await api.get<Note[]>("/notes");
            this.notes = serverNotes;

            await db.replaceAllNotes(serverNotes);
        } catch (e) {
            // TODO: we offline, should notify the user
            console.error(e);
        }
    }

    async add(content: string) {
        let note = await api.post<CreatedNote>("/notes", { content: content })
        let fullNote = {
            ...note,
            content: content,
            isArchived: false,
        };
        this.notes.push(fullNote);
        this.lastAdded = Date.now()
        
        await db.putNote(fullNote);
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
        
        await db.deleteNote(id);
    }
}

export const notes = new NotesStore()