#!/usr/bin/env bash
set -euo pipefail

if [[ $EUID -ne 0 ]]; then
    echo "Run as root (use sudo)." >&2
    exit 1
fi

if ! getent group spa-notes >/dev/null; then
    groupadd --system spa-notes
fi
if ! id -u spa-notes >/dev/null 2>&1; then
    useradd --system --gid spa-notes \
        --home-dir /var/lib/spa-notes \
        --shell /usr/sbin/nologin \
        spa-notes
fi

install -d -m 0755 /opt/spa-notes
install -d -m 0750 -o spa-notes -g spa-notes /var/lib/spa-notes
install -d -m 0755 /etc/spa-notes

install -m 0644 spa-notes-all.jar    /opt/spa-notes/spa-notes.jar
install -m 0644 spa-notes.service    /etc/systemd/system/spa-notes.service

if [[ ! -f /etc/spa-notes/spa-notes.env ]]; then
    install -m 0644 spa-notes.env /etc/spa-notes/spa-notes.env
fi

systemctl daemon-reload
systemctl enable spa-notes.service
systemctl restart spa-notes.service
systemctl --no-pager --full status spa-notes.service || true
