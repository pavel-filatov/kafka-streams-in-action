package com.ohmyspark.ksia.chapter03.simple

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.scalacheck.Gen
import org.scalacheck.rng.Seed

object GenerateSimpleMessage {

  val entryGen: Gen[(String, String, Int)] = for {
    key <- Gen.listOfN(3, Gen.alphaChar).map(_.mkString.toLowerCase)
    event <- Gen.alphaStr
    sleep <- Gen.chooseNum(100, 1000)
  } yield (key, event, sleep)

  val entryStreamGen: Gen[LazyList[(String, String, Int)]] =
    Gen.infiniteLazyList(entryGen)

  def main(args: Array[String]): Unit = {
    val props = {
      val p = new Properties()

      p.put("bootstrap.servers", "http://localhost:29092")
      p.put("schema.registry.url", "http://localhost:8090")
      p.put(
        "key.serializer",
        "org.apache.kafka.common.serialization.StringSerializer"
      )
      p.put(
        "value.serializer",
        "org.apache.kafka.common.serialization.StringSerializer"
      )
      p.put("acks", "1")

      p
    }

    val producer = new KafkaProducer[String, String](props)

    for {
      (key, message, sleep) <-
        entryStreamGen.pureApply(Gen.Parameters.default, Seed(100))
    } {
      val record = new ProducerRecord[String, String]("src-topic", key, message)
      producer.send(record)
      Thread.sleep(sleep)
    }

  }
}
