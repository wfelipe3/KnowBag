package knowbag.posa.mvc.view

import knowbag.posa.mvc.console.MVCConsole
import knowbag.posa.mvc.model.ConsoleModel
import org.mockito.Mockito.verify
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by feliperojas on 19/04/15.
 */
class ConsoleViewTest extends FlatSpec with Matchers with MockitoSugar {

  behavior of "Console"

  it should "print value to console" in {
    val console = mock[MVCConsole]
    val modelWithObservers = new ConsoleModel().addObserver(new ConsoleView(console))
    modelWithObservers.notifyMessage("test message")
    verify(console).print("test message")
  }
}
