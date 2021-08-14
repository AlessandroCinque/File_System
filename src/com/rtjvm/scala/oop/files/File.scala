package com.rtjvm.scala.oop.files

import com.rtjvm.scala.oop.filesystem.FilesystemException

class File (override  val parentPath: String, override val name: String, contents: String)
    extends DirEntry(parentPath, name)
{
    def asDirectory: Directory =  throw new FilesystemException("File cannot be convertyed in Directory")
    def asFile : File = this
    def getType: String = "File"
}
object File
{
    def empty(parentPath: String, name: String): File =
        new File(parentPath,name,"")
}