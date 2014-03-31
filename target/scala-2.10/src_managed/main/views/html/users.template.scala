
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
	<header>
		<hgroup>
			<h1> The user list. </h1>
			<h1> Displays current users. </h1>
		</hgroup>
	</header>
	
	
    <ul> 
		"""),_display_(Seq[Any](/*13.4*/for(user <- userList) yield /*13.25*/ {_display_(Seq[Any](format.raw/*13.27*/("""
		  <li>"""),_display_(Seq[Any](/*14.10*/user/*14.14*/.id)),format.raw/*14.17*/(""" - """),_display_(Seq[Any](/*14.21*/user/*14.25*/.email)),format.raw/*14.31*/(""" - """),_display_(Seq[Any](/*14.35*/user/*14.39*/.name)),format.raw/*14.44*/(""" - """),_display_(Seq[Any](/*14.48*/user/*14.52*/.status)),format.raw/*14.59*/(""" - """),_display_(Seq[Any](/*14.63*/user/*14.67*/.posts)),format.raw/*14.73*/("""</li>
		""")))})),format.raw/*15.4*/(""" 
	</ul>

""")))})),format.raw/*18.2*/("""
"""))}
    }
    
    def render(userList:List[User]): play.api.templates.HtmlFormat.Appendable = apply(userList)
    
    def f:((List[User]) => play.api.templates.HtmlFormat.Appendable) = (userList) => apply(userList)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Sun Mar 30 20:11:28 HST 2014
                    SOURCE: C:/Users/Tyler/Documents/GitHub/OCKB/app/views/users.scala.html
                    HASH: 914b54f32fd22d304a0dc091ab049d6e2117dfdf
                    MATRIX: 778->1|894->23|933->28|954->41|993->43|1167->182|1204->203|1244->205|1291->216|1304->220|1329->223|1369->227|1382->231|1410->237|1450->241|1463->245|1490->250|1530->254|1543->258|1572->265|1612->269|1625->273|1653->279|1694->289|1739->303
                    LINES: 26->1|29->1|31->3|31->3|31->3|41->13|41->13|41->13|42->14|42->14|42->14|42->14|42->14|42->14|42->14|42->14|42->14|42->14|42->14|42->14|42->14|42->14|42->14|43->15|46->18
                    -- GENERATED --
                */
            