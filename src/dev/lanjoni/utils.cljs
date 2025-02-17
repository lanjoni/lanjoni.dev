(ns dev.lanjoni.utils)

(defn get-stored-theme []
  (let [stored-theme (js/localStorage.getItem "dark-mode")]
    (or (= stored-theme "true")
        (= stored-theme nil))))

(defn set-stored-theme [is-dark-mode]
  (js/localStorage.setItem "dark-mode" (str is-dark-mode)))

(defn apply-theme [is-dark-mode]
  (let [theme (if is-dark-mode "black" "lofi")]
    (.setAttribute js/document.documentElement "data-theme" theme)))

;;
; const userPreferences = {
;     theme: 'dark',
;     fontSize: '16px',
;     language: 'en'
; };
;
; // Storing the object in local storage
; localStorage.setItem('preferences', JSON.stringify(userPreferences));
;
; // Retrieving the object from local storage
; const savedPreferences = JSON.parse(localStorage.getItem('preferences'));
;
; console.log(savedPreferences);  // Output: { theme: "dark", fontSize: "16px", language: "en" }
