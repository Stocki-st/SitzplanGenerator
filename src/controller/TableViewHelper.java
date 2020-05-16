package controller;

import java.util.Vector;

import application.ClassListHandler;
import application.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;

public class TableViewHelper {

	static Vector<Student> students;

	// Returns an observable list of persons
	public static ObservableList<Student> getPersonList() {
		students = ClassListHandler.getStudentArray();
		return FXCollections.<Student>observableArrayList(students);
	}

	// Returns First Name TableColumn
	public static TableColumn<Student, String> getNameColumn() {
		TableColumn<Student, String> NameCol = new TableColumn<>("Name");
		PropertyValueFactory<Student, String> NameCellValueFactory = new PropertyValueFactory<>("Name");
		NameCol.setCellValueFactory(NameCellValueFactory);
		return NameCol;
	}

	public static TableColumn<Student, Boolean> getSitAloneColumn() {
		TableColumn<Student, Boolean> sitAloneCol = new TableColumn<>("sitzt alleine");
		PropertyValueFactory<Student, Boolean> sitAloneCellValueFactory = new PropertyValueFactory<>("Sitzt alleine");
		sitAloneCol.setCellValueFactory(sitAloneCellValueFactory);
		return sitAloneCol;
	}

	// Returns ZipCode TableColumn
	public static TableColumn<Student, Boolean> getFirstRowColumn() {
		TableColumn<Student, Boolean> firstRowCol = new TableColumn<>("erste Reihe");
		PropertyValueFactory<Student, Boolean> firstRowCellValueFactory = new PropertyValueFactory<>("erste Reihe");
		firstRowCol.setCellValueFactory(firstRowCellValueFactory);
		return firstRowCol;
	}

	// Returns City TableColumn
	public static TableColumn<Student, String> getFixSeatColumn() {
		TableColumn<Student, String> fixSeat = new TableColumn<>("fixer Platz");
		PropertyValueFactory<Student, String> fixSeatValueFactory = new PropertyValueFactory<>("fixer Platz");
		fixSeat.setCellValueFactory(fixSeatValueFactory);
		return fixSeat;
	}

	// Returns Country TableColumn
	public static TableColumn<Student, String> getForbiddenNeighColumn() {
		TableColumn<Student, String> forbiddenNeighboursCol = new TableColumn<>("verbotene Nachbarn");
		PropertyValueFactory<Student, String> forbiddenNeighboursValueFactory = new PropertyValueFactory<>(
				"verbotene Nachbarn");
		forbiddenNeighboursCol.setCellValueFactory(forbiddenNeighboursValueFactory);
		return forbiddenNeighboursCol;
	}

}
