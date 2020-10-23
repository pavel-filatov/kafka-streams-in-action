package com.ohmyspark.ksia.model

import java.util.Date

case class Purchase(
    firstName: String,
    lastName: String,
    customerId: String,
    creditCardNumber: Option[String],
    item: String,
    department: String,
    quantity: Int,
    price: Double,
    date: Date,
    zipCode: String,
    storeId: String
) {
  def toPurchasePattern: PurchasePattern = PurchasePattern(this)

  def toReward: Reward = Reward(this)
}

object Purchase {

  def apply(t: Transaction): Purchase =
    new Purchase(
      firstName = t.firstName,
      lastName = t.lastName,
      customerId = t.customerId,
      creditCardNumber = maskCard(t.creditCardNumber),
      item = t.item,
      department = t.department,
      quantity = t.quantity,
      price = t.price,
      date = t.date,
      zipCode = t.zipCode,
      storeId = t.storeId
    )

  def maskCard(creditCardNumber: String): Option[String] = {

    val validCardNumber = "^(\\d{12})(\\d{4})$".r

    validCardNumber.unapplySeq(creditCardNumber) match {
      case Some(_ :: last4digits :: Nil) => Some("x" * 12 + last4digits)
      case _                             => None
    }
  }
}
