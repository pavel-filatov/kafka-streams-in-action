package com.ohmyspark.ksia.serialize

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}

import org.apache.kafka.common.serialization.{Deserializer, Serde, Serializer}

package object kafka {

  class KafkaGenericSerializer[A] extends Serializer[A] {
    override def serialize(topic: String, data: A): Array[Byte] = {
      val stream: ByteArrayOutputStream = new ByteArrayOutputStream()
      val oos = new ObjectOutputStream(stream)
      oos.writeObject(data)
      oos.close()
      stream.toByteArray
    }

  }

  class KafkaGenericDeserializer[A] extends Deserializer[A] {
    override def deserialize(topic: String, data: Array[Byte]): A =
      if (data == null) {
        null.asInstanceOf[A]
      } else {
        val ois = new ObjectInputStream(new ByteArrayInputStream(data))
        val value = ois.readObject().asInstanceOf[A]
        ois.close()
        value
      }
  }

  class KafkaGenericSerde[A] extends Serde[A] {
    override def serializer(): Serializer[A] = new KafkaGenericSerializer[A]

    override def deserializer(): Deserializer[A] =
      new KafkaGenericDeserializer[A]
  }

}
