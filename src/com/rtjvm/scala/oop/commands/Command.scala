package com.rtjvm.scala.oop.commands
import  com.rtjvm.scala.oop.filesystem.State
trait Command
{
    def apply(state: State): State
}
object Command
{
    val MKDIR = "mkdir"

    val LS = "ls"

    val PWD = "pwd"

    val TOUCH = "touch"

    val CD = "cd"

    val RM = "rm"

    val ECHO = "echo"

    val CAT = "cat"

    def from(input: String): Command =
    {
        def emptyCommand: Command = new Command
        {
            override def apply(state: State): State = state
        }
        def incompleteCommand( name: String): Command = new Command
        {
            override def apply(state: State): State = state.setMessage(name + ": incomplete command")
        }
        val tokens: Array[String] = input.split(" ")

        if(tokens.isEmpty || input.isEmpty) emptyCommand
            // In scala you select element of an Array with () NOT WITH []
        else if(MKDIR.equals((tokens(0))))
        {
            if(tokens.length < 2) incompleteCommand(MKDIR)
            else new Mkdir(tokens(1))
        }
        else if (LS.equals(tokens(0)))
        {
            new Ls
        }
        else if (PWD.equals(tokens(0)))
        {
            new Pwd
        }
        else if (TOUCH.equals(tokens(0)))
        {
            if(tokens.length < 2) incompleteCommand(MKDIR)
            else new Touch(tokens(1))
        }
        else if(CD.equals(tokens(0)))
        {
            if(tokens.length < 2) incompleteCommand(CD)
            else new Cd(tokens(1))
        }
        else if(RM.equals(tokens(0)))
        {
            if(tokens.length < 2) incompleteCommand(RM)
            else new Rm(tokens(1))
        }
        else if(ECHO.equals(tokens(0)))
        {
            if(tokens.length < 2) incompleteCommand(ECHO)
            else new Echo(tokens.tail)
        }
        else if(CAT.equals(tokens(0)))
        {
            if(tokens.length < 2) incompleteCommand(CAT)
            else new Cat(tokens(1))
        }
        else new UnknownCommand
    }

}
