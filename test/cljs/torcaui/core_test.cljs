(ns torcaui.core-test
  (:require-macros [cljs.test :refer [deftest testing is async]]
                   [dommy.core :refer [sel sel1]])
  (:require [cljs.test]
            [torcaui.core]
            [js.jquery]
            [torcaui.test-common :refer [new-container!]]
            [torcaui.app :as app]
            [torcaui.routes :as routes]
            [torcaui.utils :as utils]
            [om.dom :as dom]
            [om.core :as om]
            [torcaui.components.aside :as aside]
            [dommy.core :refer [attr text]]))

(def jquery (js* "$"))

(defn x []
  (->
    (jquery "h1.class1")
    (jquery "span")
    (.text)))

(defn widget [data owner]
  (reify
    om/IRender
    (render [this]
      (dom/h1 #js {:id "classes" :className "class1 class2"} nil (:text data)))))

(deftest test-pass []
                   (is (= 2 2)))

(deftest test-fail []
                   (is (= (+ 1 1) 2)))

(deftest hamburgers []
                   (is (= (:hamburger-menu @app/state) "closed")))

(deftest alertbox-has-correct-message-text
  (let [c (new-container!)]
    (om/root widget {:text "Hello world!" :other "Potato"} {:target c})
    (is (= "Hello world!" (text (sel1 c :h1))))))

(deftest jquery-works
  (let [c (new-container!)
        xv (x)]
    (om/root widget {:text "Hello world!" :other "Potato"} {:target c})
    (is (= "Hello world!" xv))))

(deftest extract-token-extracts-valid-token
  (let [params "access_token=74652cc61e9cc798bdce5a7e3d71b5716b9e8f96c82eae8fc22ab36ad2e5db5d&token_type=bearer"
        access-token (utils/extract-access-token params)]
    (is (= access-token "74652cc61e9cc798bdce5a7e3d71b5716b9e8f96c82eae8fc22ab36ad2e5db5d"))))

(deftest extract-token-returns-nil-on-invalid-token
  (let [params "access_token=74652cc61e9cc798bdce5a7$%22&token_type=bearer"
        access-token (utils/extract-access-token params)]
    (is (= access-token nil))))


