// @SOURCE:/Users/feliperojas/KnowBag/activator/play-reactive-mongo/conf/routes
// @HASH:831f044926a0397e8c6fcd5998387177eb56c83e
// @DATE:Thu Apr 30 00:22:13 COT 2015


import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._


import Router.queryString

object Routes extends Router.Routes {

private var _prefix = "/"

def setPrefix(prefix: String) {
  _prefix = prefix
  List[(String,Routes)](("granpa",controllers.GrandpaController),("father",controllers.FatherController),("fatherNoStrings",controllers.FatherNoStringsController),("son",controllers.SonController)).foreach {
    case (p, router) => router.setPrefix(prefix + (if(prefix.endsWith("/")) "" else "/") + p)
  }
}

def prefix = _prefix

lazy val defaultPrefix = { if(Routes.prefix.endsWith("/")) "" else "/" }


// @LINE:6
private[this] lazy val controllers_Application_index0 = Route("GET", PathPattern(List(StaticPart(Routes.prefix))))
        

// @LINE:9
private[this] lazy val controllers_GrandpaController_index1 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("granpa"))))
        

// @LINE:10
lazy val controllers_GrandpaController2 = Include(controllers.GrandpaController)
        

// @LINE:11
private[this] lazy val controllers_GrandpaController_table3 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("granpa/table"))))
        

// @LINE:14
private[this] lazy val controllers_FatherController_index4 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("father"))))
        

// @LINE:15
lazy val controllers_FatherController5 = Include(controllers.FatherController)
        

// @LINE:16
private[this] lazy val controllers_FatherController_table6 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("father/table"))))
        

// @LINE:18
private[this] lazy val controllers_FatherNoStringsController_index7 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("fatherNoStrings"))))
        

// @LINE:19
lazy val controllers_FatherNoStringsController8 = Include(controllers.FatherNoStringsController)
        

// @LINE:20
private[this] lazy val controllers_FatherNoStringsController_table9 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("fatherNoStrings/table"))))
        

// @LINE:23
private[this] lazy val controllers_SonController_index10 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("son"))))
        

// @LINE:24
lazy val controllers_SonController11 = Include(controllers.SonController)
        

// @LINE:25
private[this] lazy val controllers_SonController_table12 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("son/table"))))
        

// @LINE:28
private[this] lazy val controllers_Assets_at13 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/"),DynamicPart("file", """.+""",false))))
        
def documentation = List(("""GET""", prefix,"""controllers.Application.index"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """granpa""","""controllers.GrandpaController.index"""),controllers.GrandpaController.documentation,("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """granpa/table""","""controllers.GrandpaController.table"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """father""","""controllers.FatherController.index"""),controllers.FatherController.documentation,("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """father/table""","""controllers.FatherController.table"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """fatherNoStrings""","""controllers.FatherNoStringsController.index"""),controllers.FatherNoStringsController.documentation,("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """fatherNoStrings/table""","""controllers.FatherNoStringsController.table"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """son""","""controllers.SonController.index"""),controllers.SonController.documentation,("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """son/table""","""controllers.SonController.table"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
  case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
  case l => s ++ l.asInstanceOf[List[(String,String,String)]] 
}}
      

def routes:PartialFunction[RequestHeader,Handler] = {

// @LINE:6
case controllers_Application_index0(params) => {
   call { 
        invokeHandler(controllers.Application.index, HandlerDef(this, "controllers.Application", "index", Nil,"GET", """ Home page""", Routes.prefix + """"""))
   }
}
        

// @LINE:9
case controllers_GrandpaController_index1(params) => {
   call { 
        invokeHandler(controllers.GrandpaController.index, HandlerDef(this, "controllers.GrandpaController", "index", Nil,"GET", """GranPa""", Routes.prefix + """granpa"""))
   }
}
        

// @LINE:10
case controllers_GrandpaController2(handler) => handler
        

// @LINE:11
case controllers_GrandpaController_table3(params) => {
   call { 
        invokeHandler(controllers.GrandpaController.table, HandlerDef(this, "controllers.GrandpaController", "table", Nil,"GET", """""", Routes.prefix + """granpa/table"""))
   }
}
        

// @LINE:14
case controllers_FatherController_index4(params) => {
   call { 
        invokeHandler(controllers.FatherController.index, HandlerDef(this, "controllers.FatherController", "index", Nil,"GET", """Father""", Routes.prefix + """father"""))
   }
}
        

// @LINE:15
case controllers_FatherController5(handler) => handler
        

// @LINE:16
case controllers_FatherController_table6(params) => {
   call { 
        invokeHandler(controllers.FatherController.table, HandlerDef(this, "controllers.FatherController", "table", Nil,"GET", """""", Routes.prefix + """father/table"""))
   }
}
        

// @LINE:18
case controllers_FatherNoStringsController_index7(params) => {
   call { 
        invokeHandler(controllers.FatherNoStringsController.index, HandlerDef(this, "controllers.FatherNoStringsController", "index", Nil,"GET", """bug here""", Routes.prefix + """fatherNoStrings"""))
   }
}
        

// @LINE:19
case controllers_FatherNoStringsController8(handler) => handler
        

// @LINE:20
case controllers_FatherNoStringsController_table9(params) => {
   call { 
        invokeHandler(controllers.FatherNoStringsController.table, HandlerDef(this, "controllers.FatherNoStringsController", "table", Nil,"GET", """""", Routes.prefix + """fatherNoStrings/table"""))
   }
}
        

// @LINE:23
case controllers_SonController_index10(params) => {
   call { 
        invokeHandler(controllers.SonController.index, HandlerDef(this, "controllers.SonController", "index", Nil,"GET", """Son""", Routes.prefix + """son"""))
   }
}
        

// @LINE:24
case controllers_SonController11(handler) => handler
        

// @LINE:25
case controllers_SonController_table12(params) => {
   call { 
        invokeHandler(controllers.SonController.table, HandlerDef(this, "controllers.SonController", "table", Nil,"GET", """""", Routes.prefix + """son/table"""))
   }
}
        

// @LINE:28
case controllers_Assets_at13(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        invokeHandler(controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]),"GET", """ Map static resources from the /public folder to the /assets URL path""", Routes.prefix + """assets/$file<.+>"""))
   }
}
        
}

}
     