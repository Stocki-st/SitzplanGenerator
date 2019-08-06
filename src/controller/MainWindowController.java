package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.ClassListHandler;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
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

	final int initialValueRows = 5;
	final int initialValueTableaPerRow = 3;

	String[][] tableArray = null;
	Button[][] btnArray = null;

	ClassRoomGenerator tableGen = null;

	// Value factory.
	SpinnerValueFactory<Integer> valueFactory_numOfRows = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9,
			initialValueRows);
	SpinnerValueFactory<Integer> valueFactory_desksPerRow = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9,
			initialValueTableaPerRow);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sel_numOfRows.setValueFactory(valueFactory_numOfRows);
		sel_desksPerRow.setValueFactory(valueFactory_desksPerRow);
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
			loadClasslistFromJson(classListHandler.getClassListFilename());
		} catch (Exception e) {
			btn_generateSeatingChart.setDisable(true);

		}

	}

	private void loadClasslistFromJson(String file) {
		setLabel_ClassList(file);
		int numOfStudents = ClassListHandler.loadClassList(file);
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
		if (tableGen == null) {
			tableGen = new ClassRoomGenerator(sel_numOfRows.getValue(), (2 * sel_desksPerRow.getValue()), false);
			System.out.println("get new table gen");

		}
		tableArray = tableGen.getSeatingTable();
		btnArray = tableGen.getButtonTable().clone();
		// SeatingTableGenerator tableGUI = new SeatingTableGenerator(tableArray,
		// classListHandler.copyClassList(classListHandler));
		SeatingTableGenerator tableGUI = new SeatingTableGenerator(tableArray, classListHandler.getClassLists());

		tableGUI.CreateSeatTable();

	}

	public void callback_editClassList() throws IOException {
		System.out.println("Button pressed: callback_editClassList");
		label_ClassList.setWrapText(true);
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