package knowbag.invitation

/**
 * Created by feliperojas on 10/20/15.
 */
object ResourcesFolderPathResolver {

  def getResourcePath(file: String): String = {
    this.getClass.getResource(file).getFile
  }
}
