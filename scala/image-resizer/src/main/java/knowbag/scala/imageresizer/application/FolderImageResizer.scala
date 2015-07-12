package knowbag.scala.imageresizer.application

/**
 * Created by feliperojas on 5/18/15.
 */
class FolderImageResizer(fileValidator: FileValidator, imageResizer: ImageResizer) {

  def resizeAllImagesInFolder(source: String, destination: String): Unit = {
    throwExceptionIfNotValidSource(source)
    throwExceptionIfNotValidDestination(destination)
    createDestinationFolder(destination)
    copyImages(findImagesInFolderAndResizeThem(folder = source))
  }

  private def throwExceptionIfNotValidSource(source: String): Unit = {
    if (!fileValidator.isValidSource(source)) throw new scala.IllegalArgumentException
  }

  private def throwExceptionIfNotValidDestination(destination: String): Unit = {
    if (!fileValidator.isValidDestination(destination)) throw new scala.IllegalArgumentException
  }

  def createDestinationFolder(destination: String): Unit = {
    fileValidator.createFolder(destination)
  }

  def findImagesInFolderAndResizeThem(folder: String): List[Image] = {
    fileValidator.getImagesIn(folder).map(imageResizer.resize)
  }

  def copyImages(resizedImages: List[Image]): Unit = {
    resizedImages.foreach(fileValidator.copy)
  }
}
