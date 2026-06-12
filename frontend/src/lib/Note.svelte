<script lang="ts">
    import { fly } from "svelte/transition";
    import type { Note } from "../api/notes.svelte";
    import { relative } from "../utils/utils";
    import { now } from "../utils/now.svelte";

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
    :root {
        --shadow-color: 0deg 0% 73%;
    }
    article {
        white-space: pre-wrap;
        text-align: right;
        width: max-content;
        max-width: 100%;
        margin-left: auto;
    }
    @media (prefers-color-scheme: light) {
        article {
            box-shadow: 0.4px 0.3px 0.6px
                hsl(var(--shadow-color) / 0.22),
            1.1px 0.9px 1.7px -0.7px hsl(var(--shadow-color) / 0.23),
            2.6px 2.1px 3.9px -1.5px hsl(var(--shadow-color) / 0.25),
            6.1px 4.8px 9px -2.2px hsl(var(--shadow-color) / 0.26);
            border: 1px solid rgb(100, 100, 100, 8%);
        }
    }
    small {
        display: block;
        text-align: right;
        opacity: 0.5;
        margin-top: 0.5rem;
    }
</style>
