<script lang="ts">
    import { fly } from "svelte/transition";
    import { notes, type Note } from "../api/notes.svelte";
    import { relative } from "../utils/utils";
    import { now } from "../utils/now.svelte";
    import A from "./A.svelte";

    type Props = {
        note: Note;
    };

    let { note }: Props = $props();
    let created = $derived((now.value, relative(note.created)));

    async function copy() {
        await navigator.clipboard.writeText(note.content);
    }

    async function archive() {
        notes.archive(note.id);
    }
</script>

<article in:fly={{ y: 16, duration: 200 }} out:fly={{ x: 200, duration: 200 }}>
    {note.content}
    <small>{created}</small>
    <div class="actions" role="group">
        <A
            animation="jump"
            onclick={() => {
                copy();
            }}
        >
            <svg
                width="16"
                height="16"
                viewBox="0 0 16 16"
                fill="none"
                stroke="currentColor"
                stroke-width="1.4"
                aria-hidden="true"
            >
                <rect x="5.5" y="5.5" width="8" height="8" rx="2" />
                <path
                    d="M3.5 10.5V4a1.5 1.5 0 0 1 1.5-1.5H10.5"
                    stroke-linecap="round"
                />
            </svg>
            Copy
        </A>
        {#if !note.isArchived}
            <A
                onclick={() => {
                    archive();
                }}
            >
                <svg
                    width="16"
                    height="16"
                    viewBox="0 0 16 16"
                    fill="none"
                    stroke="currentColor"
                    stroke-width="1.4"
                    aria-hidden="true"
                >
                    <rect x="2.5" y="3" width="11" height="3" rx="1" />
                    <path
                        d="M3.5 6v6.5a1 1 0 0 0 1 1H11.5a1 1 0 0 0 1-1V6"
                        stroke-linecap="round"
                    />
                    <path d="M6.5 8.5H9.5" stroke-linecap="round" />
                </svg>
                Archive
            </A>
        {/if}
    </div>
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
            box-shadow:
                0.4px 0.3px 0.6px hsl(var(--shadow-color) / 0.22),
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
    .actions {
        display: flex;
        gap: 8px;
        margin-bottom: 2px;
        width: fit-content;
        margin-left: auto;
    }
</style>
