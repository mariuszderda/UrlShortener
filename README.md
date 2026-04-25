# URL Shortener

Projekt zaliczeniowy — skracanie URLi w Spring Boot. Dwa mikroserwisy + PostgreSQL w Dockerze.

## Co to robi

- Wysyłasz długi URL → dostajesz krótki kod (Base62)
- Wchodzisz na krótki URL → przekierowuje na oryginalny
- Linki wygasają po określonym czasie (konfigurowalne w application.properties)

## Jak uruchomić

Potrzebujesz Dockera.

```bash
git clone https://github.com/mariuszderda/UrlShortener.git
cd UrlShortener
git checkout microservices
docker compose up --build
```

## Jak używać

Skrócenie URLa:

```bash
curl -X POST http://localhost:8081/api/shorten -H "Content-Type: application/json" -d '{"url": "https://example.com/very/long/path"}'
```

Przekierowanie:

```bash
curl -v http://localhost:8082/1C
```

## Architektura

- **Write Service** (port 8081) — przyjmuje URL, generuje kod Base62, zapisuje do bazy
- **Read Service** (port 8082) — szuka kodu w bazie, sprawdza TTL, robi redirect 302

Oba serwisy łączą się do wspólnej bazy PostgreSQL.

## Technologie

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Docker / Docker Compose
- JUnit 5 / Mockito

## Branche

- `main` — wersja monolityczna (in-memory, jeden serwis)
- `microservices` — rozbite na dwa serwisy + baza + Docker
