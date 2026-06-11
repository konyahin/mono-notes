<script lang="ts">
    import { onMount, tick } from "svelte";
    import { notes } from "./notes.svelte";

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
