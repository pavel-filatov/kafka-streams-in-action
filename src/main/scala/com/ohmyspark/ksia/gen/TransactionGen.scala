package com.ohmyspark.ksia.gen

import com.ohmyspark.ksia.model.Transaction
import org.scalacheck.Gen

object TransactionGen {

  val cardNumberGen: Gen[String] = Gen.listOfN(16, Gen.numChar).map(_.mkString)

  def genInfiniteTransactionsStream: Gen[LazyList[(Transaction, Int)]] =
    Gen.infiniteLazyList(TransactionGen.genTransaction)

  def genTransaction: Gen[(Transaction, Int)] =
    for {
      (firstName, lastName) <- UserGen.generateUser
      cardNumber <- cardNumberGen
      (item, price, department) <- ItemDepartmentGen.genItemDepartment
      employeeId <- Gen.chooseNum(1, 100).map(_.toString)
      quantity <- Gen.chooseNum(1, 10)
      date = "2020-10-23"
      zipCode <- Gen.listOfN(5, Gen.numChar).map(_.mkString)
      storeShortId <- Gen.listOfN(2, Gen.numChar).map(_.mkString)
      storeId = zipCode + storeShortId
      sleep <- Gen.chooseNum(10, 1000)
    } yield (
      Transaction(
        firstName = firstName,
        lastName = lastName,
        customerId = s"${firstName}_${lastName}".toLowerCase,
        creditCardNumber = cardNumber,
        item = item,
        department = department,
        employeeId = employeeId,
        quantity = quantity,
        price = price,
        date = date,
        zipCode = zipCode,
        storeId = storeId
      ),
      sleep
    )

}
