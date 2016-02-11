package knowbag.invitation

/**
 * Created by feliperojas on 10/19/15.
 */
object Main extends App {

  val names = NamesCompare.compare(extractFile("/all-people.txt"), extractFile("/marked-people.txt"))

  names.foreach(name => println(name))
  println(names.size)

  private def file(file: String): String = {
    ResourcesFolderPathResolver.getResourcePath(file)
  }

  def extract(file: String): List[String] = {
    NamesExtractor.extractFrom(file)
  }

  def extractFile(s: String): List[String] = {
    extract(file(s))
  }

}
