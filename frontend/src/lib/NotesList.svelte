<script lang="ts">
    import { onMount, tick } from "svelte";
    import { notes } from "../api/notes.svelte";
    import Note from "./Note.svelte";

    let error = $state<string | null>(null);
    let container: HTMLDivElement;

    onMount(async () => {
        try {
            await notes.refresh();
        } catch (e) {
            error = e instanceof Error ? e.message : String(e);
        }
        container?.lastElementChild?.scrollIntoView();
    });

    $effect(() => {
        notes.notes.length;
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
        <p class="empty">No notes yet. Write your first one below ↓</p>
    {:else}
        {#each notes.notes as note}
            <Note {note} />
        {/each}
    {/if}
</div>

<style>
    .notes {
        margin: 5px;
        padding-top: 15px;
    }
    article {
        white-space: pre-wrap;
    }
    .empty {
        text-align: center;
        color: var(--pico-muted-color);
        margin-top: 2rem;
    }
</style>
