package com.rtjvm.scala.oop.commands
import com.rtjvm.scala.oop.files.{Directory, File}
import com.rtjvm.scala.oop.filesystem.State

import scala.annotation.tailrec

class Echo(args: Array[String]) extends Command
{
    override def apply(state: State): State =
    {
        if(args.isEmpty) state
        else if(args.length == 1) state.setMessage(args(0))
            //if there are many arguments
        else
        {
            val operator = args(args.length - 2)
            val filename = args(args.length - 1)
            val contnets = createContent(args,args.length-2)

            if(">>".equals(operator))
            {
                doEcho(state,contnets,filename,true)
            }
            else if(">".equals(operator))
            {
                doEcho(state,contnets,filename,false)
            }
            else
            {
                state.setMessage(createContent(args,args.length-2))
            }
        }

    }
    def getRoothAfterEcho(currentDirectory: Directory, path: List[String], contents: String, append: Boolean): Directory =
    {
        if(path.isEmpty) currentDirectory
        else if (path.tail.isEmpty)
        {
            val dirEntry = currentDirectory.findEntry(path.head)

            if(dirEntry == null) currentDirectory.addEntry(new File(currentDirectory.path,path.head,contents))
            else if(dirEntry.isDirectory) currentDirectory
            else
            {
                if(append) currentDirectory.replaceEntry(path.head,dirEntry.asFile.appendContents(contents))
                else currentDirectory.replaceEntry(path.head,dirEntry.asFile.setContents(contents))
            }

        }
        else
        {
            // we are not supporting relative paths
            val nextDirectory = currentDirectory.findEntry(path.head).asDirectory
            val newNextDirectory = getRoothAfterEcho(nextDirectory,path.tail,contents,append)

            if(newNextDirectory == nextDirectory) currentDirectory
            else currentDirectory.replaceEntry(path.head, newNextDirectory)
        }
    }

    def doEcho(state: State, contets: String, filename: String, append: Boolean) =
    {
        if(filename.contains(Directory.SEPARATOR))
        {
            state.setMessage("Echo: I do not support separators")
        }
        else
        {
            val newRoot: Directory =
            {
                getRoothAfterEcho(state.root, state.wd.getAllFoldersInPath :+ filename,contets,append)
            }
            if( newRoot == state.root)
            {
                state.setMessage(filename + " : No such file")
            }
            else
                State(newRoot ,newRoot.findDescendant(state.wd.getAllFoldersInPath))
        }
    }
    // topIndex non inclusive
    def createContent(args: Array[String], topIndex: Int): String=
    {
        @tailrec
        def createContentHelper(currentIndex: Int, accumulator: String): String =
        {
            if(currentIndex >= topIndex) accumulator
            else createContentHelper(currentIndex + 1,accumulator + " " + args(currentIndex))
        }
        createContentHelper(0,"")
    }
}
