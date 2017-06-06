# Hacker News Clone

## Development

*start web app build*
```
lein cljsbuild auto dev
```

*start server*
```
rlwrap lein repl
(go)
```

## Project structure

- `client` — client-side (ClojureScript) only code
- `ssr` — backend (Clojure) code
- `ui` — shared UI code (*.cljc)

### Client

- `core.cljs` — app initialization
- `router.cljs` — hooking into HTML5 Hisotry API
- `effects.cljs` — effects handlers (HTTP)
- `controllers` — state management logic

### Server

- `core.clj` — app initialization
- `api.clj` — data retrieval from storage
- `page.clj` — HTML document template rendering
- `resolver.clj` — server state retrieval from api
- `middleware` — Ring middlewares: Transit format encoding/decoding, RPC API server, route matcher, web app renderer and Etag
- `components` — server components: web server, application
