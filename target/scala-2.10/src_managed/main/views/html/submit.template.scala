
package views.html

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.api.i18n._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import views.html._
/**/
object submit extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template1[String,play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(message: String):play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.19*/("""

"""),_display_(Seq[Any](/*3.2*/main("Submit")/*3.16*/ {_display_(Seq[Any](format.raw/*3.18*/("""

    <div class="container">
     <p>"""),_display_(Seq[Any](/*6.10*/message)),format.raw/*6.17*/("""</p>
     <p>Click here to logout!</p>
     <a href=""""),_display_(Seq[Any](/*8.16*/routes/*8.22*/.Application.logout())),format.raw/*8.43*/("""" class="btn btn-primary btn-lg btn-bottom">Log me out!</a>
    </div>

""")))})),format.raw/*11.2*/("""
"""))}
    }
    
    def render(message:String): play.api.templates.HtmlFormat.Appendable = apply(message)
    
    def f:((String) => play.api.templates.HtmlFormat.Appendable) = (message) => apply(message)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Mar 25 19:46:30 HST 2014
                    SOURCE: C:/Users/Alaan/Desktop/categoryModel/app/views/submit.scala.html
                    HASH: 4fa146fe7041c490460812583e871f2de5de2953
                    MATRIX: 775->1|886->18|925->23|947->37|986->39|1063->81|1091->88|1182->144|1196->150|1238->171|1345->247
                    LINES: 26->1|29->1|31->3|31->3|31->3|34->6|34->6|36->8|36->8|36->8|39->11
                    -- GENERATED --
                */
            