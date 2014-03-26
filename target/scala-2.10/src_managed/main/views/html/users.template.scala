
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
object users extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template1[List[User],play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(userList: List[User]):play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.24*/("""

"""),_display_(Seq[Any](/*3.2*/main("Users")/*3.15*/ {_display_(Seq[Any](format.raw/*3.17*/("""

    <ul> 
		"""),_display_(Seq[Any](/*6.4*/for(user <- userList) yield /*6.25*/ {_display_(Seq[Any](format.raw/*6.27*/("""
		  <li>"""),_display_(Seq[Any](/*7.10*/user/*7.14*/.email)),format.raw/*7.20*/(""" - """),_display_(Seq[Any](/*7.24*/user/*7.28*/.name)),format.raw/*7.33*/(""" - """),_display_(Seq[Any](/*7.37*/user/*7.41*/.password)),format.raw/*7.50*/(""" - """),_display_(Seq[Any](/*7.54*/user/*7.58*/.email)),format.raw/*7.64*/(""" - """),_display_(Seq[Any](/*7.68*/user/*7.72*/.posts)),format.raw/*7.78*/("""</li>
		""")))})),format.raw/*8.4*/(""" 
	</ul>

""")))})),format.raw/*11.2*/("""
"""))}
    }
    
    def render(userList:List[User]): play.api.templates.HtmlFormat.Appendable = apply(userList)
    
    def f:((List[User]) => play.api.templates.HtmlFormat.Appendable) = (userList) => apply(userList)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Mar 25 20:36:34 HST 2014
                    SOURCE: C:/Users/Matt/Documents/GitHub/OCKB/app/views/users.scala.html
                    HASH: a662206fdf4251176a4f88af4c1dc791644aff44
                    MATRIX: 778->1|894->23|933->28|954->41|993->43|1045->61|1081->82|1120->84|1166->95|1178->99|1205->105|1244->109|1256->113|1282->118|1321->122|1333->126|1363->135|1402->139|1414->143|1441->149|1480->153|1492->157|1519->163|1559->173|1604->187
                    LINES: 26->1|29->1|31->3|31->3|31->3|34->6|34->6|34->6|35->7|35->7|35->7|35->7|35->7|35->7|35->7|35->7|35->7|35->7|35->7|35->7|35->7|35->7|35->7|36->8|39->11
                    -- GENERATED --
                */
            