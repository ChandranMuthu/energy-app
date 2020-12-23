package com.example.crudwithvaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.util.StringUtils;

@Route
public class MainView extends VerticalLayout {

	private final EnergyOutputRepository repo;

//	private final CustomerEditor editor;

	final Grid<EnergyOutput> grid;

	final TextField filter;

	//private final Button addNewBtn;

	public MainView(EnergyOutputRepository repo) {
		this.repo = repo;
//		this.editor = editor;
		this.grid = new Grid<>(EnergyOutput.class);
		this.filter = new TextField();
		//this.addNewBtn = new Button("New customer", VaadinIcon.PLUS.create());

		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter);
		add(actions, grid);

		grid.setHeight("300px");
		grid.setColumns("id", "transactionId", "deviceType","deviceDateTime","inputReading","transactionConfig", "dataLength","createdAt");
		grid.getColumnByKey("id").setWidth("150px").setFlexGrow(0);

		filter.setPlaceholder("Filter by transactionId");

		// Hook logic to components

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.EAGER);
		filter.addValueChangeListener(e -> listCustomers(e.getValue()));

		// Connect selected Customer to editor or hide if none is selected
//		grid.asSingleSelect().addValueChangeListener(e -> {
//			editor.editCustomer(e.getValue());
//		});

		// Instantiate and edit new Customer the new button is clicked
//		addNewBtn.addClickListener(e -> editor.editCustomer(new Customer("", "")));

		// Listen changes made by the editor, refresh data from backend
//		editor.setChangeHandler(() -> {
//			editor.setVisible(false);
//			listCustomers(filter.getValue());
//		});

		// Initialize listing
		listCustomers(null);
	}

	// tag::listCustomers[]
	void listCustomers(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAll());
		}
		else {
			grid.setItems(repo.findByTransactionId(filterText));
		}
	}
	// end::listCustomers[]

}
