package knowbag.snippets

import java.io.{BufferedInputStream, File, FileInputStream}

/**
 * Created by feliperojas on 7/04/15.
 */
class FileBulkReader(val source: File) extends BulkReader{
  type In = File
  override def read: String = {
    val in  = new BufferedInputStream(new FileInputStream(source))
    val numBytes = in.available()
    val bytes = new Array[Byte](numBytes)
    in.read(bytes, 0, numBytes)
    new String(bytes)
  }
}
