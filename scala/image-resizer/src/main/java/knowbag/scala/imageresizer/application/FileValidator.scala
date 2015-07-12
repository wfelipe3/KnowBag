package knowbag.scala.imageresizer.application

/**
 * Created by feliperojas on 5/18/15.
 */
trait FileValidator {
  def isValidSource(source: String): Boolean
  def isValidDestination(destination: String): Boolean
  def createFolder(folder: String): Unit
  def getImagesIn(source: String): List[Image]
  def copy(image: Image): Unit
}
