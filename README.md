# Hacker News Clone

_A web app with server-side rendering and code splitting built with ClojureScript_

1. Build ClojureScript from master https://github.com/clojure/clojurescript/wiki/Building-the-compiler
2. Clone Sablono fork adapted to use React from NPM (`react` branch) https://github.com/roman01la/rum/tree/react
3. Clone Rum fork adapted to use React from NPM (`react` branch) https://github.com/roman01la/sablono/tree/react
4. Put them in the same directory with this project
5. Make sure ClojureScript build version matches the one in `project.clj` here
5. Build the app
```
$ lein cljsbuild once min
```
6. Start server
```
$ rlwrap lein repl
user=> (go)
```
7. Go to http://localhost:3000
