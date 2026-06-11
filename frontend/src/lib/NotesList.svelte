<script lang="ts">
    import { onMount } from "svelte";
    import { notes } from "./notes.svelte";

    let error = $state<string | null>(null);

    onMount(async () => {
        try {
            await notes.refresh();
        } catch (e) {
            error = e instanceof Error ? e.message : String(e);
        }
    });
</script>

<div class="notes">
    {#if notes.loading}
        <article aria-busy="true"></article>
    {:else if error}
        <p role="alert">Error: {error}</p>
    {:else}
        {#each notes.notes as { content }}
            <article>{content}</article>
        {/each}
    {/if}
</div>

<style>
    .notes {
        margin: 5px;
    }
</style>