package knowbag.model.pomodoro

import java.util.Date

/**
 * Created by feliperojas on 4/30/15.
 */
object PhaseFactory {

  def newWorkPhase = WorkPhase(new Date)
  def newRestPhase = RestPhase(new Date)
}
