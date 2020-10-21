package com.ohmyspark.ksia.chapter03.simple

import java.time.Duration
import java.util.Properties

import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala.Serdes._
import org.apache.kafka.streams.scala.StreamsBuilder
import org.apache.kafka.streams.{KafkaStreams, StreamsConfig}

/** Hello world example from chapter 3.2
  *
  */
object SimpleStreamsApp extends App {

  val props: Properties = {
    val p = new Properties()
    p.put(StreamsConfig.APPLICATION_ID_CONFIG, "yelling-app")
    p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092")
    p
  }

  val builder: StreamsBuilder = new StreamsBuilder()

  builder
    .stream[String, String]("src-topic")
    .mapValues(_.toUpperCase)
    .to("out-topic")

  val streams = new KafkaStreams(builder.build(), props)

  streams.start()

  sys.ShutdownHookThread {
    streams.close(Duration.ofSeconds(30))
  }

}
