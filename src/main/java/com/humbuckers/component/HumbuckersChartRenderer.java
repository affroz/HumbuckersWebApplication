package com.humbuckers.component;

import java.io.IOException;
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
		String jsonString = gson.toJson(list);
		jsonString=jsonString.replace("labelDataPoint", "label");
		jsonString=jsonString.replace("xDataPoint", "x");
		jsonString=jsonString.replace("yDataPoint", "y");
		
		writer.writeAttribute("list",jsonString, null);
        writer.endElement("div");
	}
	
}
