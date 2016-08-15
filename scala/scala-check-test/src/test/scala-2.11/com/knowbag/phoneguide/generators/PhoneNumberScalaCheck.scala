package com.knowbag.phoneguide.generators

import com.knowbag.phoneguide.{PhoneGuide, PhoneNumber}
import com.knowbag.phoneguide.generators.Generators._
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by feliperojas on 7/31/16.
  */
class PhoneNumberScalaCheck extends FlatSpec with GeneratorDrivenPropertyChecks with Matchers {

  behavior of "Phone number equals"

  it should "be reflexibe" in {
    forAll { p: PhoneNumber =>
      p.equals(p) should be(true)
    }
  }

  it should "be symmetric" in {
    forAll { p: PhoneNumber =>
      val p2 = PhoneGuide.fromPhoneNumber(p)
      p.equals(p2) should be(true)
      p2.equals(p) should be(true)
    }
  }
}
