<script lang="ts">
    import { notes } from "../api/notes.svelte";

    let query = $state("");

    $effect(() => {
        const q = query
        const id = setTimeout(() => notes.search(q), 300);
        return () => clearTimeout(id);
    });
</script>

<input
    bind:value={query}
    type="search"
    name="search"
    placeholder="Search"
    aria-label="Search"
    autocomplete="off"
/>

<style>
    input {
        margin-bottom: 5px;
    }
</style>
