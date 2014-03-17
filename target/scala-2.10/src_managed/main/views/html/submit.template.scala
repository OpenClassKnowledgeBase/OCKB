
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
    </div>

""")))})),format.raw/*9.2*/("""
"""))}
    }
    
    def render(message:String): play.api.templates.HtmlFormat.Appendable = apply(message)
    
    def f:((String) => play.api.templates.HtmlFormat.Appendable) = (message) => apply(message)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Sun Mar 16 20:45:04 HST 2014
                    SOURCE: /Users/renzeereyes/School/ICS414/OCKB/app/views/submit.scala.html
                    HASH: 2840e9a691609630612ee27707b1d63c385d30b4
                    MATRIX: 775->1|886->18|923->21|945->35|984->37|1058->76|1086->83|1134->101
                    LINES: 26->1|29->1|31->3|31->3|31->3|34->6|34->6|37->9
                    -- GENERATED --
                */
            