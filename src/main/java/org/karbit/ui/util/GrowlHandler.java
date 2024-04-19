package org.karbit.ui.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class GrowlHandler {

	private static void addMessage(FacesMessage.Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().
				addMessage(null, new FacesMessage(severity, summary, detail));
	}

	public static void showMessage(String title, String detail, FacesMessage.Severity severity) {
		addMessage(severity, title, detail);
	}
}
