package com.ohmyspark.ksia.gen

import org.scalacheck.Gen

object ItemDepartmentGen {

  val itemsByDepartment: Map[String, List[(String, Double)]] = Map(
    "groceries" -> List(
      ("apples", 4.20),
      ("bread", 0.90),
      ("cucumbers", 2.35),
      ("diet coke", 0.57)
    ),
    "electronics" -> List(
      ("TV", 400.0),
      ("radio", 45.95),
      ("remote control", 13.95),
      ("smart speaker", 35),
      ("laptop", 1350),
      ("PC", 1200)
    ),
    "books and others" -> List(
      ("magazines", 7.70),
      ("newspapers", 3.20),
      ("books", 10.90),
      ("accessories", 2.50)
    )
  )

  def genItemDepartment: Gen[(String, Double, String)] =
    for {
      department <- Gen.oneOf(itemsByDepartment.keys)
      (item, price) <- Gen.oneOf(itemsByDepartment(department))
    } yield (item, price, department)

}
