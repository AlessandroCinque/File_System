package com.rtjvm.scala.oop.filesystem

import com.rtjvm.scala.oop.commands.Command
import com.rtjvm.scala.oop.files.Directory

import java.util.Scanner

object FileSystem extends App
{
    val root = Directory.ROOT
    //wd as well is root becaus ethis is the starting point
    var state = State(root,root)
    val scanner = new Scanner(System.in)
    while(true)
    {
        state.show
        val input = scanner.nextLine()
        state = Command.from(input).apply(state)
    }
}
