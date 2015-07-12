// @SOURCE:/Users/feliperojas/KnowBag/activator/play-reactive-mongo/conf/routes
// @HASH:831f044926a0397e8c6fcd5998387177eb56c83e
// @DATE:Thu Apr 30 00:22:13 COT 2015

import Routes.{prefix => _prefix, defaultPrefix => _defaultPrefix}
import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._


import Router.queryString


// @LINE:28
// @LINE:25
// @LINE:23
// @LINE:20
// @LINE:18
// @LINE:16
// @LINE:14
// @LINE:11
// @LINE:9
// @LINE:6
package controllers {

// @LINE:28
class ReverseAssets {
    

// @LINE:28
def at(file:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                                                
    
}
                          

// @LINE:11
// @LINE:9
class ReverseGrandpaController {
    

// @LINE:11
def table(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "granpa/table")
}
                                                

// @LINE:9
def index(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "granpa")
}
                                                
    
}
                          

// @LINE:20
// @LINE:18
class ReverseFatherNoStringsController {
    

// @LINE:20
def table(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "fatherNoStrings/table")
}
                                                

// @LINE:18
def index(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "fatherNoStrings")
}
                                                
    
}
                          

// @LINE:25
// @LINE:23
class ReverseSonController {
    

// @LINE:25
def table(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "son/table")
}
                                                

// @LINE:23
def index(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "son")
}
                                                
    
}
                          

// @LINE:6
class ReverseApplication {
    

// @LINE:6
def index(): Call = {
   Call("GET", _prefix)
}
                                                
    
}
                          

// @LINE:16
// @LINE:14
class ReverseFatherController {
    

// @LINE:16
def table(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "father/table")
}
                                                

// @LINE:14
def index(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "father")
}
                                                
    
}
                          
}
                  


// @LINE:28
// @LINE:25
// @LINE:23
// @LINE:20
// @LINE:18
// @LINE:16
// @LINE:14
// @LINE:11
// @LINE:9
// @LINE:6
package controllers.javascript {

// @LINE:28
class ReverseAssets {
    

// @LINE:28
def at : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        
    
}
              

// @LINE:11
// @LINE:9
class ReverseGrandpaController {
    

// @LINE:11
def table : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.GrandpaController.table",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "granpa/table"})
      }
   """
)
                        

// @LINE:9
def index : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.GrandpaController.index",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "granpa"})
      }
   """
)
                        
    
}
              

// @LINE:20
// @LINE:18
class ReverseFatherNoStringsController {
    

// @LINE:20
def table : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FatherNoStringsController.table",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "fatherNoStrings/table"})
      }
   """
)
                        

// @LINE:18
def index : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FatherNoStringsController.index",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "fatherNoStrings"})
      }
   """
)
                        
    
}
              

// @LINE:25
// @LINE:23
class ReverseSonController {
    

// @LINE:25
def table : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.SonController.table",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "son/table"})
      }
   """
)
                        

// @LINE:23
def index : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.SonController.index",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "son"})
      }
   """
)
                        
    
}
              

// @LINE:6
class ReverseApplication {
    

// @LINE:6
def index : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.index",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + """"})
      }
   """
)
                        
    
}
              

// @LINE:16
// @LINE:14
class ReverseFatherController {
    

// @LINE:16
def table : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FatherController.table",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "father/table"})
      }
   """
)
                        

// @LINE:14
def index : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.FatherController.index",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "father"})
      }
   """
)
                        
    
}
              
}
        


// @LINE:28
// @LINE:25
// @LINE:23
// @LINE:20
// @LINE:18
// @LINE:16
// @LINE:14
// @LINE:11
// @LINE:9
// @LINE:6
package controllers.ref {


// @LINE:28
class ReverseAssets {
    

// @LINE:28
def at(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]), "GET", """ Map static resources from the /public folder to the /assets URL path""", _prefix + """assets/$file<.+>""")
)
                      
    
}
                          

// @LINE:11
// @LINE:9
class ReverseGrandpaController {
    

// @LINE:11
def table(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.GrandpaController.table(), HandlerDef(this, "controllers.GrandpaController", "table", Seq(), "GET", """""", _prefix + """granpa/table""")
)
                      

// @LINE:9
def index(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.GrandpaController.index(), HandlerDef(this, "controllers.GrandpaController", "index", Seq(), "GET", """GranPa""", _prefix + """granpa""")
)
                      
    
}
                          

// @LINE:20
// @LINE:18
class ReverseFatherNoStringsController {
    

// @LINE:20
def table(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FatherNoStringsController.table(), HandlerDef(this, "controllers.FatherNoStringsController", "table", Seq(), "GET", """""", _prefix + """fatherNoStrings/table""")
)
                      

// @LINE:18
def index(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FatherNoStringsController.index(), HandlerDef(this, "controllers.FatherNoStringsController", "index", Seq(), "GET", """bug here""", _prefix + """fatherNoStrings""")
)
                      
    
}
                          

// @LINE:25
// @LINE:23
class ReverseSonController {
    

// @LINE:25
def table(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.SonController.table(), HandlerDef(this, "controllers.SonController", "table", Seq(), "GET", """""", _prefix + """son/table""")
)
                      

// @LINE:23
def index(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.SonController.index(), HandlerDef(this, "controllers.SonController", "index", Seq(), "GET", """Son""", _prefix + """son""")
)
                      
    
}
                          

// @LINE:6
class ReverseApplication {
    

// @LINE:6
def index(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Seq(), "GET", """ Home page""", _prefix + """""")
)
                      
    
}
                          

// @LINE:16
// @LINE:14
class ReverseFatherController {
    

// @LINE:16
def table(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FatherController.table(), HandlerDef(this, "controllers.FatherController", "table", Seq(), "GET", """""", _prefix + """father/table""")
)
                      

// @LINE:14
def index(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.FatherController.index(), HandlerDef(this, "controllers.FatherController", "index", Seq(), "GET", """Father""", _prefix + """father""")
)
                      
    
}
                          
}
        
    