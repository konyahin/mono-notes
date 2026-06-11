import { mount } from 'svelte'
import { registerSW } from 'virtual:pwa-register'
import '@picocss/pico/css/pico.min.css'
import './app.css'
import App from './App.svelte'

registerSW({ immediate: true })

const app = mount(App, {
  target: document.getElementById('app')!,
})

export default app
