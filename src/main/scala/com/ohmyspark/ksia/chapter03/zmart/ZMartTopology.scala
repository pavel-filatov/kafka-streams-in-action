package com.ohmyspark.ksia.chapter03.zmart

import java.util.Properties

import com.ohmyspark.ksia.model.{Purchase, PurchasePattern, Reward, Transaction}
import com.ohmyspark.ksia.serialize.json.GenericJsonSerde
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.streams.kstream.Printed
import org.apache.kafka.streams.scala.StreamsBuilder
import org.apache.kafka.streams.{KafkaStreams, StreamsConfig}

object ZMartTopology extends App {

  val props: Properties = {
    val p = new Properties()

    p.put(StreamsConfig.APPLICATION_ID_CONFIG, "ZMart-topology")
    p.put("bootstrap.servers", "http://localhost:29092")
    p.put("schema.registry.url", "http://localhost:8090")
    p.put(
      "key.serializer",
      "org.apache.kafka.common.serialization.StringSerializer"
    )
    p.put("acks", "1")

    p
  }

  implicit val rawPurchaseSerde: Serde[Transaction] =
    new GenericJsonSerde[Transaction]()
  implicit val purchaseSerde: Serde[Purchase] =
    new GenericJsonSerde[Purchase]()
  implicit val purchasePatternSerde: Serde[PurchasePattern] =
    new GenericJsonSerde[PurchasePattern]()
  implicit val rewardSerde: Serde[Reward] = new GenericJsonSerde[Reward]()

  val builder = new StreamsBuilder()

  val rawPurchaseStream = builder.stream[String, Transaction]("transactions")

  val purchaseStream = rawPurchaseStream.mapValues(_.toPurchase)
  val purchasePatternStream = purchaseStream.mapValues(_.toPurchasePattern)
  val rewardStream = purchaseStream.mapValues(_.toReward)

  rawPurchaseStream.print(Printed.toSysOut().withLabel("src"))
  purchaseStream.print(Printed.toSysOut().withLabel("prc"))
  purchasePatternStream.print(Printed.toSysOut().withLabel("pat"))
  rewardStream.print(Printed.toSysOut().withLabel("rew"))

  purchaseStream.to("purchase")
  purchasePatternStream.to("pattern")
  rewardStream.to("reward")

  val streams = new KafkaStreams(builder.build(), props)

  streams.start()
}
