package com.humbuckers.component;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.renderkit.CoreRenderer;
import org.primefaces.util.WidgetBuilder;

import com.google.gson.Gson;


public class HumbuckersChartRenderer extends CoreRenderer {
	
	
	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		HumbuckersChart chart = (HumbuckersChart) component;
		@SuppressWarnings("unchecked")
		List<HumbuckersChartModule> list = (List<HumbuckersChartModule>) chart.getValue();
		encodeMarkup(context, chart,list);
		encodeScript(context, chart);
	}
	
	protected void encodeScript(FacesContext context, HumbuckersChart cal) throws IOException {
		String clientId = cal.getClientId(context);
        WidgetBuilder wb = getWidgetBuilder(context);
        wb.init("HumbuckersChart", cal.resolveWidgetVar(), clientId).finish();
	}
	
	
	protected void encodeMarkup(FacesContext context, HumbuckersChart chart, List<HumbuckersChartModule> list) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		String clientId = chart.getClientId(context);
		writer.startElement("div", null);
		writer.writeAttribute("id", clientId, null);
	
		writer.writeAttribute("type", chart.getType(), null);
		writer.writeAttribute("animationEnabled", chart.isAnimationEnabled(), null);
		
		writer.writeAttribute("chartTitle", chart.getChartTitle(), null);
		writer.writeAttribute("yAxisTitle", chart.getYaxisTitle(), null);
		writer.writeAttribute("xAxisTitle", chart.getXaxisTitle(), null);
		writer.writeAttribute("yAxisSuffix", chart.getYaxisSuffix(), null);
		writer.writeAttribute("xAxisSuffix", chart.getXaxisSuffix(), null);
		writer.writeAttribute("yAxisPrefix", chart.getYaxisPrefix(), null);
		writer.writeAttribute("xAxisPrefix", chart.getXaxisPrefix(), null);
		writer.writeAttribute("yAxisIncludeZero", chart.isYaxisIncludeZero(), null);
		writer.writeAttribute("xAxisIncludeZero", chart.isXaxisIncludeZero(), null);
		Gson gson = new Gson();
		if(chart.getType().equals("combined")) {
			List<HumbuckersChartModule> list1=list.get(0).getList();
			String jsonString1 = gson.toJson(list1);
			jsonString1=jsonString1.replace("labelDataPoint", "label");
			jsonString1=jsonString1.replace("01-Jan-3916", (CharSequence) new Date(2016, 1, 0));
			jsonString1=jsonString1.replace("xDataPoint", "x");
			jsonString1=jsonString1.replace("yDataPoint", "y");
			writer.writeAttribute("list1",jsonString1, null);
			
			List<HumbuckersChartModule> list2=list.get(1).getList();
			String jsonString2 = gson.toJson(list2);
			jsonString2=jsonString2.replace("labelDataPoint", "label");
			jsonString2=jsonString2.replace("xDataPoint", "x");
			jsonString2=jsonString2.replace("yDataPoint", "y");
			writer.writeAttribute("list2",jsonString2, null);
			
			List<HumbuckersChartModule> list3=list.get(2).getList();
			String jsonString3 = gson.toJson(list3);
			jsonString3=jsonString3.replace("labelDataPoint", "label");
			jsonString3=jsonString3.replace("xDataPoint", "x");
			jsonString3=jsonString3.replace("yDataPoint", "y");
			writer.writeAttribute("list3",jsonString3, null);
			
			List<HumbuckersChartModule> list4=list.get(3).getList();
			String jsonString4 = gson.toJson(list4);
			jsonString4=jsonString4.replace("labelDataPoint", "label");
			jsonString4=jsonString4.replace("xDataPoint", "x");
			jsonString4=jsonString4.replace("yDataPoint", "y");
			writer.writeAttribute("list4",jsonString4, null);

		}else {
			String jsonString = gson.toJson(list);
			jsonString=jsonString.replace("labelDataPoint", "label");
			jsonString=jsonString.replace("xDataPoint", "x");
			jsonString=jsonString.replace("yDataPoint", "y");
			writer.writeAttribute("list",jsonString, null);
		}
		
	
		
        writer.endElement("div");
	}
	
}
