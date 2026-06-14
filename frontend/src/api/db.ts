import { openDB, type DBSchema, type IDBPDatabase } from 'idb'
import type { Note } from './notes.svelte'

interface NotesDB extends DBSchema {
    notes: {
        key: string
        value: Note
        indexes: { 'by-created': string }
    }
}

let dbPromise: Promise<IDBPDatabase<NotesDB>> | null = null

function getDB() {
    if (!dbPromise) {
        dbPromise = openDB<NotesDB>('mono-notes', 1, {
            upgrade(db) {
                let notes = db.createObjectStore('notes', { keyPath: 'id' })
                notes.createIndex('by-created', 'created')
            },
        })
    }
    return dbPromise
}

export const db = {
    async allNotes(): Promise<Note[]> {
        return (await getDB()).getAllFromIndex("notes", "by-created")
    },
    async putNote(note: Note) {
        await (await getDB()).put('notes', note)
    },
    async deleteNote(id: string) {
        await (await getDB()).delete('notes', id)
    },
    async replaceAllNotes(notes: Note[]) {
        const database = await getDB()
        const tx = database.transaction('notes', 'readwrite')
        await tx.store.clear()
        for (const note of notes) {
            await tx.store.put(note)
        }
        await tx.done
    },
}