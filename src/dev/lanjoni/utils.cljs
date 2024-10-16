(ns dev.lanjoni.utils)

(defn get-stored-theme []
  (let [stored-theme (js/localStorage.getItem "dark-mode")]
    (if (or (= stored-theme "true")
            (= stored-theme nil))
      true
      false)))

(defn set-stored-theme [is-dark-mode]
  (js/localStorage.setItem "dark-mode" (str is-dark-mode)))

(defn apply-theme [is-dark-mode]
  (let [theme (if is-dark-mode "black" "lofi")]
    (.setAttribute js/document.documentElement "data-theme" theme)))
