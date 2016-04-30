(ns torcaui.suite
  (:require
    [doo.runner :refer-macros [doo-tests doo-all-tests]]
    [torcaui.core-test]))

(doo-tests 'torcaui.core-test)