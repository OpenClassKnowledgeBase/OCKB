
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
object post extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template1[List[Comment],play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(commentList: List[Comment]):play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.30*/("""

"""),_display_(Seq[Any](/*3.2*/main("Post")/*3.14*/ {_display_(Seq[Any](format.raw/*3.16*/("""

	<div class="container">
      <ol class="breadcrumb">
        <li>
          <a href="../../index.html">Home</a>
        </li>
        <li>
          <a href="../../Categories.html">Categories</a>
        </li>
        <li class="active">Generics</li>
      </ol>
      <form class="navbar-form navbar-right">
        <div class="form-group">
          <input class="form-control" placeholder="Search for ..." type="text">
        </div>
        <button type="submit" class="btn btn-default">Search</button>
      </form>
      <h1>Generics</h1>
      <section>
          <div class="panel panel-default">
            <div class="panel-heading">
              <span class="label label-info">#148</span><a href = "" class = "postLink">Does anyone know of a good explanation for Generics?</a>
            </div>
            <div class="panel-body">
              <div class = "row">
                <div class = "col-sm-11 content">
                 I've been Googling around for ages and I still can't figure out how Generics work. Does anybody know of a good site or video that really helped them understand this topic? Maybe with examples?
                </div>
                <div class = "hidden-xs col-sm-1 votes">
                  <div>
                    <span class = "glyphicon glyphicon glyphicon-chevron-up"></span>
                  </div>
                  <div class = "votes">
                    10
                  </div>
                  <div>
                    <span class = "glyphicon glyphicon glyphicon-chevron-down"></span>
                  </div>
                </div>
              </div>
            </div>
            <div class="panel-footer">
              Submitted by <span class="badge">Paul</span>
              <div class = "date">
                Asked on 1/14/14 at 3:43 pm
              </div>
            </div>
          </div>
        </section>
        <hr>
        <h3>3 Answers </h3>
        <hr>
        
        <!-- Replies -->
        """),_display_(Seq[Any](/*58.10*/for(comment <- commentList) yield /*58.37*/ {_display_(Seq[Any](format.raw/*58.39*/("""
          <div class="panel-body">
            <div class = "row">
              <div class = "col-sm-11 content">
                 """),_display_(Seq[Any](/*62.19*/comment/*62.26*/.content)),format.raw/*62.34*/("""
              </div>
              
              <div class = "hidden-xs col-sm-1 votes">
                <div>
                  <span class = "glyphicon glyphicon glyphicon-chevron-up"></span>
                </div>
                <div class = "votes">
                  1
                </div>
                <div>
                  <span class = "glyphicon glyphicon glyphicon-chevron-down"></span>
                </div>
              </div>
            </div>
            <div class = "authorReply">
              Submitted by <span class = "badge">"""),_display_(Seq[Any](/*78.51*/comment/*78.58*/.author)),format.raw/*78.65*/("""</span><br>
              Answered """),_display_(Seq[Any](/*79.25*/comment/*79.32*/.submission_date)),format.raw/*79.48*/("""
            </div>
          </div>
          <hr>
		  
		""")))})),format.raw/*84.4*/("""
		<!-- End of Replies -->
		
        <!-- Post a Reply -->
        <hr>
        <h3>Post a Reply</h3>
        <form id="editor-form">
          <textarea class="editor" name="editor1" id="editor1" rows="10" cols="80"></textarea>
          <script>CKEDITOR.replace( 'editor1' );</script>
        </form>

        </div>
      <!-- End toolbar -->
      <div class="container-fluid">
      <div class="row">
        <div class="col-lg-10"></div>
        <div class="col-lg-2">
      <button type="submit" id="submit-btn" class="btn btn-success btn-lg">Submit</button>
      <script>
        $('#submit-btn').click(function () """),format.raw/*103.44*/("""{"""),format.raw/*103.45*/("""
          // get date
          var d = new Date();
          var month = d.getMonth()+1;
          var day = d.getDate();
          var year = d.getFullYear().toString().substr(2,2);
          var outputDate = (month<10 ? '0' : '') + month + '/' +
            (day<10 ? '0' : '') + day + '/' + year;
          // get time
          var hours = d.getHours();
          var minutes = d.getMinutes();
          var hours = hours%24; 
          var mid='am';
          if(hours==0)"""),format.raw/*116.23*/("""{"""),format.raw/*116.24*/(""" //At 00 hours we need to show 12 am
            hours=12;
          """),format.raw/*118.11*/("""}"""),format.raw/*118.12*/("""
          else if(hours>12)"""),format.raw/*119.28*/("""{"""),format.raw/*119.29*/("""
            hours=hours%12;
            mid='pm';
          """),format.raw/*122.11*/("""}"""),format.raw/*122.12*/("""
          if (minutes < 10)"""),format.raw/*123.28*/("""{"""),format.raw/*123.29*/("""
            minutes = "0" + minutes;
          """),format.raw/*125.11*/("""}"""),format.raw/*125.12*/("""
          outputTime = hours + ":" + minutes + " " + mid;
          var input = CKEDITOR.instances.editor1.getData();
          CKEDITOR.instances.editor1.setData("");
          var newEntry = $('<hr><div class = "panel-body"><div class = "row"><div class = "col-sm-11 content">' + input + '</div><div class = "col-sm-1 votes"><div><span class = "glyphicon glyphicon-chevron-up"></div><div class = "votes">0</div><div><span class = "glyphicon glyphicon-chevron-down"></div></div></div><div class = "authorReply">Submitted by <span class = "badge">You</span><br>Answered ' + outputDate + ' at ' + outputTime + '</div></div>');
          var lastReply = $(".panel-body").get(-1);
          console.log(lastReply);
          $(newEntry).insertAfter(lastReply);
          """),format.raw/*133.11*/("""}"""),format.raw/*133.12*/(""");
      </script>

        </div>
    </div>
  </div>

  </body>

</html>


""")))})),format.raw/*145.2*/("""
"""))}
    }
    
    def render(commentList:List[Comment]): play.api.templates.HtmlFormat.Appendable = apply(commentList)
    
    def f:((List[Comment]) => play.api.templates.HtmlFormat.Appendable) = (commentList) => apply(commentList)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Tue Mar 25 17:09:31 HST 2014
                    SOURCE: /Users/renzeereyes/School/ICS414/OCKB/app/views/post.scala.html
                    HASH: 3a301964a3cbb309b8c14ae9cdbc076e131c0a69
                    MATRIX: 780->1|902->29|939->32|959->44|998->46|3029->2041|3072->2068|3112->2070|3282->2204|3298->2211|3328->2219|3925->2780|3941->2787|3970->2794|4042->2830|4058->2837|4096->2853|4187->2913|4841->3538|4871->3539|5379->4018|5409->4019|5507->4088|5537->4089|5594->4117|5624->4118|5714->4179|5744->4180|5801->4208|5831->4209|5908->4257|5938->4258|6736->5027|6766->5028|6876->5106
                    LINES: 26->1|29->1|31->3|31->3|31->3|86->58|86->58|86->58|90->62|90->62|90->62|106->78|106->78|106->78|107->79|107->79|107->79|112->84|131->103|131->103|144->116|144->116|146->118|146->118|147->119|147->119|150->122|150->122|151->123|151->123|153->125|153->125|161->133|161->133|173->145
                    -- GENERATED --
                */
            