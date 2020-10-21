package com.ohmyspark.ksia.model

import java.sql.Date

case class Transaction(
    firstName: String,
    lastName: String,
    customerId: String,
    creditCardNumber: String,
    item: String,
    department: String,
    employeeId: String,
    quantity: Int,
    price: Double,
    date: Date,
    zipCode: String,
    storeId: String
) {
  def toPurchase: Purchase = Purchase.apply(this)
}
