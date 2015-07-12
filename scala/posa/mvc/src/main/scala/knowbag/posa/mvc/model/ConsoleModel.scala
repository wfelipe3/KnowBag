package knowbag.posa.mvc.model

/**
 * Created by feliperojas on 19/04/15.
 */
class ConsoleModel(observers: List[(String) => Unit] = List()) {
  def addObserver(f: (String => Unit)): ConsoleModel = new ConsoleModel(f :: observers)
  def notifyMessage(message: String) = observers.foreach(f => f(message))
  def encryptMessage(message: String) = observers.foreach(f => f(message.reverse))
}
