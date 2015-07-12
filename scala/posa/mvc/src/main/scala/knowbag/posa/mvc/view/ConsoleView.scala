package knowbag.posa.mvc.view

import knowbag.posa.mvc.console.MVCConsole

/**
 * Created by feliperojas on 19/04/15.
 */
class ConsoleView(console: MVCConsole) extends (String => Unit){

  def showMessage(message: String): Unit = console.print(message)

  override def apply(message: String): Unit = console.print(message)
}
