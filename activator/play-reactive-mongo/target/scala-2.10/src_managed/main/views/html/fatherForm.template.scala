
package views.html

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import play.api.i18n._
import play.api.mvc._
import play.api.data._
import views.html._
/**/
object fatherForm extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template3[Form[models.Father],RequestHeader,controllers.helper.CRUDerPaths,play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(faform: Form[models.Father])(implicit request: RequestHeader, paths: controllers.helper.CRUDerPaths):play.api.templates.HtmlFormat.Appendable = {
        _display_ {import helper._


Seq[Any](format.raw/*1.103*/("""

"""),format.raw/*4.1*/("""
<div class="centered">
	"""),_display_(Seq[Any](/*6.3*/navigator())),format.raw/*6.14*/("""
</ul>

"""),_display_(Seq[Any](/*9.2*/helper/*9.8*/.form(action = new play.api.mvc.Call("POST",paths.submit))/*9.66*/ {_display_(Seq[Any](format.raw/*9.68*/("""
     <link rel="stylesheet" media="screen" href=""""),_display_(Seq[Any](/*10.51*/routes/*10.57*/.Assets.at("stylesheets/main.css"))),format.raw/*10.91*/("""">
     <link rel="shortcut icon" type="image/png" href=""""),_display_(Seq[Any](/*11.56*/routes/*11.62*/.Assets.at("images/favicon.png"))),format.raw/*11.94*/("""">
     <div class="centered form">
     
	    <ul>
	    	<li>
	    		"""),_display_(Seq[Any](/*16.9*/inputText(faform("id")))),format.raw/*16.32*/("""
	    	</li>
	    	<li>
	    		"""),_display_(Seq[Any](/*19.9*/inputText(faform("name")))),format.raw/*19.34*/("""
	    	</li>
	    	<li>
	    		"""),_display_(Seq[Any](/*22.9*/inputText(faform("description")))),format.raw/*22.41*/("""
	    	</li>
	    	<li>
	    		"""),_display_(Seq[Any](/*25.9*/inputText(faform("age")))),format.raw/*25.33*/("""
	    	</li>
	    	<li>
	    		"""),_display_(Seq[Any](/*28.9*/inputText(faform("gp")))),format.raw/*28.32*/("""
	    		<label for="gp_field" class="little">Insert Id</label>
	    	</li>
	    	<li>
	    		"""),_display_(Seq[Any](/*32.9*/inputText(faform("sons")))),format.raw/*32.34*/("""
	    		<label for="sons_field" class="little">Please, insert elements like a JSON array (i.e. ["526f9c77e4b083eec04dee31", "526e9709e4b054fb03a1574c"])</label>
	    	</li>
	    	<li class="remove">
	    		<input id="remove" type="checkbox" name="remove" />
	    		<label for="remove"></label>
	    	</li>
	    
	    </ul>
    
    	<input type="submit" />
     	<img src=""""),_display_(Seq[Any](/*43.18*/routes/*43.24*/.Assets.at("images/homer.png"))),format.raw/*43.54*/("""" />
    </div>
""")))})))}
    }
    
    def render(faform:Form[models.Father],request:RequestHeader,paths:controllers.helper.CRUDerPaths): play.api.templates.HtmlFormat.Appendable = apply(faform)(request,paths)
    
    def f:((Form[models.Father]) => (RequestHeader,controllers.helper.CRUDerPaths) => play.api.templates.HtmlFormat.Appendable) = (faform) => (request,paths) => apply(faform)(request,paths)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Thu Apr 30 00:22:14 COT 2015
                    SOURCE: /Users/feliperojas/KnowBag/activator/play-reactive-mongo/app/views/fatherForm.scala.html
                    HASH: 9f77c91724babb778307000a12ba05b1e001209f
                    MATRIX: 619->1|831->102|859->121|919->147|951->158|994->167|1007->173|1073->231|1112->233|1199->284|1214->290|1270->324|1364->382|1379->388|1433->420|1539->491|1584->514|1651->546|1698->571|1765->603|1819->635|1886->667|1932->691|1999->723|2044->746|2173->840|2220->865|2630->1239|2645->1245|2697->1275
                    LINES: 19->1|23->1|25->4|27->6|27->6|30->9|30->9|30->9|30->9|31->10|31->10|31->10|32->11|32->11|32->11|37->16|37->16|40->19|40->19|43->22|43->22|46->25|46->25|49->28|49->28|53->32|53->32|64->43|64->43|64->43
                    -- GENERATED --
                */
            