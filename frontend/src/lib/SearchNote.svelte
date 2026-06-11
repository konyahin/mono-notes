<script lang="ts">
    import { notes } from "./notes.svelte";

    let query = $state("");

    $effect(() => {
        const q = query
        const id = setTimeout(() => notes.search(q), 200);
        return () => clearTimeout(id);
    });
</script>

<input
    bind:value={query}
    type="search"
    name="search"
    placeholder="Search note"
    aria-label="Search note"
/>

<style>
    input {
        margin-bottom: 5px;
    }
</style>
