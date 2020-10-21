package com.ohmyspark.ksia.serialize

import net.liftweb.json._
import org.apache.kafka.common.serialization.{Deserializer, Serde, Serializer}

package object json {

  class GenericJsonSerializer[A] extends Serializer[A] {
    implicit val formats: Formats = DefaultFormats

    override def serialize(topic: String, data: A): Array[Byte] = {
      compactRender(Extraction.decompose(data)).toCharArray.map(_.toByte)
    }
  }

  class GenericJsonDeserializer[A]()(implicit m: Manifest[A])
      extends Deserializer[A] {
    implicit val formats: Formats = DefaultFormats
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
