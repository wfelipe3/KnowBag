
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
object index extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template0[play.api.templates.HtmlFormat.Appendable] {

    /**/
    def apply/*1.2*/():play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*1.4*/("""

"""),_display_(Seq[Any](/*3.2*/main("MongoDB CRUD & TablePage")/*3.34*/ {_display_(Seq[Any](format.raw/*3.36*/("""

		<h1>Here's the little family: edit it and make it bigger!</h1>
		
    <div class="centered main">

    		<a href="/granpa" class="main">Granpa
    			<img src=""""),_display_(Seq[Any](/*10.19*/routes/*10.25*/.Assets.at("images/abe.png"))),format.raw/*10.53*/("""" />
    		</a>
    		<a href="/father" class="main">Father
    			<img src=""""),_display_(Seq[Any](/*13.19*/routes/*13.25*/.Assets.at("images/homer.png"))),format.raw/*13.55*/("""" />
    		</a>
    		<a href="/fatherNoStrings" class="main">Father No Strings 
    			<img src=""""),_display_(Seq[Any](/*16.19*/routes/*16.25*/.Assets.at("images/homer.png"))),format.raw/*16.55*/("""" />
    		</a>
    		<a href="/son" class="main">Son
    			<img src=""""),_display_(Seq[Any](/*19.19*/routes/*19.25*/.Assets.at("images/bart.png"))),format.raw/*19.54*/("""" />
    		</a>

    </div>
    
""")))})),format.raw/*24.2*/("""
"""))}
    }
    
    def render(): play.api.templates.HtmlFormat.Appendable = apply()
    
    def f:(() => play.api.templates.HtmlFormat.Appendable) = () => apply()
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Thu Apr 30 00:22:14 COT 2015
                    SOURCE: /Users/feliperojas/KnowBag/activator/play-reactive-mongo/app/views/index.scala.html
                    HASH: debeb0e6ffe46069669a8b18fe578a75855093d2
                    MATRIX: 549->1|644->3|681->6|721->38|760->40|961->205|976->211|1026->239|1140->317|1155->323|1207->353|1342->452|1357->458|1409->488|1517->560|1532->566|1583->595|1648->629
                    LINES: 19->1|22->1|24->3|24->3|24->3|31->10|31->10|31->10|34->13|34->13|34->13|37->16|37->16|37->16|40->19|40->19|40->19|45->24
                    -- GENERATED --
                */
            