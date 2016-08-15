package com.knowbag

import org.docopt.Docopt

/**
  * Created by feliperojas on 7/25/16.
  */
trait DocoptWrapperApp extends App {

  val doc: String

  val opts = new Docopt(doc)
    .withVersion("2.0")
    .parse(args: _*)

}
