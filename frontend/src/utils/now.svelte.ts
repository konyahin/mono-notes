let _now = $state(Date.now())

$effect.root(() => {
    const id = setInterval(() => (_now = Date.now()), 60_000)
    return () => clearInterval(id)
})

export const now = {
    get value() { return _now }
}