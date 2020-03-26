
SOURCES = $(shell find src/ -name \*.java)

target/scm-1.0-SNAPSHOT-jar-with-dependencies.jar : pom.xml $(SOURCES)
	mvn package

run: target/scm-1.0-SNAPSHOT-jar-with-dependencies.jar
	mvn exec:java

clean:
	mvn clean

eclipse:
	mvn dependency:sources
	mvn dependency:resolve -Dclassifier=javadoc
	mvn eclipse:eclipse

$(HOME)/.local/bin/scm:
	(echo "#!/bin/sh"; echo "java -jar $(HOME)/.local/share/scm/scm.jar") > $@
	chmod u+x $@

install: target/scm-1.0-SNAPSHOT-jar-with-dependencies.jar  $(HOME)/.local/bin/scm
	mkdir -p $(HOME)/.local/share/scm
	cp target/scm-1.0-SNAPSHOT-jar-with-dependencies.jar $(HOME)/.local/share/scm/scm.jar
