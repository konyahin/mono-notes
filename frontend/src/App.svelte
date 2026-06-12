<script lang="ts">
    import AddNote from "./lib/AddNote.svelte";
    import NotesList from "./lib/NotesList.svelte";
    import SearchNote from "./lib/SearchNote.svelte";
</script>

<div class="container">
    <header><SearchNote /></header>
    <main><NotesList /></main>
    <footer><AddNote /></footer>
</div>

<style>
    .container {
        display: grid;
        grid-template-columns: 1fr;
        grid-template-rows: 1fr auto;
        height: 100dvh;
    }
    header,
    main {
        grid-column: 1;
        grid-row: 1;
    }
    header {
        align-self: start;
        z-index: 1;
        padding-top: 5px;
        background: var(--pico-background-color);
        box-shadow: 0 8px 8px -8px rgba(0, 0, 0, 0.15);
    }
    main {
        overflow-y: auto;
        min-height: 0;
        overscroll-behavior: contain;
        padding-top: 3rem;
    }
    footer {
        grid-row: 2;
    }
</style>
