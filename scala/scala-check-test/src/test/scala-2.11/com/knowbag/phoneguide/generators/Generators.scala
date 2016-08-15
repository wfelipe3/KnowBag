package com.knowbag.phoneguide.generators

import com.knowbag.phoneguide.PhoneNumber
import org.scalacheck.{Arbitrary, Gen}

/**
  * Created by feliperojas on 7/31/16.
  */
object Generators {

  implicit val arbPhoneNumber: Arbitrary[PhoneNumber] = Arbitrary(genPhoneNumber)

  def genPhoneNumber = for {
    areaCode <- Gen.choose(1, 999)
    prefix <- Gen.choose(1, 999)
    lineNumber <- Gen.choose(1, 999)
  } yield new PhoneNumber(areaCode.toShort, prefix.toShort, lineNumber.toShort)

}
