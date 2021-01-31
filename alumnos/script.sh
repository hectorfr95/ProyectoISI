!/bin/bash
cd programa_alumnos
mvn install
java -cp ./target/myapp-1.0-jar-with-dependencies.jar urjc.isi.myapp.Main