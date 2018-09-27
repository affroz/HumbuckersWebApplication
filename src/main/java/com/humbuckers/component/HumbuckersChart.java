package com.humbuckers.component;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;

import org.primefaces.component.api.UIData;
import org.primefaces.util.ComponentUtils;

@FacesComponent(value = "humbuckersChart")
@ResourceDependencies({
        @ResourceDependency(library = "humbuckers-components", name = "humbuckersChart/jquery-1.11.1.min.js"),
        @ResourceDependency(library = "humbuckers-components", name = "humbuckersChart/jquery.canvasjs.min.js"),
        @ResourceDependency(library = "humbuckers-components", name = "humbuckersChart/humbuckersChart.js")})
public class HumbuckersChart extends UIData implements org.primefaces.component.api.Widget {
	
	
    public HumbuckersChart() {
        setRendererType("HumbuckersChartRenderer");
    }

    @Override
    public String getFamily() {
        return "HumbuckersChart";
    }

	public enum PropertyKeys {

		widgetVar,
		type,
		animationEnabled,
		chartTitle,
		yaxisTitle,
		xaxisTitle,
		yaxisSuffix,
		xaxisSuffix,
		yaxisPrefix,
		xaxisPrefix,
		yaxisIncludeZero,
		xaxisIncludeZero;

		String toString;

		PropertyKeys(String toString) {
			this.toString = toString;
		}

		PropertyKeys() {}

		public String toString() {
			return ((this.toString != null) ? this.toString : super.toString());
		}
	}

	
	public java.lang.String getWidgetVar() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.widgetVar, null);
	}
	public void setWidgetVar(java.lang.String _widgetVar) {
		getStateHelper().put(PropertyKeys.widgetVar, _widgetVar);
	}

	public java.lang.String getType() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.type, null);
	}
	public void setType(java.lang.String _type) {
		getStateHelper().put(PropertyKeys.type, _type);
	}

	public String resolveWidgetVar() {
		return ComponentUtils.resolveWidgetVar(getFacesContext(), this);
	}
	public boolean isAnimationEnabled() {
		return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.animationEnabled, false);
	}
	public void setAnimationEnabled(boolean _animationEnabled) {
		getStateHelper().put(PropertyKeys.animationEnabled, _animationEnabled);
	}
	
	public java.lang.String getChartTitle() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.chartTitle, null);
	}
	public void setChartTitle(java.lang.String _chartTitle) {
		getStateHelper().put(PropertyKeys.chartTitle, _chartTitle);
	}
	
	public java.lang.String getYaxisTitle() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.yaxisTitle, null);
	}
	public void setYaxisTitle(java.lang.String _yaxisTitle) {
		getStateHelper().put(PropertyKeys.yaxisTitle, _yaxisTitle);
	}
	
	public java.lang.String getXaxisTitle() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.xaxisTitle, null);
	}
	public void setXaxisTitle(java.lang.String _xaxisTitle) {
		getStateHelper().put(PropertyKeys.xaxisTitle, _xaxisTitle);
	}	
	
	public java.lang.String getYaxisSuffix() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.yaxisSuffix, null);
	}
	public void setYaxisSuffix(java.lang.String _yaxisSuffix) {
		getStateHelper().put(PropertyKeys.yaxisSuffix, _yaxisSuffix);
	}
	public java.lang.String getXaxisSuffix() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.xaxisSuffix, null);
	}
	public void setXaxisSuffix(java.lang.String _xaxisSuffix) {
		getStateHelper().put(PropertyKeys.xaxisSuffix, _xaxisSuffix);
	}
	
	public java.lang.String getYaxisPrefix() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.yaxisPrefix, null);
	}
	public void setYaxisPrefix(java.lang.String _yaxisPrefix) {
		getStateHelper().put(PropertyKeys.yaxisPrefix, _yaxisPrefix);
	}
	
	public java.lang.String getXaxisPrefix() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.xaxisPrefix, null);
	}
	public void setXaxisPrefix(java.lang.String _xaxisPrefix) {
		getStateHelper().put(PropertyKeys.xaxisPrefix, _xaxisPrefix);
	}
	
	
	
	public boolean isYaxisIncludeZero() {
		return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.yaxisIncludeZero, false);
	}
	public void setYaxisIncludeZero(boolean _yaxisIncludeZero) {
		getStateHelper().put(PropertyKeys.yaxisIncludeZero, _yaxisIncludeZero);
	}
	
	
	public boolean isXaxisIncludeZero() {
		return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.xaxisIncludeZero, false);
	}
	public void setXaxisIncludeZero(boolean _xaxisIncludeZero) {
		getStateHelper().put(PropertyKeys.xaxisIncludeZero, _xaxisIncludeZero);
	}
	
	
}
