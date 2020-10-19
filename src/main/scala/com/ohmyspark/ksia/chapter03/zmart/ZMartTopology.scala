package com.ohmyspark.ksia.chapter03.zmart

import java.util.Properties

import com.ohmyspark.ksia.chapter03.zmart.Entity.{Purchase, RawPurchase}
import com.ohmyspark.ksia.serialize.json.GenericJsonSerde
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.streams.kstream.Printed
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala.Serdes._
import org.apache.kafka.streams.scala.StreamsBuilder
import org.apache.kafka.streams.scala.kstream.KStream
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

  implicit val rawPurchaseSerde: Serde[RawPurchase] =
    new GenericJsonSerde[RawPurchase]()
  implicit val purchaseSerde: Serde[Purchase] =
    new GenericJsonSerde[Purchase]()

  val builder = new StreamsBuilder()

  val rawPurchaseStream = builder.stream[String, RawPurchase]("transactions")
  val purchaseStream: KStream[String, Purchase] =
    rawPurchaseStream.mapValues(_.maskCard)

  rawPurchaseStream.print(Printed.toSysOut().withLabel("src"))
  purchaseStream.print(Printed.toSysOut().withLabel("prc"))
  purchaseStream.to("purchase")

  val streams = new KafkaStreams(builder.build(), props)

  streams.start()
}
