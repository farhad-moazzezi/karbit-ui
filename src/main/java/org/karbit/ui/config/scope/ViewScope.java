package org.karbit.ui.config.scope;

import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

class ViewScope implements Scope {

	@NonNull
	@Override
	public Object get(@NonNull String name, @NonNull ObjectFactory<?> objectFactory) {
		Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
		if (viewMap.containsKey(name)) {
			return viewMap.get(name);
		} else {
			Object object = objectFactory.getObject();
			viewMap.put(name, object);

			return object;
		}
	}

	@Override
	public String getConversationId() {
		return null;
	}

	@Override
	public void registerDestructionCallback(@Nullable String arg0, @Nullable Runnable runnable) {

	}

	@Override
	public Object remove(@NonNull String name) {
		return FacesContext.getCurrentInstance().getViewRoot().getViewMap().remove(name);
	}

	@Override
	public Object resolveContextualObject(@Nullable String arg0) {
		return null;
	}

}
