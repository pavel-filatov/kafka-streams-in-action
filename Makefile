.PHONY: help
help:
	@ echo '====================================='
	@ echo 'Kafka in Action: Help'
	@ echo '====================================='
	@ sed -E -n '/^[a-z].*\#\>/p' Makefile | sed -E $$'s/:.*#>[ ]*/\\\n  /'
	@ echo '====================================='


##> Docker

.PHONY: up
up:  #> Run Docker environment with Kafka, ZooKeeper, and Avro Schema Registry
	docker-compose -f docker-compose.yaml up

.PHONY: down
down:  #> Shutdown Docker environment
	docker-compose -f docker-compose.yaml down


##> Kafka

.PHONY: consume
consume:  #> Usage: make consume TOPIC=<topic name>. Consume messages from specified topic and print them to console.
	docker exec -it broker \
		kafka-console-consumer --topic $(TOPIC) \
		--bootstrap-server broker:9092 \
		--from-beginning
