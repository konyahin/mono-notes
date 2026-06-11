<script lang="ts">
    import { onMount } from "svelte"
    import { greetings } from "./greetings.svelte";

    let error = $state<string | null>(null)

    onMount(async () => {
        try {
            await greetings.refresh()
        } catch (e) {
            error = e instanceof Error ? e.message : String(e)
        }
    });
</script>

{#if greetings.loading}
    <p aria-live="polite">Loading…</p>
{:else if error}
    <p role="alert">Error: {error}</p>
{:else}
    <ul>
        {#each greetings.greetings as g}
            <li>{g}</li>
        {/each}
    </ul>
{/if}
