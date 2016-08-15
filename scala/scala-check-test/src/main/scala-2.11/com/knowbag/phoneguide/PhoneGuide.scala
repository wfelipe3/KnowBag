package com.knowbag.phoneguide

/**
  * Created by feliperojas on 7/31/16.
  */
object PhoneGuide {

  def apply(areaCode: Short, prefix: Short, lineNumber: Short) = {
    rangeCheck(areaCode, 999, "area code")
    rangeCheck(prefix, 999, "prefix")
    rangeCheck(lineNumber, 9999, "line number")
    new PhoneNumber(areaCode, prefix, lineNumber)
  }


  def fromPhoneNumber(p: PhoneNumber) = {
    new PhoneNumber(p.areaCode, p.prefix, p.lineNumber)
  }

  private def rangeCheck(arg: Int, max: Int, name: String): Unit = {
    if (arg < 0 || arg > max)
      throw new IllegalArgumentException(name + ":" + arg)
  }
}
