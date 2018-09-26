package com.humbuckers.utils;


import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.web.context.request.FacesRequestAttributes;

/**
 * @author Ghassan
 *
 */

public class ViewScope implements Scope {

	public static final String VIEW_SCOPE_CALLBACKS = "viewScope.callbacks";

	@Override
	public synchronized Object get(String name, ObjectFactory<?> objectFactory) {
		Object instance = this.getViewMap().get(name);
		if (instance == null) {
			instance = objectFactory.getObject();
			this.getViewMap().put(name, instance);
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object remove(String name) {
		Object instance = this.getViewMap().remove(name);
		if (instance == null) {
			Map<String, Runnable> callbacks = (Map<String, Runnable>) this.getViewMap().get(VIEW_SCOPE_CALLBACKS);
			if (callbacks != null)
				callbacks.remove(name);
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void registerDestructionCallback(String name, Runnable runnable) {
		Map<String, Runnable> callbacks = (Map<String, Runnable>) this.getViewMap().get(VIEW_SCOPE_CALLBACKS);
		if (callbacks != null)
			callbacks.put(name, runnable);
	}

	@Override
	public Object resolveContextualObject(String key) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesRequestAttributes facesResquestAttributes = new FacesRequestAttributes(facesContext);
		return facesResquestAttributes.resolveReference(key);
	}

	@Override
	public String getConversationId() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesRequestAttributes facesResquestAttributes = new FacesRequestAttributes(facesContext);
		return facesResquestAttributes.getSessionId() + "-" + facesContext.getViewRoot().getViewId();
	}

	private Map<String, Object> getViewMap() {
		return FacesContext.getCurrentInstance().getViewRoot().getViewMap();
	}

}
