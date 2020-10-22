package com.ohmyspark.ksia.model

case class PurchasePattern(
    zipCode: String,
    item: String,
    date: String,
    amount: Double
)

object PurchasePattern {
  def apply(p: Purchase) =
    new PurchasePattern(p.zipCode, p.item, p.date, p.quantity * p.price)
}
