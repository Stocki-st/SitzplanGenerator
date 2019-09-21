/**
 * 
 */
package controller;

/**
 * @author mails
 *
 */
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Arrays;
import java.util.Vector;

import application.Student;
import controller.TableViewHelper;

public class EditClassListController {

	// Define the Text Fields
	private final TextField nameField = new TextField();
	private final CheckBox sitAloneBox = new CheckBox();
	private final CheckBox firstRowBox = new CheckBox();
	private final TextField fixedChairField = new TextField();
	private final TextField forbiddenNeighb = new TextField();

	// Create the TableView
	TableView<Student> table = new TableView<>(TableViewHelper.getPersonList());

	@SuppressWarnings("unchecked")

	public EditClassListController() {
		Stage stage = new Stage();
		// Turn on multi-row selection for the TableView
		TableViewSelectionModel<Student> tsm = table.getSelectionModel();
		tsm.setSelectionMode(SelectionMode.MULTIPLE);

		table.setEditable(true);
		// Add columns to the TableView

		table.getColumns().addAll(confNameColumn(table), confSitAloneColumn(table), confFirstRowColumn(table),
				confFixChairColumn(table), TableViewHelper.getForbiddenNeighColumn());

		// Set the column resize policy to constrained resize policy
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		// Set the Placeholder for an empty table
		table.setPlaceholder(new Label("No visible columns and/or data exist."));

		// Create the GridPane
		GridPane newDataPane = this.getNewPersonDataPane();

		// Create the Delete Button and add Event-Handler
		Button deleteButton = new Button("Delete Selected Rows");
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				deletePerson();
			}
		});

		// Create the VBox
		VBox root = new VBox();
		// Add the GridPane and the Delete Button to the VBox
		root.getChildren().addAll(newDataPane, deleteButton, table);

		// Set the Padding and Border for the VBox
		root.setSpacing(5);
		// Set the Spacing and Border for the VBox
		root.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");

		// Create the Scene
		Scene scene = new Scene(root);
		// Add the Scene to the Stage
		stage.setScene(scene);
		// Set the Title of the Stage
		stage.setTitle("Adding/Deleting Rows in a TableViews");
		// Display the Stage
		stage.show();
	}

	public GridPane getNewPersonDataPane() {
		// Create the GridPane
		GridPane pane = new GridPane();

		// Set the hgap and vgap properties
		pane.setHgap(10);
		pane.setVgap(5);

		// Add the TextFields to the Pane
		pane.addRow(0, new Label("Name:"), nameField);
		pane.addRow(1, new Label("muss alleine sitzen:"), sitAloneBox);
		pane.addRow(2, new Label("sitzt in der 1. Reihe:"), firstRowBox);
		pane.addRow(3, new Label("soll auf folgendem Platz sitzen:"), fixedChairField);
		pane.addRow(4, new Label("darf nicht neben ... sitzen:"), forbiddenNeighb);

		// Create the Add Button and add Event-Handler
		Button addButton = new Button("Add");
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				addPerson();
			}
		});

		// Add the Add Button to the GridPane
		pane.add(addButton, 2, 0);

		return pane;
	}

	public void addPerson() {

		if (nameField.getText() == null || nameField.getText().isEmpty())
			return;

		// Create a new Person Object
		/// TODO: //forbiddenNeighb missings

		Student person = new Student(nameField.getText(), sitAloneBox.isSelected(), firstRowBox.isSelected(),
				fixedChairField.getText(), null);

		// Add the new Person to the table
		table.getItems().add(person);

		// Clear the Input Fields
		nameField.setText(null);
		sitAloneBox.setSelected(false);
		firstRowBox.setSelected(false);
		fixedChairField.setText(null);
		forbiddenNeighb.setText(null);
	}

	public void deletePerson() {
		TableViewSelectionModel<Student> tsm = table.getSelectionModel();

		// Check, if any rows are selected
		if (tsm.isEmpty()) {
			System.out.println("Please select a row to delete.");
			return;
		}

		// Get all selected row indices in an array
		ObservableList<Integer> list = tsm.getSelectedIndices();

		Integer[] selectedIndices = new Integer[list.size()];
		selectedIndices = list.toArray(selectedIndices);

		// Sort the array
		Arrays.sort(selectedIndices);

		// Delete rows (last to first)
		for (int i = selectedIndices.length - 1; i >= 0; i--) {
			tsm.clearSelection(selectedIndices[i].intValue());
			table.getItems().remove(selectedIndices[i].intValue());
		}
	}

	public static TableColumn<Student, String> confNameColumn(TableView<Student> table) {
		// Last Name is a String, editable column
		TableColumn<Student, String> lastNameCol = TableViewHelper.getNameColumn();
		// Use a TextFieldTableCell, so it can be edited
		lastNameCol.setCellFactory(TextFieldTableCell.<Student>forTableColumn());

		// Set editing related event handlers (OnEditCommit)
		lastNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Student, String>>() {
			@Override
			public void handle(CellEditEvent<Student, String> t) {
				((Student) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());
			}
		});
		return lastNameCol;
	}

	public static TableColumn<Student, Boolean> confSitAloneColumn(TableView<Student> table) {
		// Last Name is a String, editable column
		TableColumn<Student, Boolean> sitAloneCol = TableViewHelper.getSitAloneColumn();
		// Use a TextFieldTableCell, so it can be edited
		sitAloneCol.setCellFactory(CheckBoxTableCell.<Student>forTableColumn(sitAloneCol));

		// Set editing related event handlers (OnEditCommit)
		sitAloneCol.setOnEditCommit(new EventHandler<CellEditEvent<Student, Boolean>>() {
			@Override
			public void handle(CellEditEvent<Student, Boolean> t) {
				{
					((Student) t.getTableView().getItems().get(t.getTablePosition().getRow()))
							.setSitAlone(t.getNewValue());
				}
			}
		});
		return sitAloneCol;
	}

	public static TableColumn<Student, Boolean> confFirstRowColumn(TableView<Student> table) {
		// Last Name is a String, editable column
		TableColumn<Student, Boolean> firstRowCol = TableViewHelper.getFirstRowColumn();
		// Use a TextFieldTableCell, so it can be edited
		firstRowCol.setCellFactory(CheckBoxTableCell.<Student>forTableColumn(firstRowCol));

		// Set editing related event handlers (OnEditCommit)
		firstRowCol.setOnEditCommit(new EventHandler<CellEditEvent<Student, Boolean>>() {
			@Override
			public void handle(CellEditEvent<Student, Boolean> t) {
				{
					((Student) t.getTableView().getItems().get(t.getTablePosition().getRow()))
							.setFirstRow(t.getNewValue());
				}
			}
		});
		return firstRowCol;
	}

	public static TableColumn<Student, String> confFixChairColumn(TableView<Student> table) {
		// Last Name is a String, editable column
		TableColumn<Student, String> fixChairCol = TableViewHelper.getFixSeatColumn();
		// Use a TextFieldTableCell, so it can be edited
		fixChairCol.setCellFactory(TextFieldTableCell.<Student>forTableColumn());

		// Set editing related event handlers (OnEditCommit)
		fixChairCol.setOnEditCommit(new EventHandler<CellEditEvent<Student, String>>() {
			@Override
			public void handle(CellEditEvent<Student, String> t) {
				((Student) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setFixedChar(t.getNewValue());
			}
		});
		return fixChairCol;
	}

}
