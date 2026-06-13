<script lang="ts">
    import { notes } from "../api/notes.svelte";

    let query = $state("");
    let { archive = false } = $props();

    $effect(() => {
        query;
        const id = setTimeout(() => notes.search(query, archive), 300);
        return () => clearTimeout(id);
    });
    
    $effect(() => {
        archive;
        query = "";
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
