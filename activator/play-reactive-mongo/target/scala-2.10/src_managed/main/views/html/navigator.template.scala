
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
/* navigator Template File */
object navigator extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template0[play.api.templates.HtmlFormat.Appendable] {

    /* navigator Template File */
    def apply/*2.2*/():play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*2.4*/("""
<ul class="menu">
	<a href="/granpa" class="main">Granpa
  	<img src=""""),_display_(Seq[Any](/*5.15*/routes/*5.21*/.Assets.at("images/abe.png"))),format.raw/*5.49*/("""" />
  </a>
	<a href="/father" class="main">Father
  	<img src=""""),_display_(Seq[Any](/*8.15*/routes/*8.21*/.Assets.at("images/homer.png"))),format.raw/*8.51*/("""" />
  </a>
	<a href="/son" class="main">Son
  	<img src=""""),_display_(Seq[Any](/*11.15*/routes/*11.21*/.Assets.at("images/bart.png"))),format.raw/*11.50*/("""" />
  </a>
</ul>"""))}
    }
    
    def render(): play.api.templates.HtmlFormat.Appendable = apply()
    
    def f:(() => play.api.templates.HtmlFormat.Appendable) = () => apply()
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Thu Apr 30 00:22:14 COT 2015
                    SOURCE: /Users/feliperojas/KnowBag/activator/play-reactive-mongo/app/views/navigator.scala.html
                    HASH: 9d290a527ea176b2e4ee88ba34318d2d97979338
                    MATRIX: 603->31|698->33|805->105|819->111|868->139|968->204|982->210|1033->240|1128->299|1143->305|1194->334
                    LINES: 19->2|22->2|25->5|25->5|25->5|28->8|28->8|28->8|31->11|31->11|31->11
                    -- GENERATED --
                */
            