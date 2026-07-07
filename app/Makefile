.PHONY: build run-dist clean checkstyle test setup sonar jacoco

setup:
	chmod +x gradlew
	./gradlew clean build

build:
	chmod +x gradlew
	./gradlew build

clean:
	chmod +x gradlew
	./gradlew clean

checkstyle:
	chmod +x gradlew
	./gradlew checkstyleMain checkstyleTest

test:
	chmod +x gradlew
	./gradlew clean test

sonar:
	chmod +x gradlew
	./gradlew sonar

jacoco:
	chmod +x gradlew
	./gradlew jacocoTestReport

win-setup:
	chmod +x gradlew
	.\gradlew clean build
