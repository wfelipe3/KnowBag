package knowbag.posa.mvc.console

/**
 * Created by feliperojas on 19/04/15.
 */
trait MVCConsole {

  def print(message: String): Unit

  def readLine(): String
}
