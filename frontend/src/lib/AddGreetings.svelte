<script lang="ts">
    import { greetings } from "./greetings.svelte";

    let greeting = $state("");
    let error = $state("");

    async function submit(event: SubmitEvent) {
        event.preventDefault();
        if (greeting === "") {
            return;
        }

        try {
            await greetings.add(greeting)
            greeting = ""
            error = ""
        } catch (e) {
            error = e instanceof Error ? e.message : String(e);
        }
    }
</script>

<form onsubmit={submit}>
    <label for="new-greeting">New greeting</label>
    <fieldset role="group">
        <input id="new-greeting" bind:value={greeting} placeholder="Hello, world" />
        <button type="submit">Save</button>
    </fieldset>
    {#if error}
        <p role="alert">{error}</p>
    {/if}
</form>
