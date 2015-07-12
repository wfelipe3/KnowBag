package knowbag.model.pomodoro

import java.util.Date

import knowbag.model.pomodoro.Phases.Phases

/**
 * Created by feliperojas on 4/30/15.
 */
trait Phase{
  val state: Phases
  def startTime: Date
}

object Phases extends Enumeration{
  type Phases = Value
  val work = Value
  val rest = Value
}

case class RestPhase(startTime: Date) extends Phase {
  override val state: Phases = Phases.rest
}

case class WorkPhase(startTime: Date) extends Phase {
  override val state: Phases = Phases.work
}
