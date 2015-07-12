package knowbag.signal

import knowbag.frp.Var

/**
 * Created by feliperojas on 5/25/15.
 */
class BankAccount {

  val balance = Var(0)

  def deposit(amount: Int): Unit =
    if (amount > 0) {
      val b: Int = balance()
      balance() = b + amount
    }

  def withdraw(amount: Int): Unit =
    if (0 < amount && amount <= balance()) {
      val b: Int = balance()
      balance() = b - amount
    }
    else
      throw new Error("insufficient found")

}
