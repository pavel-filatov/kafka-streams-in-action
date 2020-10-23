package com.ohmyspark.ksia.model

import java.util.Date

case class PurchasePattern(
    zipCode: String,
    item: String,
    date: Date,
    amount: Double
)

object PurchasePattern {
  def apply(p: Purchase) =
    new PurchasePattern(p.zipCode, p.item, p.date, p.quantity * p.price)
}
