// @SOURCE:/Users/renzeereyes/School/ICS414/OCKB/conf/routes
// @HASH:27470eb90a2ef7c35ca9b531af7b6a66108830fe
// @DATE:Thu Mar 27 23:19:29 HST 2014

import Routes.{prefix => _prefix, defaultPrefix => _defaultPrefix}
import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString


// @LINE:16
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package controllers {

// @LINE:16
class ReverseAssets {
    

// @LINE:16
def at(file:String): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                                                
    
}
                          

// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {
    

// @LINE:10
def submitPost(): Call = {
   Call("POST", _prefix + { _defaultPrefix } + "posts")
}
                                                

// @LINE:7
def submit(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "submit")
}
                                                

// @LINE:12
def categories(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "categories")
}
                                                

// @LINE:11
def deletePost(id:Long): Call = {
   Call("POST", _prefix + { _defaultPrefix } + "posts/" + implicitly[PathBindable[Long]].unbind("id", id) + "/delete")
}
                                                

// @LINE:8
def explore(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "explore")
}
                                                

// @LINE:9
def posts(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "posts")
}
                                                

// @LINE:6
def index(): Call = {
   Call("GET", _prefix)
}
                                                

// @LINE:13
def comments(): Call = {
   Call("GET", _prefix + { _defaultPrefix } + "comments")
}
                                                
    
}
                          
}
                  


// @LINE:16
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package controllers.javascript {

// @LINE:16
class ReverseAssets {
    

// @LINE:16
def at : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        
    
}
              

// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {
    

// @LINE:10
def submitPost : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.submitPost",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "posts"})
      }
   """
)
                        

// @LINE:7
def submit : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.submit",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "submit"})
      }
   """
)
                        

// @LINE:12
def categories : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.categories",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "categories"})
      }
   """
)
                        

// @LINE:11
def deletePost : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.deletePost",
   """
      function(id) {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "posts/" + (""" + implicitly[PathBindable[Long]].javascriptUnbind + """)("id", id) + "/delete"})
      }
   """
)
                        

// @LINE:8
def explore : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.explore",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "explore"})
      }
   """
)
                        

// @LINE:9
def posts : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.posts",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "posts"})
      }
   """
)
                        

// @LINE:6
def index : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.index",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + """"})
      }
   """
)
                        

// @LINE:13
def comments : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.comments",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "comments"})
      }
   """
)
                        
    
}
              
}
        


// @LINE:16
// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
package controllers.ref {


// @LINE:16
class ReverseAssets {
    

// @LINE:16
def at(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]), "GET", """ Map static resources from the /public folder to the /assets URL path""", _prefix + """assets/$file<.+>""")
)
                      
    
}
                          

// @LINE:13
// @LINE:12
// @LINE:11
// @LINE:10
// @LINE:9
// @LINE:8
// @LINE:7
// @LINE:6
class ReverseApplication {
    

// @LINE:10
def submitPost(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.submitPost(), HandlerDef(this, "controllers.Application", "submitPost", Seq(), "POST", """""", _prefix + """posts""")
)
                      

// @LINE:7
def submit(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.submit(), HandlerDef(this, "controllers.Application", "submit", Seq(), "GET", """""", _prefix + """submit""")
)
                      

// @LINE:12
def categories(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.categories(), HandlerDef(this, "controllers.Application", "categories", Seq(), "GET", """""", _prefix + """categories""")
)
                      

// @LINE:11
def deletePost(id:Long): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.deletePost(id), HandlerDef(this, "controllers.Application", "deletePost", Seq(classOf[Long]), "POST", """""", _prefix + """posts/$id<[^/]+>/delete""")
)
                      

// @LINE:8
def explore(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.explore(), HandlerDef(this, "controllers.Application", "explore", Seq(), "GET", """""", _prefix + """explore""")
)
                      

// @LINE:9
def posts(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.posts(), HandlerDef(this, "controllers.Application", "posts", Seq(), "GET", """""", _prefix + """posts""")
)
                      

// @LINE:6
def index(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Seq(), "GET", """ Home page""", _prefix + """""")
)
                      

// @LINE:13
def comments(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.comments(), HandlerDef(this, "controllers.Application", "comments", Seq(), "GET", """""", _prefix + """comments""")
)
                      
    
}
                          
}
        
    