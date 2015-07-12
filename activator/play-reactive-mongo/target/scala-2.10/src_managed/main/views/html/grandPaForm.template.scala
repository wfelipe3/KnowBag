
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
object grandPaForm extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template3[Form[models.GrandPa],RequestHeader,controllers.helper.CRUDerPaths,play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/(gpform: Form[models.GrandPa])(implicit request: RequestHeader, paths: controllers.helper.CRUDerPaths):play.api.templates.HtmlFormat.Appendable = {
        _display_ {import helper._


Seq[Any](format.raw/*1.104*/("""

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
	    		"""),_display_(Seq[Any](/*16.9*/inputText(gpform("id")))),format.raw/*16.32*/("""
	    	</li>
	    	<li>
	    		"""),_display_(Seq[Any](/*19.9*/inputText(gpform("name")))),format.raw/*19.34*/("""
	    	</li>
	    	<li>
	    		"""),_display_(Seq[Any](/*22.9*/inputText(gpform("sons")))),format.raw/*22.34*/("""
	    		<label for="sons_field" class="little">Please, insert elements like a JSON array (i.e. ["526f9c77e4b083eec04dee31", "526e9709e4b054fb03a1574c"])</label>
	    	</li>
	    	<li class="remove">
	    		<input id="remove" type="checkbox" name="remove" />
	    		<label for="remove"></label>
	    	</li>
	    
	    </ul>
	    
	    <input type="submit" />
     	<img src=""""),_display_(Seq[Any](/*33.18*/routes/*33.24*/.Assets.at("images/abe.png"))),format.raw/*33.52*/("""" />
    </div>
""")))})))}
    }
    
    def render(gpform:Form[models.GrandPa],request:RequestHeader,paths:controllers.helper.CRUDerPaths): play.api.templates.HtmlFormat.Appendable = apply(gpform)(request,paths)
    
    def f:((Form[models.GrandPa]) => (RequestHeader,controllers.helper.CRUDerPaths) => play.api.templates.HtmlFormat.Appendable) = (gpform) => (request,paths) => apply(gpform)(request,paths)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Thu Apr 30 00:22:14 COT 2015
                    SOURCE: /Users/feliperojas/KnowBag/activator/play-reactive-mongo/app/views/grandPaForm.scala.html
                    HASH: 807c7adc2bdeb32766cc5a413c776922813276ee
                    MATRIX: 621->1|834->103|862->122|922->148|954->159|997->168|1010->174|1076->232|1115->234|1202->285|1217->291|1273->325|1367->383|1382->389|1436->421|1542->492|1587->515|1654->547|1701->572|1768->604|1815->629|2226->1004|2241->1010|2291->1038
                    LINES: 19->1|23->1|25->4|27->6|27->6|30->9|30->9|30->9|30->9|31->10|31->10|31->10|32->11|32->11|32->11|37->16|37->16|40->19|40->19|43->22|43->22|54->33|54->33|54->33
                    -- GENERATED --
                */
            