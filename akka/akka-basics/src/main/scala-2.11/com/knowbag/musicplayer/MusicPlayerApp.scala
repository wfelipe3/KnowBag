package com.knowbag.musicplayer

import akka.actor.{Actor, ActorSystem, Props}
import com.knowbag.musicplayer.MusicController.{Play, Stop}
import com.knowbag.musicplayer.MusicPlayer.{StartMusic, StopMusic}

/**
  * Created by feliperojas on 7/24/16.
  */
object MusicController {

  sealed trait ControllerMsg

  case object Play extends ControllerMsg

  case object Stop extends ControllerMsg

  val props = Props[MusicController]
}

class MusicController extends Actor {
  override def receive: Receive = {
    case Play => println("Music started .......")
    case Stop => println("Music stopped .......")
  }
}

object MusicPlayer {

  sealed trait PlayMsg

  case object StartMusic extends PlayMsg

  case object StopMusic extends PlayMsg

}

class MusicPlayer extends Actor {
  override def receive: Receive = {
    case StopMusic => println("Dont whant to stop the music")
    case StartMusic =>
      val controller = context.actorOf(MusicController.props)
      controller ! Play
  }
}


object MusicPlayerApp extends App {
  val system = ActorSystem("MusicPlayer")
  val musicPlayer = system.actorOf(Props[MusicPlayer])
  musicPlayer ! StopMusic
  system.terminate()
}
