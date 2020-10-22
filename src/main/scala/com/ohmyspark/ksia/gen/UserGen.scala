package com.ohmyspark.ksia.gen

import org.scalacheck.Gen

object UserGen {

  def generateUser: Gen[(String, String)] = for {
    first <- Gen.oneOf(firstNames)
    last <- Gen.oneOf(lastNames)
  } yield (first, last)

  val firstNames: List[String] = List(
    "Aaron",
    "Aisha",
    "Ben",
    "Brook",
    "Chris",
    "Cindy",
    "Dave",
    "Diana",
    "Evan",
    "Eva",
    "Floyd",
    "Felicia",
    "George",
    "Gene",
    "Harry",
    "Helen",
    "Ivan",
    "Iren",
    "James",
    "Jane",
    "Kyle",
    "Kelly"
  )

  val lastNames: List[String] = List(
    "Lemon",
    "Monday",
    "Orange",
    "Potter",
    "Quinn",
    "Rabble",
    "Swanton",
    "Teepot",
    "Umtiti",
    "Volo",
    "Wilson",
    "Xyloto",
    "Young",
    "Zevelow"
  )

}
