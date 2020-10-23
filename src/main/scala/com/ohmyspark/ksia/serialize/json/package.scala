package com.ohmyspark.ksia.serialize

import java.text.SimpleDateFormat

import net.liftweb.json._
import org.apache.kafka.common.serialization.{Deserializer, Serde, Serializer}

package object json {

  implicit val formats: Formats = new DefaultFormats {
    override def dateFormatter: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")
  }

  class GenericJsonSerializer[A] extends Serializer[A] {
    override def serialize(topic: String, data: A): Array[Byte] = {
      compactRender(Extraction.decompose(data)).toCharArray.map(_.toByte)
    }
  }

  class GenericJsonDeserializer[A]()(implicit m: Manifest[A])
      extends Deserializer[A] {
    def deserialize(topic: String, data: Array[Byte]): A = {
      parse(data.map(_.toChar).mkString).extract[A]
    }

  }

  class GenericJsonSerde[A](implicit m: Manifest[A]) extends Serde[A] {
    override def serializer(): Serializer[A] = new GenericJsonSerializer[A]

    override def deserializer(): Deserializer[A] =
      new GenericJsonDeserializer[A]
  }

}
