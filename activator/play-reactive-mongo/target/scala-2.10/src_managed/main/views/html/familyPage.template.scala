
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
/* familyTable Template File */
object familyPage extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template4[String,play.mvc.Call,Seq[String],controllers.helper.CRUDerPaths,play.api.templates.HtmlFormat.Appendable] {

    /* familyTable Template File */
    def apply/*2.2*/(name: String, table: play.mvc.Call, elemsToDisplay: Seq[String])(implicit paths: controllers.helper.CRUDerPaths):play.api.templates.HtmlFormat.Appendable = {
        _display_ {

Seq[Any](format.raw/*2.115*/("""

"""),_display_(Seq[Any](/*4.2*/main(s"${name} - Page")/*4.25*/ {_display_(Seq[Any](format.raw/*4.27*/("""
	<div class="centered">
		"""),_display_(Seq[Any](/*6.4*/navigator())),format.raw/*6.15*/("""
	
	<div>
			 <ul>
					<a href=""""),_display_(Seq[Any](/*10.16*/paths/*10.21*/.create)),format.raw/*10.28*/("""" class="main">
						<h3>Create New """),_display_(Seq[Any](/*11.23*/name)),format.raw/*11.27*/("""</h3>
					</a>
			</ul>					
		</div>
	
	<table id=""""),_display_(Seq[Any](/*16.14*/name)),format.raw/*16.18*/("""">
	<thead>
		<tr>
			"""),_display_(Seq[Any](/*19.5*/elemsToDisplay/*19.19*/.map(h => <th>{h}</th> ))),format.raw/*19.43*/("""
		</tr>
	</thead>
	<tbody></tbody>
	</table>
	
	</div>
	
	<script>
		$(document).ready(function() """),format.raw/*28.32*/("""{"""),format.raw/*28.33*/("""

			//Using dataTable (jQuery plugin: http://datatables.net/)

			oTable = $("#"""),_display_(Seq[Any](/*32.18*/name)),format.raw/*32.22*/("""").dataTable("""),format.raw/*32.35*/("""{"""),format.raw/*32.36*/("""

				"iDisplayLength" : 20,
				"bProcessing" : true,
				"bAutoWidth" : false,
				"aaSorting" : [ [ 1, "asc" ] ],
				"fnRowCallback" : function(nRow, aData, iDisplayIndex) """),format.raw/*38.60*/("""{"""),format.raw/*38.61*/("""
					$('td:eq(0)', nRow).html('<a href=""""),_display_(Seq[Any](/*39.42*/paths/*39.47*/.edit)),format.raw/*39.52*/("""/' + aData[0] + '">' + aData[0] + '</a>');
					return nRow;
				"""),format.raw/*41.5*/("""}"""),format.raw/*41.6*/(""",
				"bServerSide" : true,
				"bJQueryUI" : true,
				"bRetrieve" : true,
				"sAjaxSource" : """"),_display_(Seq[Any](/*45.23*/table)),format.raw/*45.28*/("""",
				"aoColumnDefs" : [ """),format.raw/*46.24*/("""{"""),format.raw/*46.25*/("""
					"bSearchable" : false,
					//"bVisible" : false,
					"aTargets" : [ 0 ]
				"""),format.raw/*50.5*/("""}"""),format.raw/*50.6*/(""", """),format.raw/*50.8*/("""{"""),format.raw/*50.9*/("""
					"sWidth" : "20%",
					"aTargets" : [ 1 ]
				"""),format.raw/*53.5*/("""}"""),format.raw/*53.6*/(""" ]

			"""),format.raw/*55.4*/("""}"""),format.raw/*55.5*/(""");

		"""),format.raw/*57.3*/("""}"""),format.raw/*57.4*/(""")
	</script>
	
""")))})))}
    }
    
    def render(name:String,table:play.mvc.Call,elemsToDisplay:Seq[String],paths:controllers.helper.CRUDerPaths): play.api.templates.HtmlFormat.Appendable = apply(name,table,elemsToDisplay)(paths)
    
    def f:((String,play.mvc.Call,Seq[String]) => (controllers.helper.CRUDerPaths) => play.api.templates.HtmlFormat.Appendable) = (name,table,elemsToDisplay) => (paths) => apply(name,table,elemsToDisplay)(paths)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Thu Apr 30 00:22:14 COT 2015
                    SOURCE: /Users/feliperojas/KnowBag/activator/play-reactive-mongo/app/views/familyPage.scala.html
                    HASH: 2bf86dbfd2d1d33304ce86a03aa9d4f7f54f316a
                    MATRIX: 672->33|880->146|917->149|948->172|987->174|1049->202|1081->213|1151->247|1165->252|1194->259|1268->297|1294->301|1384->355|1410->359|1468->382|1491->396|1537->420|1664->519|1693->520|1810->601|1836->605|1877->618|1906->619|2110->795|2139->796|2217->838|2231->843|2258->848|2350->913|2378->914|2512->1012|2539->1017|2593->1043|2622->1044|2733->1128|2761->1129|2790->1131|2818->1132|2897->1184|2925->1185|2959->1192|2987->1193|3020->1199|3048->1200
                    LINES: 19->2|22->2|24->4|24->4|24->4|26->6|26->6|30->10|30->10|30->10|31->11|31->11|36->16|36->16|39->19|39->19|39->19|48->28|48->28|52->32|52->32|52->32|52->32|58->38|58->38|59->39|59->39|59->39|61->41|61->41|65->45|65->45|66->46|66->46|70->50|70->50|70->50|70->50|73->53|73->53|75->55|75->55|77->57|77->57
                    -- GENERATED --
                */
            