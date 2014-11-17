## running the word-net clojure functions from java

```
lein uberjar
```
then compile java with the word-net standalone jar in the classpath (or word-net and clojure jar)
```
javac -cp '.:../../../target/uberjar/word-net-0.1.0-SNAPSHOT-standalone.jar' WordNet.java
```
then run it
```
java -cp '.:../../../target/uberjar/word-net-0.1.0-SNAPSHOT-standalone.jar' WordNet
```
also had to change use a full abolute path in file_io.clj
