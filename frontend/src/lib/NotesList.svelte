<script lang="ts">
    import { onMount, tick } from "svelte";
    import { flip } from "svelte/animate";
    import { notes } from "../api/notes.svelte";
    import Note from "./Note.svelte";
    import { fly } from "svelte/transition";

    let error = $state<string | null>(null);
    let container: HTMLDivElement;

    let { archive = false } = $props();
    
    async function refreshNotes() {
        try {
            await notes.refresh(archive);
        } catch (e) {
            error = e instanceof Error ? e.message : String(e);
        }
        container?.lastElementChild?.scrollIntoView();
    }

    onMount(async () => {
        refreshNotes();
    });

    $effect(() => {
        archive;
        refreshNotes();
    });
    
    $effect(() => {
        notes.lastAdded;
        tick().then(() => {
            container?.lastElementChild?.scrollIntoView({
                behavior: "smooth",
            });
        });
    });
</script>

<div class="notes" bind:this={container}>
    {#if notes.loading}
        <article aria-busy="true"></article>
    {:else if error}
        <p role="alert">Error: {error}</p>
    {:else if notes.notes.length === 0}
        <div in:fly={{ duration: 500 }}>
            <p class="empty">
                {#if archive}
                    No notes in archive yet
                {:else}
                    No notes yet. Write your first one below ↓
                {/if}
            </p>
        </div>
    {:else}
        {#each notes.notes as note (note.id)}
            <div animate:flip={{ duration: 300, delay: 150 }}>
                <Note {note} />
            </div>
        {/each}
    {/if}
</div>

<style>
    .notes {
        margin: 5px;
        padding-top: 15px;
        padding-right: 15px;
    }
    .empty {
        text-align: center;
        color: var(--pico-muted-color);
        margin-top: 2rem;
    }
</style>
