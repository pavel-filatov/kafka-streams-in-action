package com.ohmyspark.ksia.chapter03.zmart

import com.ohmyspark.ksia.chapter03.zmart.Entity.RawPurchase
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should.Matchers

class TestEntity extends AnyFlatSpecLike with Matchers {

  "maskCard" should "mask creadit card number correctly" in {
    val raw = RawPurchase("user", "1234567890123456")
    val purchase = raw.maskCard

    purchase.creditCard shouldBe Some("xxxxxxxxxxxx3456")
  }

}
