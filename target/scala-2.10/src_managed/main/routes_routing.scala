// @SOURCE:C:/xampp/htdocs/ICS414/OCKB/conf/routes
// @HASH:5bc8ea54bf0ee8a0edddbf808b3330bb301c15d7
// @DATE:Sun Mar 23 11:41:43 HST 2014


import play.core._
import play.core.Router._
import play.core.j._

import play.api.mvc._
import play.libs.F

import Router.queryString

object Routes extends Router.Routes {

private var _prefix = "/"

def setPrefix(prefix: String) {
  _prefix = prefix
  List[(String,Routes)]().foreach {
    case (p, router) => router.setPrefix(prefix + (if(prefix.endsWith("/")) "" else "/") + p)
  }
}

def prefix = _prefix

lazy val defaultPrefix = { if(Routes.prefix.endsWith("/")) "" else "/" }


// @LINE:6
private[this] lazy val controllers_Application_index0 = Route("GET", PathPattern(List(StaticPart(Routes.prefix))))
        

// @LINE:7
private[this] lazy val controllers_Application_submit1 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("submit"))))
        

// @LINE:8
private[this] lazy val controllers_Application_explore2 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("explore"))))
        

// @LINE:9
private[this] lazy val controllers_Application_posts3 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("posts"))))
        

// @LINE:10
private[this] lazy val controllers_Application_submitPost4 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("posts"))))
        

// @LINE:11
private[this] lazy val controllers_Application_deletePost5 = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("posts/"),DynamicPart("id", """[^/]+""",true),StaticPart("/delete"))))
        

// @LINE:12
private[this] lazy val controllers_Application_categories6 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("categories"))))
        

// @LINE:15
private[this] lazy val controllers_Assets_at7 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/"),DynamicPart("file", """.+""",false))))
        
def documentation = List(("""GET""", prefix,"""controllers.Application.index()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """submit""","""controllers.Application.submit()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """explore""","""controllers.Application.explore()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """posts""","""controllers.Application.posts()"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """posts""","""controllers.Application.submitPost()"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """posts/$id<[^/]+>/delete""","""controllers.Application.deletePost(id:Long)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """categories""","""controllers.Application.categories()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
  case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
  case l => s ++ l.asInstanceOf[List[(String,String,String)]] 
}}
      

def routes:PartialFunction[RequestHeader,Handler] = {

// @LINE:6
case controllers_Application_index0(params) => {
   call { 
        invokeHandler(controllers.Application.index(), HandlerDef(this, "controllers.Application", "index", Nil,"GET", """ Home page""", Routes.prefix + """"""))
   }
}
        

// @LINE:7
case controllers_Application_submit1(params) => {
   call { 
        invokeHandler(controllers.Application.submit(), HandlerDef(this, "controllers.Application", "submit", Nil,"GET", """""", Routes.prefix + """submit"""))
   }
}
        

// @LINE:8
case controllers_Application_explore2(params) => {
   call { 
        invokeHandler(controllers.Application.explore(), HandlerDef(this, "controllers.Application", "explore", Nil,"GET", """""", Routes.prefix + """explore"""))
   }
}
        

// @LINE:9
case controllers_Application_posts3(params) => {
   call { 
        invokeHandler(controllers.Application.posts(), HandlerDef(this, "controllers.Application", "posts", Nil,"GET", """""", Routes.prefix + """posts"""))
   }
}
        

// @LINE:10
case controllers_Application_submitPost4(params) => {
   call { 
        invokeHandler(controllers.Application.submitPost(), HandlerDef(this, "controllers.Application", "submitPost", Nil,"POST", """""", Routes.prefix + """posts"""))
   }
}
        

// @LINE:11
case controllers_Application_deletePost5(params) => {
   call(params.fromPath[Long]("id", None)) { (id) =>
        invokeHandler(controllers.Application.deletePost(id), HandlerDef(this, "controllers.Application", "deletePost", Seq(classOf[Long]),"POST", """""", Routes.prefix + """posts/$id<[^/]+>/delete"""))
   }
}
        

// @LINE:12
case controllers_Application_categories6(params) => {
   call { 
        invokeHandler(controllers.Application.categories(), HandlerDef(this, "controllers.Application", "categories", Nil,"GET", """""", Routes.prefix + """categories"""))
   }
}
        

// @LINE:15
case controllers_Assets_at7(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        invokeHandler(controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]),"GET", """ Map static resources from the /public folder to the /assets URL path""", Routes.prefix + """assets/$file<.+>"""))
   }
}
        
}

}
     