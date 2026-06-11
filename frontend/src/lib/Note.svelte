<script lang="ts">
    import { fly } from "svelte/transition"
    import type { Note } from "./notes.svelte"
    import { relative } from "./utils";
    import { now } from "./now.svelte";

    type Props = {
        note: Note;
    };

    let { note }: Props = $props();
    let created = $derived((now.value, relative(note.created)));
</script>

<article in:fly={{ y: 16, duration: 200 }}>
    {note.content}
    <small>{created}</small>
</article>

<style>
    article {
        white-space: pre-wrap;
    }
    small {
        display: block;
        text-align: right;
        opacity: 0.5;
        margin-top: 0.5rem;
    }
</style>