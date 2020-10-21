package com.ohmyspark.ksia.chapter03.zmart

import scala.beans.BeanProperty

object Entity {
  case class RawPurchase(@BeanProperty user: String, @BeanProperty creditCard: String) {
    def maskCard: Purchase = {

      val validCardNumber = "^(\\d{12})(\\d{4})$".r

      val maskedCardNumber = validCardNumber.unapplySeq(creditCard) match {
        case Some(_ :: last4digits :: Nil) => Some("x" * 12 + last4digits)
        case _ => None
      }

      Purchase(user, maskedCardNumber)
    }
  }
  case class Purchase(user: String, creditCard: Option[String])
}
