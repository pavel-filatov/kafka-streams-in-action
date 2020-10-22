package com.ohmyspark.ksia.chapter03.zmart

import java.util.Properties

import com.ohmyspark.ksia.gen.TransactionGen
import com.ohmyspark.ksia.model.Transaction
import com.ohmyspark.ksia.serialize.json.GenericJsonSerializer
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.scalacheck.Gen
import org.scalacheck.rng.Seed

object ZMartProducer {

  val props: Properties = {
    val p = new Properties()

    p.put("bootstrap.servers", "http://localhost:29092")
    p.put("schema.registry.url", "http://localhost:8090")
    p.put(
      "key.serializer",
      "org.apache.kafka.common.serialization.StringSerializer"
    )
    p.put(
      "value.serializer",
      classOf[GenericJsonSerializer[Transaction]]
    )
    p.put("acks", "1")

    p
  }

  def main(args: Array[String]): Unit = {

    val producer = new KafkaProducer[String, Transaction](props)

    for {
      (transaction, sleep) <-
        TransactionGen.genInfiniteTransactionsStream.pureApply(Gen.Parameters.default, Seed(100))
    } {
      val record = new ProducerRecord("transactions", transaction.customerId, transaction)
      producer.send(record)
      Thread.sleep(sleep)
    }

  }

}
