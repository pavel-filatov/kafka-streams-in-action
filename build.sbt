name := "kafka-streams-in-action"

version := "0.1"

scalaVersion := "2.13.3"

resolvers += "confluent" at "https://packages.confluent.io/maven/"

libraryDependencies ++= Seq(
  "org.apache.kafka" % "kafka-clients" % "2.6.0",
  "org.apache.kafka" %% "kafka-streams-scala" % "2.6.0",
  "org.scalacheck" %% "scalacheck" % "1.14.3",
  "io.confluent" % "kafka-streams-avro-serde" % "6.0.0",
  "com.sksamuel.avro4s" %% "avro4s-kafka" % "4.0.0",
  "net.liftweb" %% "lift-json" % "3.4.2",

  "org.scalatest" %% "scalatest" % "3.2.2" % Test
)
