const rtf = new Intl.RelativeTimeFormat('en-EN', { numeric: 'auto' })

export function relative(iso: string): string {
    const diffSec = (new Date(iso).getTime() - Date.now()) / 1000
    const mins = Math.round(diffSec / 60)
    if (Math.abs(mins) < 60) return rtf.format(mins, 'minute')
    const hrs = Math.round(mins / 60)
    if (Math.abs(hrs) < 24) return rtf.format(hrs, 'hour')
    return rtf.format(Math.round(hrs / 24), 'day')
}