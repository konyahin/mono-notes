<script lang="ts">
    import { notes } from "./notes.svelte";

    const placeholder = "Shift+Enter new line\nEnter to send"
    let note = $state("");
    let error = $state("");

    async function submit() {
        if (note === "") {
            return;
        }

        try {
            await notes.add(note);
            note = "";
            error = "";
        } catch (e) {
            error = e instanceof Error ? e.message : String(e);
        }
    }

    function processInput(e: KeyboardEvent) {
        if (e.key === "Enter" && !e.shiftKey) {
            e.preventDefault()
            submit()
            return
        }
    }
</script>

<textarea
    bind:value={note}
    onkeydown={processInput}
    {placeholder}
></textarea>
{#if error}
    <p role="alert">{error}</p>
{/if}
