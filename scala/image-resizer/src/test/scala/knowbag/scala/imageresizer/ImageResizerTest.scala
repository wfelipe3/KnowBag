package knowbag.scala.imageresizer

import knowbag.scala.imageresizer.application.{FileValidator, Image, FolderImageResizer, ImageResizer}
import org.mockito.Mockito._
import org.mockito.Matchers._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by feliperojas on 5/18/15.
 */
class ImageResizerTest extends FlatSpec with Matchers with MockitoSugar {

  behavior of "Application Image resizer"

  it should "throw illegal argument exception when validations in source file does not pass" in {
    val fileValidator: FileValidator = mock[FileValidator]
    when(fileValidator.isValidSource("missed-file")).thenReturn(false)
    when(fileValidator.isValidDestination(anyString())).thenReturn(true)
    a[IllegalArgumentException] should be thrownBy resizeImagesInFolder(source = "missed-file", destination = "any-folder", fileValidator = fileValidator)
  }

  it should "throw illegal argument exception when a validation in the destination folder does not pass" in {
    val fileValidator: FileValidator = mock[FileValidator]
    when(fileValidator.isValidSource(anyString())).thenReturn(true)
    when(fileValidator.isValidDestination("not-a-folder")).thenReturn(false)
    a[IllegalArgumentException] should be thrownBy resizeImagesInFolder(source = "any-folder", destination = "not-a-folder", fileValidator)
  }

  it should "create destination folder when the folder does not exist" in {
    val fileValidator: FileValidator = mock[FileValidator]
    when(fileValidator.isValidSource(anyString())).thenReturn(true)
    when(fileValidator.isValidDestination(anyString())).thenReturn(true)
    when(fileValidator.getImagesIn(anyString())).thenReturn(List())
    resizeImagesInFolder("any-folder", "any-dest-folder", fileValidator)
    verify(fileValidator, times(1)).createFolder("any-dest-folder")
  }

  it should "resize images in source folder" in {
    val imageResizer = mock[ImageResizer]
    val fileValidator = mock[FileValidator]
    when(fileValidator.isValidSource(anyString())).thenReturn(true)
    when(fileValidator.isValidDestination(anyString())).thenReturn(true)
    when(fileValidator.getImagesIn("any-folder")).thenReturn(List(Image("test"), Image("cat"), Image("dog")))
    resizeImagesInFolder("any-folder", "any-dest-folder", fileValidator, imageResizer)
    verify(imageResizer, times(3)).resize(any[Image])
  }

  it should "copy resized images in destination folder" in {
    val fileValidator = mock[FileValidator]
    when(fileValidator.isValidSource(anyString())).thenReturn(true)
    when(fileValidator.isValidDestination(anyString())).thenReturn(true)
    when(fileValidator.getImagesIn("any-folder")).thenReturn(List(Image("test"), Image("cat"), Image("dog")))

    val imageResizer = mock[ImageResizer]
    when(imageResizer.resize(any[Image])).thenReturn(any[Image])

    resizeImagesInFolder("any-folder", "any-dest-folder", fileValidator, imageResizer)
    verify(fileValidator, times(3)).copy(any[Image])
  }

  private def resizeImagesInFolder(source: String, destination: String, fileValidator: FileValidator, imageResizer: ImageResizer = imageResizer): Unit = {
    val folderImageResizer = new FolderImageResizer(fileValidator, imageResizer)
    folderImageResizer.resizeAllImagesInFolder(source, destination)
  }

  def imageResizer = mock[ImageResizer]
}
