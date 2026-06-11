<script lang="ts">
    import AddNote from "./lib/AddNote.svelte";
    import NotesList from "./lib/NotesList.svelte";
    import SearchNote from "./lib/SearchNote.svelte";
</script>

<main class="container">
    <header><SearchNote /></header>
    <main><NotesList /></main>
    <footer><AddNote /></footer>
</main>

<style>
    .container {
        display: flex;
        flex-direction: column;
        height: 100dvh;
    }
    header {
        padding-top: 5px;
    }
    header,
    footer {
        flex: 0 0 auto;
    }
    main {
        flex: 1 1 auto;
        overflow-y: auto;
        min-height: 0;
        overscroll-behavior: contain;
    }
</style>
