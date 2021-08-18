package com.rtjvm.scala.oop.commands
import com.rtjvm.scala.oop.files.{DirEntry, Directory}
import com.rtjvm.scala.oop.filesystem.State

import javax.naming.spi.DirStateFactory.Result
import scala.annotation.tailrec

class Cd(dir: String) extends Command
{
    override def apply(state: State): State =
    {
        val root = state.root
        val wd = state.wd

        // Find the absolute path and  check if it is the absolute path
        val absolutePath =
        {
            if (dir.startsWith(Directory.SEPARATOR)) dir
            else if (wd.isRoot) wd.path + dir
            else wd.path + Directory.SEPARATOR + dir

        }

        // find the directoru to cd TO
        val destinationDirectory = doFindEntry(root,absolutePath)

        //4 - Finally cheange the state to that path

        if(destinationDirectory == null || ! destinationDirectory.isDirectory)
        {
            state.setMessage(dir + " :no such directory")
        }
        else
        {
            State(root,destinationDirectory.asDirectory)
        }
    }

    def doFindEntry(root: Directory,path: String): DirEntry =
    {
        @tailrec
        def findEntryHelper(currentDirectory: Directory,path: List[String]) : DirEntry =
        {
            if (path.isEmpty || path.head.isEmpty) currentDirectory
            else if (path.tail.isEmpty) currentDirectory.findEntry(path.head)
            else
            {
                val nextDir = currentDirectory.findEntry(path.head)
                // this will trigger what we wrote in path 4
                if (nextDir == null || !nextDir.isDirectory) null
                else findEntryHelper(nextDir.asDirectory,path.tail)
            }
        }

        //1 getting all the path between the slashes
        val tokens: List[String] = path.substring(1).split(Directory.SEPARATOR).toList

        //1.5 collapse relative tokens
        @tailrec
        def collapseRelativeTokens(path: List[String], result: List[String]): List[String] =
        {
            if(path.isEmpty)result
            else if(".".equals((path.head))) collapseRelativeTokens(path.tail,result.init)
            else if("..".equals(path.head))
            {
                // all this tails,heads and init are filtering the passed-in values
                if(result.isEmpty) null
                else collapseRelativeTokens(path.tail, result.tail)
            }
            else
            {
                // all this tails,heads and init are filtering the passed-in values
                collapseRelativeTokens(path.tail,result :+ path.head)
            }
        }
        val newTokens = collapseRelativeTokens(tokens, List())
        // 2 navigating to entry
        if(newTokens == null) null
        else findEntryHelper(root,newTokens)



    }
}
