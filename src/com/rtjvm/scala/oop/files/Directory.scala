package com.rtjvm.scala.oop.files

import com.rtjvm.scala.oop.filesystem.FilesystemException

import scala.annotation.tailrec

//there should be some override in front of parentPath and name but for now it gives only errors
class Directory(override val parentPath : String, override val name: String, val contents: List[DirEntry]) extends DirEntry(parentPath,name)
{
    //It is just a method to check if is there is something or not
    def hasEntry(name : String): Boolean =
    {
        findEntry(name) != null
    }

    def getAllFoldersInPath: List[String]=
    {
        path.substring(1).split(Directory.SEPARATOR).toList.filter(x => ! x.isEmpty)
    }

    def findDescendant(path: List[String]): Directory =
    {
        if(path.isEmpty) this
        else
        {
            findEntry(path.head).asDirectory.findDescendant(path.tail)
        }
    }
    // OVERLOADING IT TO HANDLE SINGLE STRING
    def findDescendant(relativePath: String): Directory=
    {
        if(relativePath.isEmpty) this
        else findDescendant(relativePath.split(Directory.SEPARATOR).toList)
    }
    def removeEntry(entryName: String): Directory =
    {
        if(!hasEntry(entryName)) this
                                                           //Not the same that we just removed
        else new Directory(parentPath,name,contents.filter(x => !x.name.equals(entryName)))

    }
    def addEntry(newEntry: DirEntry): Directory =
    {
        new Directory(parentPath,name,contents:+ newEntry)
    }

    def findEntry(entryName: String): DirEntry =
    {
        @tailrec
        def findEntryHelper(name: String, contentList: List[DirEntry]): DirEntry =
        {
            if(contentList.isEmpty) null
            else if(contentList.head.name.equals(name)) contentList.head
            else findEntryHelper(name,contentList.tail)
        }
        findEntryHelper(entryName,contents)
    }

    def replaceEntry(entryName: String,newEntry: DirEntry): Directory =
    {
        new Directory(parentPath,name,contents.filter( e => ! e.name.equals(entryName)) :+ newEntry)
    }

    def isRoot : Boolean = parentPath.isEmpty



    def getType: String = "Directory"

    def asFile : File = throw  new FilesystemException("Directory cannot be converted as File")

    override def isDirectory: Boolean = true
    override def isFile: Boolean = false



    override def asDirectory: Directory = this
}
object Directory
{
    val SEPARATOR = "/"
    val ROOT_PATH = "/"

    def ROOT: Directory = Directory.empty("","");
    def empty(parentPath: String,name:String) = new Directory(parentPath,name,List())

}
