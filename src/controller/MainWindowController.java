package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.ClassListHandler;
import application.ClassRoomGenerator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

public class MainWindowController implements Initializable {
	final FileChooser fileChooser = new FileChooser();
	ClassListHandler classListHandler = new ClassListHandler();

	@FXML
	private Button btn_editClassList, btn_generateSeatingChart, btn_loadClassList, btn_editGroundPlan;
	@FXML
	private Label label_ClassList, label_numOfPersons;

	@FXML
	private Spinner<Integer> sel_numOfRows, sel_desksPerRow;

	final int initialValue = 3;

	String[][] tableArray = null;
	Button[][] btnArray = null;

	ClassRoomGenerator tableGen = null;

	// Value factory.
	SpinnerValueFactory<Integer> valueFactory_numOfRows = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9,
			initialValue);
	SpinnerValueFactory<Integer> valueFactory_desksPerRow = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9,
			initialValue);

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
			setLabel_ClassList(ClassListHandler.getClassListFilename());
			int numOfStudents = ClassListHandler.loadClassList();
			setLabel_numOfPersons(numOfStudents + " Personen geladen");
			if (numOfStudents > 0) {
				btn_generateSeatingChart.setDisable(false);
			} else {
				btn_generateSeatingChart.setDisable(true);

			}
		} catch (Exception e) {
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
		SeatingTableGenerator tableGUI = new SeatingTableGenerator(tableArray, classListHandler.copyClassList(classListHandler));
	
		tableGUI.CreateSeatTable();

	}

	public void callback_editClassList() throws IOException {
		System.out.println("Button pressed: callback_editClassList");

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