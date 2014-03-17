
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
object comments extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template1[List[Comment],play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(commentList: List[Comment]):play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.30*/("""

"""),_display_(Seq[Any](/*3.2*/main("Comments")/*3.18*/ {_display_(Seq[Any](format.raw/*3.20*/("""

    <ul> 
		"""),_display_(Seq[Any](/*6.4*/for(comment <- commentList) yield /*6.31*/ {_display_(Seq[Any](format.raw/*6.33*/("""
		  <li>d</li>
		""")))})),format.raw/*8.4*/(""" 
	</ul>

""")))})),format.raw/*11.2*/("""
"""))}
    }
    
    def render(commentList:List[Comment]): play.api.templates.HtmlFormat.Appendable = apply(commentList)
    
    def f:((List[Comment]) => play.api.templates.HtmlFormat.Appendable) = (commentList) => apply(commentList)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Mon Mar 17 08:30:12 HST 2014
                    SOURCE: /Users/renzeereyes/School/ICS414/OCKB/app/views/comments.scala.html
                    HASH: 4ed38b1160ec084b5377bfe7866ddd1c35448cea
                    MATRIX: 784->1|906->29|943->32|967->48|1006->50|1055->65|1097->92|1136->94|1185->113|1227->124
                    LINES: 26->1|29->1|31->3|31->3|31->3|34->6|34->6|34->6|36->8|39->11
                    -- GENERATED --
                */
            