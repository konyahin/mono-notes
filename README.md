# mono-notes

Simple self-hosted note-taking PWA for personal use.
All your notes will be saved on your server in an SQLite database.

## Installation

Requires Java 21 or newer on the server.

Get the distribution archive in one of two ways:
- Download `mono-notes-<version>.tar.gz` from the GitHub releases page.
- Build it locally with `./gradlew packageDist` (output in `build/distributions/`).

Then on the server:

```sh
tar -xzf mono-notes-<version>.tar.gz
cd mono-notes-<version>
sudo ./install.sh
```

The script installs a systemd unit and starts the service on port 8080.
You can change port in the `mono-notes.env` file.
