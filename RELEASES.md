# Releases

## How do I create a release and deploy?

	mvn versions:set -DnewVersion=<version>
	mvn clean test
	mvn versions:commit
	mvn clean deploy
	git commit pom.xml src/main/java/com/paystax/client/Version.java -m "Incrementing version for release"
	mvn scm:tag -Dtag=<name>
	mvn versions:set -DnewVersion=<version>
	mvn versions:commit
	git commit pom.xml src/main/java/com/paystax/client/Version.java -m "Incrementing version for release"

Example:

	mvn versions:set -DnewVersion=1.0.0
	mvn clean test
	mvn versions:commit
	mvn clean deploy
	git commit pom.xml src/main/java/com/paystax/client/Version.java -m "Incrementing version for release"
	mvn scm:tag -Dtag=1.0.0
	mvn versions:set -DnewVersion=1.0.1-SNAPSHOT
	mvn clean test
	mvn versions:commit
	git commit pom.xml src/main/java/com/paystax/client/Version.java -m "Incrementing version for release"
