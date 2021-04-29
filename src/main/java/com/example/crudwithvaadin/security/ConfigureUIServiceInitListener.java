package com.example.crudwithvaadin.security;

import com.example.crudwithvaadin.view.DashboardView;
import com.example.crudwithvaadin.view.LoginView;
import com.example.crudwithvaadin.view.SignupView;
import com.example.crudwithvaadin.view.UsersView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.stereotype.Component;

@Component 
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

	@Override
	public void serviceInit(ServiceInitEvent event) {
		event.getSource().addUIInitListener(uiEvent -> { 
			final UI ui = uiEvent.getUI();
			ui.addBeforeEnterListener(this::authenticateNavigation);
		});
	}

	private void authenticateNavigation(BeforeEnterEvent event) {
		if (!LoginView.class.equals(event.getNavigationTarget())
		    && !SecurityUtils.isUserLoggedIn()) { 
			event.rerouteTo(LoginView.class);
		}
		if ((UsersView.class.equals(event.getNavigationTarget())
				&& SecurityUtils.isUserLoggedIn()) && !SecurityUtils.isUserHasAdminRole()) {
			event.rerouteTo(DashboardView.class);
		}
		if ((SignupView.class.equals(event.getNavigationTarget())
				&& SecurityUtils.isUserLoggedIn()) && !SecurityUtils.isUserHasAdminRole()) {
			event.rerouteTo(DashboardView.class);
		}
	}
}