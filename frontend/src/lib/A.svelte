<script lang="ts">
    import type { Snippet } from "svelte";

    type Props = {
        children: Snippet,
        onclick: CallableFunction,
        class?: string,
        animation?:  "jump",
    }
    
    let animations = {
        "jump": {
            keyframes: [
                { transform: "translateY(0)", offset: 0 },
                { transform: "translateY(8px)", offset: 0.3 },
                { transform: "translateY(0)", offset: 0.6 },
                { transform: "translateY(3px)", offset: 0.8 },
                { transform: "translateY(0)", offset: 1 },
            ],
            options: { duration: 400, easing: "ease" }
        }
    };
    
    let { children, onclick, class: className = " ", animation }: Props = $props();
</script>

<a href="/" class="{className}" onclick={(e) => {
    e.preventDefault();
    onclick();
    if (animation) {
        let animationOptions = animations[animation];
        (e.currentTarget as HTMLElement).animate(animationOptions.keyframes, animationOptions.options);
    }
}}>
{@render children()}
</a>

<style>
    a {
        color: #373c44;
        text-decoration: none;
        opacity: 0.8;
    }
</style>
