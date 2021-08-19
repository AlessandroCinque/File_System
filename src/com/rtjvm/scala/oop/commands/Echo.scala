package com.rtjvm.scala.oop.commands
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
    def doEcho(state: State, contets: String, filename: String, append: Boolean) =
    {
        ???
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
