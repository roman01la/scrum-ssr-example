(ns ui.routes)

(def routes
  ["/" [["" :top]
        ["top" [["" :top]
                [["/" :id] :top]]]
        ["new" [["" :new]
                [["/" :id] :new]]]
        ["show" [["" :show]
                 [["/" :id] :show]]]
        ["ask" [["" :ask]
                [["/" :id] :ask]]]
        ["jobs" [["" :jobs]
                 [["/" :id] :jobs]]]
        [["user/" :id] :user]
        [["item/" :id] :post]
        ["about" :about]]])
