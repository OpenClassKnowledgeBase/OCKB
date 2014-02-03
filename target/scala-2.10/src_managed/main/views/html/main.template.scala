
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
    def apply/*1.2*/(page: String)(content: Html):play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.31*/("""

<!DOCTYPE html>

<html>
    <head>
        <title>"""),_display_(Seq[Any](/*7.17*/page)),format.raw/*7.21*/("""</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
        
        <!-- Load site-specific customizations after bootstrap. -->
        <link rel="stylesheet" media="screen" href=""""),_display_(Seq[Any](/*12.54*/routes/*12.60*/.Assets.at("stylesheets/main.css"))),format.raw/*12.94*/("""">
        <link rel="shortcut icon" type="image/png" href=""""),_display_(Seq[Any](/*13.59*/routes/*13.65*/.Assets.at("images/favicon.png"))),format.raw/*13.97*/("""">
        
        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
<script src="http://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.6.2/html5shiv.js"></script>
<script src="http://cdnjs.cloudflare.com/ajax/libs/respond.js/1.2.0/respond.js"></script>
<![endif]-->
    </head>
    <body>
    
     <!-- Responsive navbar -->
  <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
          <!-- Display three horizontal lines when navbar collapsed. -->
          <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#">OpenClassKnowledgeBase</a>
      </div>
      <div class="collapse navbar-collapse">
        <ul class="nav navbar-nav">
          <li class=""""),_display_(Seq[Any](/*35.23*/("active".when(page == "Home")))),format.raw/*35.54*/(""""><a href=""""),_display_(Seq[Any](/*35.66*/routes/*35.72*/.Application.index())),format.raw/*35.92*/("""">Home</a></li>
          <li class=""""),_display_(Seq[Any](/*36.23*/("active".when(page == "Submit")))),format.raw/*36.56*/(""""><a href=""""),_display_(Seq[Any](/*36.68*/routes/*36.74*/.Application.submit())),format.raw/*36.95*/("""">Submit</a></li>
          <li class=""""),_display_(Seq[Any](/*37.23*/("active".when(page == "Explore")))),format.raw/*37.57*/(""""><a href=""""),_display_(Seq[Any](/*37.69*/routes/*37.75*/.Application.explore())),format.raw/*37.97*/("""">Explore</a></li>
        </ul>
      </div>
    </div>
  </div>
      """),_display_(Seq[Any](/*42.8*/content)),format.raw/*42.15*/("""
      <!-- Load Bootstrap JavaScript components. HTMLUnit (used in testing) requires JQuery 1.8.3 or below). -->
      <script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
      <script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
    </body>
</html>
"""))}
    }
    
    def render(page:String,content:Html): play.api.templates.HtmlFormat.Appendable = apply(page)(content)
    
    def f:((String) => (Html) => play.api.templates.HtmlFormat.Appendable) = (page) => (content) => apply(page)(content)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Thu Jan 30 11:25:07 HST 2014
                    SOURCE: /Users/renzeereyes/School/ICS414/OCKB/play-ockb/app/views/main.scala.html
                    HASH: 3735dd2f9cd4ff659e4c246b56c52fbba801cb39
                    MATRIX: 778->1|901->30|989->83|1014->87|1376->413|1391->419|1447->453|1544->514|1559->520|1613->552|2652->1555|2705->1586|2753->1598|2768->1604|2810->1624|2884->1662|2939->1695|2987->1707|3002->1713|3045->1734|3121->1774|3177->1808|3225->1820|3240->1826|3284->1848|3392->1921|3421->1928
                    LINES: 26->1|29->1|35->7|35->7|40->12|40->12|40->12|41->13|41->13|41->13|63->35|63->35|63->35|63->35|63->35|64->36|64->36|64->36|64->36|64->36|65->37|65->37|65->37|65->37|65->37|70->42|70->42
                    -- GENERATED --
                */
            