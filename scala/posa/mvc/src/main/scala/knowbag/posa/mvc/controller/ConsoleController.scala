package knowbag.posa.mvc.controller

import knowbag.posa.mvc.console.MVCConsole
import knowbag.posa.mvc.model.ConsoleModel

/**
 * Created by feliperojas on 22/04/15.
 */
class ConsoleController(console: MVCConsole, model: ConsoleModel) {

  def readLine: String = {
    val line = console.readLine()
    model.encryptMessage(line)
    line
  }
}
