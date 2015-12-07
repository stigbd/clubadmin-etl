
.PHONY: test
all: build transform

build:
	@echo "Building"
	mvn clean install

test:
	@echo "Testing"

build-docker:
	docker build -t etl .

clean:
	@echo "Cleaning"
	mvn clean

transform:
	java -jar target/clubadminetl-1.0-SNAPSHOT.jar
