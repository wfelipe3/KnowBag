package knowbag.posa.mvc.main

import java.util.Scanner

import knowbag.posa.mvc.console.MVCConsole
import knowbag.posa.mvc.controller.ConsoleController
import knowbag.posa.mvc.model.ConsoleModel
import knowbag.posa.mvc.view.ConsoleView

/**
 * Created by feliperojas on 19/04/15.
 */
object Main extends App {

  val console = new MVCConsole {
    val systemConsole: Scanner = new Scanner(System.in)

    override def print(message: String): Unit = println(message)

    override def readLine(): String = systemConsole.nextLine()
  }

  val model = new ConsoleModel(List(new ConsoleView(console)))
  val controller = new ConsoleController(console, model)

  while (true){
    new Thread {
      controller.readLine
    }.start()
  }
}
