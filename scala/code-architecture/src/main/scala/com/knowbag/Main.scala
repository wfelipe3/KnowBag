package com.knowbag

import com.structurizr.Workspace
import com.structurizr.api.StructurizrClient

/**
  * Created by williame on 7/10/17.
  */
object Main extends App {

  val workspace = new Workspace("test project with this", "using this shit")
  val model = workspace.getModel
  val viewSet = workspace.getViews

  val me = model.addPerson("Me", "myself.")
  val world = model.addSoftwareSystem("world", "earth, to be precised")

  me.uses(world, "hello, world")

  viewSet.createSystemContextView(world, "my first view", "Just me and the world").addAllElements()

  val struc = new StructurizrClient()

}
