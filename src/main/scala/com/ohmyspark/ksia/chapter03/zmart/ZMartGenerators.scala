package com.ohmyspark.ksia.chapter03.zmart

import java.util.Properties

import com.ohmyspark.ksia.chapter03.zmart.Entity.RawPurchase
import com.ohmyspark.ksia.serialize.json.GenericJsonSerializer
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.scalacheck.Gen
import org.scalacheck.rng.Seed

object ZMartGenerators {

  val usersGen: Gen[List[String]] =
    Gen.listOfN(3, Gen.uuid).map(_.map(_.toString))
  val userGen: Gen[String] = usersGen.flatMap(us => Gen.oneOf(us))

  val cardNumberGen: Gen[String] = Gen.listOfN(16, Gen.numChar).map(_.mkString)

  val entryGen: Gen[(RawPurchase, Int)] = for {
    user <- userGen
    card <- cardNumberGen
    sleep <- Gen.chooseNum(100, 1000)
  } yield (RawPurchase(user, card), sleep)

  val entryStreamGen: Gen[LazyList[(RawPurchase, Int)]] =
    Gen.infiniteLazyList(entryGen)

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
      classOf[GenericJsonSerializer[RawPurchase]]
    )
    p.put("acks", "1")

    p
  }

  def main(args: Array[String]): Unit = {

    val producer = new KafkaProducer[String, RawPurchase](props)

    for {
      (purchase, sleep) <-
        entryStreamGen.pureApply(Gen.Parameters.default, Seed(100))
    } {
      val record = new ProducerRecord("transactions", purchase.user, purchase)
      producer.send(record)
      Thread.sleep(sleep)
    }

  }

}
