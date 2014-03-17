// @SOURCE:/Users/renzeereyes/School/ICS414/OCKB/conf/routes
// @HASH:00b0159daea7494133831e494dc28a41a0ad60b3
// @DATE:Mon Mar 17 10:42:51 HST 2014


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
private[this] lazy val controllers_Application_comments3 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("comments"))))
        

// @LINE:12
private[this] lazy val controllers_Assets_at4 = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("assets/"),DynamicPart("file", """.+""",false))))
        
def documentation = List(("""GET""", prefix,"""controllers.Application.index()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """submit""","""controllers.Application.submit()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """explore""","""controllers.Application.explore()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """comments""","""controllers.Application.comments()"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
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
case controllers_Application_comments3(params) => {
   call { 
        invokeHandler(controllers.Application.comments(), HandlerDef(this, "controllers.Application", "comments", Nil,"GET", """""", Routes.prefix + """comments"""))
   }
}
        

// @LINE:12
case controllers_Assets_at4(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        invokeHandler(controllers.Assets.at(path, file), HandlerDef(this, "controllers.Assets", "at", Seq(classOf[String], classOf[String]),"GET", """ Map static resources from the /public folder to the /assets URL path""", Routes.prefix + """assets/$file<.+>"""))
   }
}
        
}

}
     