package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.ClassListHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

public class MainWindowController implements Initializable {
	final FileChooser fileChooser = new FileChooser();
	ClassListHandler classListHandler = new ClassListHandler();

	@FXML
	private Button btn_editClassList, btn_generateSeatingChart, btn_loadClassList, btn_editGroundPlan,
			btn_createNewClassList;
	@FXML
	private Label label_ClassList, label_numOfPersons;

	@FXML
	private Spinner<Integer> sel_numOfRows, sel_desksPerRow;
	@FXML
	private MenuItem menu_loadClassList, menu_createNewClassList, menu_editClassList, menu_About;

	final int initialValueRows = 5;
	final int initialValueTableaPerRow = 3;

	String[][] tableArray = null;
	Button[][] btnArray = null;

	ClassRoomGenerator tableGen = null;
	boolean numOfSeatsChanged = false;

	// Value factory.
	SpinnerValueFactory<Integer> valueFactory_numOfRows = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 15,
			initialValueRows);
	SpinnerValueFactory<Integer> valueFactory_desksPerRow = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 15,
			initialValueTableaPerRow);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sel_numOfRows.setValueFactory(valueFactory_numOfRows);
		sel_desksPerRow.setValueFactory(valueFactory_desksPerRow);

		sel_desksPerRow.getEditor().setOnMouseClicked(event -> {
			numOfSeatsChanged = true;
			System.out.println("Button pressed: callback_loadJsonClassList");

		});
		sel_numOfRows.getEditor().setOnMouseClicked(event -> {
			numOfSeatsChanged = true;
			System.out.println("Button pressed: callback_loadJsonClassList");

		});

		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON files", "*.json"),
				new ExtensionFilter("Excel files", "*.xls"));

	}

	public void callback_loadJsonClassList() throws IOException {
		System.out.println("Button pressed: callback_loadJsonClassList");

		Window dummyWindow = null;
		// Set extension filter
		try {

			// Show open file dialog
			File file = fileChooser.showOpenDialog(dummyWindow);

			classListHandler.setClassListFilename(file.getPath());
			loadClasslistFromJson(ClassListHandler.getClassListFilename());
		} catch (Exception e) {
			btn_generateSeatingChart.setDisable(true);

		}

	}

	private void loadClasslistFromJson(String file) {
		setLabel_ClassList(file);
		int numOfStudents = classListHandler.loadClassList(file);
		setLabel_numOfPersons(numOfStudents + " Personen geladen");
		if (numOfStudents > 0) {
			btn_generateSeatingChart.setDisable(false);
		} else {
			btn_generateSeatingChart.setDisable(true);

		}
	}

	public void callback_editGroundPlan() throws IOException {
		System.out.println("Button pressed: callback_editGroundPlan");
		tableGen = new ClassRoomGenerator(sel_numOfRows.getValue(), (2 * sel_desksPerRow.getValue()), true);
		tableArray = tableGen.getSeatingTable();

	}

	public void callback_createSeatingChart() throws IOException {
		System.out.println("Button pressed: callback_createSeatingChart");
		if (tableGen == null || numOfSeatsChanged) {
			tableGen = new ClassRoomGenerator(sel_numOfRows.getValue(), (2 * sel_desksPerRow.getValue()), false);
			System.out.println("get new table gen");
			numOfSeatsChanged = false;
		}

		tableArray = tableGen.getSeatingTable();
		btnArray = tableGen.getButtonTable().clone();
		SeatingTableGenerator tableGUI = new SeatingTableGenerator(tableArray, classListHandler.getClassLists());
		tableGUI.CreateSeatTable();
	}

	public void callback_editClassList() throws IOException {
		System.out.println("Button pressed: callback_editClassList");
		label_ClassList.setWrapText(true);
	}

	public void callback_AboutWindow() throws IOException {
		System.out.println("Button pressed: callback_editClassList");
		AboutWindow aboutWindow = new AboutWindow();
		aboutWindow.showWindow();
	}

	public void callback_createNewClassList() throws IOException {
		System.out.println("Button pressed: callback_createNewClassList");

		try {

			NewClassListController ctrl = new NewClassListController();
			loadClasslistFromJson(ctrl.getClassListFilename());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Hide this current window (if this is what you want)
		// ((Node)(event.getSource())).getScene().getWindow().hide();

	}

	public String getLabel_ClassList() {
		return label_ClassList.toString();
	}

	public void setLabel_ClassList(String value) {
		label_ClassList.setText(value);
	}

	public String getLabel_numOfPersons() {
		return label_numOfPersons.toString();
	}

	public void setLabel_numOfPersons(String value) {
		label_numOfPersons.setText(value);
	}
}