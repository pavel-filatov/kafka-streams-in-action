package com.ohmyspark.ksia.model

case class Reward(
    customerId: String,
    amount: Double,
    rewardPoints: Int
) {
  def addRewardPoints(points: Int): Reward =
    copy(rewardPoints = rewardPoints + points)
}

object Reward {
  def apply(p: Purchase): Reward = {
    val amount = p.price * p.quantity
    new Reward(
      customerId = s"${p.firstName} ${p.lastName}",
      amount = amount,
      rewardPoints = amount.toInt
    )
  }
}
