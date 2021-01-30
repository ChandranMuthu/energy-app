package com.example.crudwithvaadin;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.*;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

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

		MenuBar menuBar = new MenuBar();
		Text selected = new Text("");
		Div message = new Div(new Text("Selected: "), selected);

		MenuItem project = menuBar.addItem("Home");
		MenuItem account = menuBar.addItem("Account");
		menuBar.addItem("Sign Out", e -> selected.setText("Sign Out"));

		SubMenu projectSubMenu = project.getSubMenu();
		MenuItem users = projectSubMenu.addItem("Users");
		MenuItem billing = projectSubMenu.addItem("Billing");

		SubMenu usersSubMenu = users.getSubMenu();
		usersSubMenu.addItem("List", e -> selected.setText("List"));
		usersSubMenu.addItem("Add", e -> selected.setText("Add"));

		SubMenu billingSubMenu = billing.getSubMenu();
		billingSubMenu.addItem("Invoices", e -> selected.setText("Invoices"));
		billingSubMenu.addItem("Balance Events",
				e -> selected.setText("Balance Events"));

		account.getSubMenu().addItem("Edit Profile",
				e -> selected.setText("Edit Profile"));
		account.getSubMenu().addItem("Privacy Settings",
				e -> selected.setText("Privacy Settings"));
		add(menuBar, message);


		drawGraph(repo.findAll());


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

	private void drawGraph(final List<EnergyOutput> energyOutputList) {
		Chart chart = new Chart();

		Configuration configuration = chart.getConfiguration();
		configuration.setTitle("Monthly Average Reading");
		configuration.setSubTitle("Source: Some Device");
		chart.getConfiguration().getChart().setType(ChartType.COLUMN);

		//List<Map<String, Integer>> graphData = new ArrayList<>();

		final Map<String, List<EnergyOutput>> collect = energyOutputList.stream().collect(groupingBy(EnergyOutput::getDeviceId));
		final Set<LocalDateTime> localDateTimes = energyOutputList.stream().filter(distinctByKey(p -> p.getDeviceDateTime())).map(EnergyOutput::getDeviceDateTime).collect(Collectors.toSet());

		final String[] xAxisData = localDateTimes.stream().map(e-> e.toLocalDate().toString()).toArray(n -> new String[n]);




		collect.forEach((k, v) -> {
			configuration.addSeries(new ListSeries(k, v.stream().map(energyOutput -> energyOutput.getCalculatedValue().intValue()).collect(Collectors.toSet())));
		});


//		energyOutputList.forEach(energyOutput -> {
//			configuration.addSeries(new ListSeries(energyOutput.getDeviceId(), energyOutput.getCalculatedValue()));
//		});

//		configuration.addSeries(new ListSeries("Device 1", 49.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4));
//		configuration.addSeries(new ListSeries("Device 2", 83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3));
//		configuration.addSeries(new ListSeries("Device 3", 48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2));
//		configuration.addSeries(new ListSeries("Device 4", 42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1));

		XAxis x = new XAxis();
		x.setCrosshair(new Crosshair());
		//x.setCategories("Jan","Feb","March", "April", "May", "June", "July","Aug", "Sep", "oct", "Nov", "Dec");
		x.setCategories(xAxisData);
		configuration.addxAxis(x);

		YAxis y = new YAxis();
		y.setMin(0);
		y.setTitle("Voltage used (volt)");
		configuration.addyAxis(y);

		Tooltip tooltip = new Tooltip();
		tooltip.setShared(true);
		configuration.setTooltip(tooltip);

		add(chart);
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

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
	{
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

}
