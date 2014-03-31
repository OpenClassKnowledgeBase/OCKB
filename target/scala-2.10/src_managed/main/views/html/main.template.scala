
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
object main extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template2[String,Html,play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(title: String)(content: Html):play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.32*/("""

<!DOCTYPE html>

<html>
    <head>
        <title>"""),_display_(Seq[Any](/*7.17*/title)),format.raw/*7.22*/("""</title>
        <link href="http://netdna.bootstrapcdn.com/bootswatch/3.1.1/flatly/bootstrap.min.css" rel="stylesheet">
    	<link href="http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
        <link rel="stylesheet" media="screen" href=""""),_display_(Seq[Any](/*10.54*/routes/*10.60*/.Assets.at("stylesheets/main.css"))),format.raw/*10.94*/("""">
        <link rel="shortcut icon" type="image/png" href=""""),_display_(Seq[Any](/*11.59*/routes/*11.65*/.Assets.at("images/favicon.png"))),format.raw/*11.97*/("""">
        <script src="https://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js" type="text/javascript"></script>
        <script src=""""),_display_(Seq[Any](/*13.23*/routes/*13.29*/.Assets.at("javascripts/jquery-1.9.0.min.js"))),format.raw/*13.74*/("""" type="text/javascript"></script>
    </head>
    <body>
    	<div class="navbar-default navbar-fixed-top">
	      <div class="container-fluid">
	        <div class="navbar-header">
	          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
	            <span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
	          </button>
	          <a class="navbar-brand" href="#">OCKB</a>
	        </div>
	        <div class="navbar-collapse collapse">
	          <form class="navbar-form navbar-right" action=""""),_display_(Seq[Any](/*25.60*/routes/*25.66*/.Application.comments())),format.raw/*25.89*/("""">
	            <button type="submit" class="btn btn-default">Sign in</button>
	          </form>
	        </div>
	      </div>
    	</div>
        """),_display_(Seq[Any](/*31.10*/content)),format.raw/*31.17*/("""
    </body>
</html>
"""))}
    }
    
    def render(title:String,content:Html): play.api.templates.HtmlFormat.Appendable = apply(title)(content)
    
    def f:((String) => (Html) => play.api.templates.HtmlFormat.Appendable) = (title) => (content) => apply(title)(content)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Sun Mar 30 20:08:35 HST 2014
                    SOURCE: C:/Users/Tyler/Documents/GitHub/OCKB/app/views/main.scala.html
                    HASH: 77f76321c0e4523dffbb2e87ce19a9f3d96554aa
                    MATRIX: 778->1|902->31|996->90|1022->95|1342->379|1357->385|1413->419|1511->481|1526->487|1580->519|1766->669|1781->675|1848->720|2490->1326|2505->1332|2550->1355|2741->1510|2770->1517
                    LINES: 26->1|29->1|35->7|35->7|38->10|38->10|38->10|39->11|39->11|39->11|41->13|41->13|41->13|53->25|53->25|53->25|59->31|59->31
                    -- GENERATED --
                */
            