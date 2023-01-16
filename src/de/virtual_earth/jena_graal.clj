(ns de.virtual-earth.jena-graal
  (:require [clojure.java.io :as io])
  (:import (org.apache.jena.update UpdateFactory)
           (org.apache.jena.query QueryParseException))
  (:gen-class))

(set! *warn-on-reflection* true)

(defn check-syntax
  "check if Sparql Update statement has correct syntax.
  returns map with either {:ok? true :prettyQuery <prettyPrintedQuery>}
  or {:or? false :errormsg <exception msg>}"
  [updateStmt] 
  (try
    {:ok? true
     :prettyQuery (str (UpdateFactory/create updateStmt "sparql11"))}
    (catch QueryParseException e
      {:ok? false
       :errormsg (.getMessage e)})))

( def query
 "PREFIX owl:   <http://www.w3.org/2002/07/owl#>
  PREFIX sh:    <http://www.w3.org/ns/shacl#>
  PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#> 
  PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>
  PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>
  PREFIX rdfox: <https://rdfox.com/vocabulary#>
  PREFIX ex:    <https://example.org/ex#>


  INSERT  {?invalid a rdfox:ConstraintViolation;
                  sh:message  ?message;
                  sh:severity ?severity. }
  USING ex:g1

  WHERE {
  BIND(\"INLGE_FLAG: The synonym is not allowed to have multiple values.\" AS ?message)
  BIND(sh:Violation AS ?severity) 
 ?syn a owl:FunctionalProperty, ex:p28 .

 ?invalid ?syn ?object1, ?object2 . 
 FILTER (?object1 != ?object2)
 }")

(defn -main
  [& args]
  (println
   (check-syntax query)))


(comment

  (UpdateFactory.)

  (check-syntax query)
  )
