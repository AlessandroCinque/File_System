package com.rtjvm.scala.oop.files

//there should be some override in front of parentPath and name but for now it gives only errors
class Directory(val parentPath : String, val name: String, val contents: List[DirEntry]) extends DirEntry(parentPath,name)
{
    def hasEntry(name : String): Boolean = ???
    def getAllFoldersInPath: List[String]=
    {
        path.substring(1).split(Directory.SEPARATOR).toList
    }
    def findDescendant(path: List[String]): Directory = ???

    def addEntry(newEntry: DirEntry): Directory = ???

    def findEntry(entryName: String): DirEntry = ???

    def replaceEntry(entryName: String,newEntry: DirEntry): Directory = ???

    override def asDirectory: Directory = ???
}
object Directory
{
    val SEPARATOR = "/"
    val ROOT_PATH = "/"

    def ROOT: Directory = Directory.empty("","");
    def empty(parentPath: String,name:String) = new Directory(parentPath,name,List())

}
