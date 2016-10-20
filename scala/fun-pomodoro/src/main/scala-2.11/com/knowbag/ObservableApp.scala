package com.knowbag

import rx.lang.scala.Observable

/**
  * Created by feliperojas on 10/17/16.
  */
object ObservableApp extends App {

  Observable.just(1,2,3,4,5)
        .map(i => i + 1)
      .subscribe(i => println(i))

}
