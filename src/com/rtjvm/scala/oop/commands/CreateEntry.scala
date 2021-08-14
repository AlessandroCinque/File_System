package com.rtjvm.scala.oop.commands

import com.rtjvm.scala.oop.files.{DirEntry, Directory}
import com.rtjvm.scala.oop.filesystem.State

abstract class CreateEntry(name: String) extends Command
{
    override def apply(state: State): State =
    {
        val wd = state.wd
        if(wd.hasEntry(name))
        {
            state.setMessage("Entry " + name + " already exist")
        }
        else if(name.contains(Directory.SEPARATOR))
        {
            // forbid in some way this thing later on
            state.setMessage(name + " must not contain separators!")
        }
        else if(checkIllegal(name))
        {
            state.setMessage(name + " : illegal entry name!")
        }
        else
        {
            doCreateEntry(state, name)
        }
    }

    def checkIllegal(str: String): Boolean =
    {
        name.contains(".")
    }
    def doCreateEntry(state: State, name: String): State =
    {
        def updateStructure(currentDirectory: Directory, path: List[String], newEntry:DirEntry): Directory =
        {
            if(path.isEmpty)
            {
                currentDirectory.addEntry(newEntry)
            }
            else
            {
                println(path)
                println(currentDirectory.findEntry(path.head).asDirectory)
                val oldEntry = currentDirectory.findEntry(path.head).asDirectory
                currentDirectory.replaceEntry(oldEntry.name ,updateStructure(oldEntry, path.tail, newEntry))
            }
        }

        val wd = state.wd

        //1 all the directories entry in the WD
        val allDirsInPath = wd.getAllFoldersInPath

//        //2 create new directory entry in the wd
//        val newDir = Directory.empty(wd.path,name)
        //TODO implement this!
        val newEntry: DirEntry = createSpecificEntry(state)

        //3 update the whole directory structure starting from the root
        val newRoot = updateStructure(state.root,allDirsInPath, newEntry)

        //4 find the new working directory INSTANCE given wd's full path
        val newWd = newRoot.findDescendant(allDirsInPath)

        State(newRoot, newWd)


    }
    def createSpecificEntry(state: State): DirEntry
}
