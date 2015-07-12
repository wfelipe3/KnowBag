package knowbag.model.pomodoro

/**
 * Created by feliperojas on 4/30/15.
 */
class PomodoroSubject(var observers: List[Pomodoro => Unit] = List()) {

  def notify(pomodoro: Pomodoro) = observers.foreach(f => f(pomodoro))
  
  def addObserver(observer: Pomodoro => Unit): Unit = observers = observer :: observers
}
