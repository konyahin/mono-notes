#!/usr/bin/env bash
set -euo pipefail

if [[ $EUID -ne 0 ]]; then
    echo "Run as root (use sudo)." >&2
    exit 1
fi

if ! getent group mono-notes >/dev/null; then
    groupadd --system mono-notes
fi
if ! id -u mono-notes >/dev/null 2>&1; then
    useradd --system --gid mono-notes \
        --home-dir /var/lib/mono-notes \
        --shell /usr/sbin/nologin \
        mono-notes
fi

install -d -m 0755 /opt/mono-notes
install -d -m 0750 -o mono-notes -g mono-notes /var/lib/mono-notes
install -d -m 0755 /etc/mono-notes

install -m 0644 mono-notes-all.jar    /opt/mono-notes/mono-notes.jar
install -m 0644 mono-notes.service    /etc/systemd/system/mono-notes.service

if [[ ! -f /etc/mono-notes/mono-notes.env ]]; then
    install -m 0644 mono-notes.env /etc/mono-notes/mono-notes.env
fi

systemctl daemon-reload
systemctl enable mono-notes.service
systemctl restart mono-notes.service
systemctl --no-pager --full status mono-notes.service || true
