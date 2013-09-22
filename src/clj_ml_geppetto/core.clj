(ns clj-ml-geppetto.core
  (:use [geppetto.cli])
  (:use [clj-ml io classifiers data utils]))

(def classifier-table
  {"c45" [:decision-tree :c45]
   "boosted-stump" [:decision-tree :boosted-stump]
   "random-forest" [:decision-tree :random-forest]
   "rotation-forest" [:decision-tree :rotation-forest]
   "m5p" [:decision-tree :m5p]
   "naive-bayes" [:bayes :naive]
   "smo" [:support-vector-machine :smo]
   "spegasos" [:support-vector-machine :spegasos]
   "svm" [:support-vector-machine :libsvm-grid]
   "linear-reg" [:regression :linear]
   "logistic-reg" [:regression :logistic]
   "pace-reg" [:regression :pace]
   "boosted-reg" [:regression :boosted-regression]
   "mlp" [:neural-network :multilayer-perceptron]})

(defn run
  [comparative? params]
  (let [ds (-> (load-instances :arff "http://clj-ml.artifice.cc/iris.arff")
               (dataset-set-class :class))
        classifier (apply make-classifier (classifier-table (:Classifier params)))
        evaluation (classifier-evaluate classifier :cross-validation ds 10)]
    [(assoc (select-keys evaluation [:error-rate])
       :params params)]))

(defn -main [& args]
  (geppetto-cli run args))
