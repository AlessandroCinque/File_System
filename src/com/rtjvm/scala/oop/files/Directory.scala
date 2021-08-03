package com.rtjvm.scala.oop.files

//there should be some override in front of parentPath and name but for now it gives only errors
class Directory(val parentPath : String, val name: String, val contents: List[DirEntry]) extends DirEntry(parentPath,name)
{

}
object Directory
{
    val SEPARATOR = "/"
    val ROOT_PATH = "/"

    def ROOT: Directory = Directory.empty("","");
    def empty(parentPath: String,name:String) = new Directory(parentPath,name,List())

}
