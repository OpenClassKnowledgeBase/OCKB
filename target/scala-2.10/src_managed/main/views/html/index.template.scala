
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
object index extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template1[String,play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(message: String):play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.19*/("""

"""),_display_(Seq[Any](/*3.2*/main("Open Class Knowledge Base")/*3.35*/ {_display_(Seq[Any](format.raw/*3.37*/("""

    <div class="container-fluid center-block">

      <div class="row row-top-img">
        <div class="col-lg-12">
          <img src = """"),_display_(Seq[Any](/*9.24*/routes/*9.30*/.Assets.at("images/logo-bluegray.gif"))),format.raw/*9.68*/("""" class="img-responsive">
        </div>
      </div>

      <div class = "row row-top">
        <div class = "col-lg-12 txt-top">
          <h1 class="text-center"> Open Class Knowledge Base </h1>
          <p class = "text-center">Made by students, for students, the Open Class Knowledge Base features a collaborative environment that lets you interact with your classmates and professors like never before.</p>
        </div>
        <!--<div class="col-lg-6">
<img src = "comm-based.png" class = "img-responsive">
</div> -->
      </div>
      <hr>

      <div class = "row row-fluid">
        <div class = "col-md-offset-1 col-md-4 col-width">
          <img src = """"),_display_(Seq[Any](/*26.24*/routes/*26.30*/.Assets.at("images/content.png"))),format.raw/*26.62*/("""">
        </div>
        <div class = "col-md-offset-2 col-md-4 col-width col-txt">
          <h1> Content at your fingertips </h1>
          <p class = "pull-left">
          The Open Class Knowledge Base has content from all parts of ICS 211. From simple review topics to in-depth analysis of sorting algorithms, the Open Class Knowledge Base will have what you're looking for.
          </p>
        </div>
      </div>
      <hr>

      <div class = "row row-fluid">
        <div class = "col-md-offset-1 col-md-4 col-width col-txt">
          <h1> Course collaboration </h1>
          <p class="pull-right">The Open Class Knowledge Base is driven by the peers you learn with and the instructors you learn from. Connect, communicate, and collaborate with people you know and names you recognize.</p>
        </div>
        <div class="col-md-offset-2 col-md-4 col-width">
          <img src = """"),_display_(Seq[Any](/*43.24*/routes/*43.30*/.Assets.at("images/coursecollab.png"))),format.raw/*43.67*/("""">
        </div>
      </div>
      <hr>

      <div class = "row row-fluid">
        <div class = "col-md-offset-1 col-md-4 col-width">
          <img src = """"),_display_(Seq[Any](/*50.24*/routes/*50.30*/.Assets.at("images/past.png"))),format.raw/*50.59*/("""">
        </div>
        <div class="col-md-offset-2 col-md-4 col-width col-txt">
          <h1>Learn from the past, prepare for the future</h1>
          <p class = "pull-left">
          Got a question? Chances are, someone may have already asked it. Utilize this feature and collaborate with the community to find new answers to old questions.
          </p>
        </div>
      </div>
      <hr>

      <div class = "row row-fluid">
          <div class = "col-md-offset-1 col-md-4 col-width col-txt">
            <h1>Beyond the classroom</h1>
            <p class = "pull-left">
              Open Class Knowledge Base expands on the same topics covered in class by offering different perspectives on the same problem. Go beyond the textbook and spend less time searching and more time learning.
            </p>
          </div>
          <div class="col-md-offset-2 col-md-4 col-width">
            <img src = """"),_display_(Seq[Any](/*69.26*/routes/*69.32*/.Assets.at("images/designed.png"))),format.raw/*69.65*/("""">
          </div>
      </div>
      <hr>

      <div class = "row row-fluid">
        <div class = "col-md-offset-1 col-md-4 col-width">
          <img src = """"),_display_(Seq[Any](/*76.24*/routes/*76.30*/.Assets.at("images/mobile.png"))),format.raw/*76.61*/("""">
        </div>
        <div class="col-md-offset-2 col-md-4 col-width col-txt">
          <h1> Learn anywhere, anytime </h1>
          <p class = "pull-left">Access the Open Class Knowledge Base from your tablet, mobile phone, or your laptop. Contribute content and ask questions wherever you are, whenever you want.</p>
        </div>
      </div>
      <hr>

      <div class = "row-fluid bottom-text"><br>
        <div class = "text-center">
          <p>Sign in with your UH info to get started </p>
          <a href="#.html" class="btn btn-primary btn-lg btn-bottom">Sign in</a>
      </div>
    </div>

    </div>
        

    <footer>
      <p>&copy; OCKB 2014</p>
    </footer>

""")))})))}
    }
    
    def render(message:String): play.api.templates.HtmlFormat.Appendable = apply(message)
    
    def f:((String) => play.api.templates.HtmlFormat.Appendable) = (message) => apply(message)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Thu Mar 20 20:59:13 HST 2014
                    SOURCE: C:/Users/Alaan/Desktop/categoryModel/app/views/index.scala.html
                    HASH: 59ac2a95711b0425bb88b6f0c52cb9d282c5979e
                    MATRIX: 774->1|885->18|922->21|963->54|1002->56|1178->197|1192->203|1251->241|1959->913|1974->919|2028->951|2964->1851|2979->1857|3038->1894|3235->2055|3250->2061|3301->2090|4258->3011|4273->3017|4328->3050|4527->3213|4542->3219|4595->3250
                    LINES: 26->1|29->1|31->3|31->3|31->3|37->9|37->9|37->9|54->26|54->26|54->26|71->43|71->43|71->43|78->50|78->50|78->50|97->69|97->69|97->69|104->76|104->76|104->76
                    -- GENERATED --
                */
            