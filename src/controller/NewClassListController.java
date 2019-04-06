package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;

import org.json.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class NewClassListController extends Application {

	private final Button btn_cancel;
	private final Button btn_saveAs;
	private final TextArea text_newClassList;
	private final Label label_newClassListHeader;
	private final Label label_newClassListDescription;
	private final Stage stage;

	static String classListFilename;

	public NewClassListController() {

		Pane mainPane = new Pane();

		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(10));
		stage = new Stage();

		btn_cancel = new Button();
		btn_saveAs = new Button();
		text_newClassList = new TextArea();
		label_newClassListHeader = new Label();
		label_newClassListDescription = new Label();

		btn_cancel.setPrefHeight(32.0);
		btn_cancel.setPrefWidth(119.0);
		btn_cancel.setText("Cancel");
		btn_cancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				stage.close();
			}
		});

		btn_saveAs.setMnemonicParsing(false);
		btn_saveAs.setPrefHeight(32.0);
		btn_saveAs.setPrefWidth(119.0);
		btn_saveAs.setText("Save as...");

		btn_saveAs.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("print button pressed");

				SaveClasslistAsJson();
			}

		});

		text_newClassList.setPrefHeight(350);
		text_newClassList.setPrefWidth(280);

		label_newClassListHeader.setText("New Class List");
		label_newClassListHeader.setFont(new Font("System Bold", 14.0));

		label_newClassListDescription.setAlignment(javafx.geometry.Pos.CENTER);

		label_newClassListDescription.setText(
				"Paste (Crtl+V) the names of all your students into the text area below.\nOne person per line.");
		label_newClassListDescription.setWrapText(true);

		vbox.getChildren().add(label_newClassListHeader);
		vbox.getChildren().add(label_newClassListDescription);
		vbox.getChildren().add(text_newClassList);

		HBox hbox = new HBox();
		hbox.setSpacing(10);
		hbox.setPadding(new Insets(10));
		hbox.setAlignment(Pos.CENTER);
		vbox.setAlignment(Pos.CENTER);

		hbox.getChildren().add(btn_cancel);
		hbox.getChildren().add(btn_saveAs);

		vbox.getChildren().add(hbox);
		stage.setResizable(false);

		mainPane.getChildren().add(vbox);
		Scene scene = new Scene(mainPane);

		stage.setScene(scene);
		stage.showAndWait();

	}

	public void SaveClasslistAsJson() {
		try {
			FileChooser fileChooser = new FileChooser();

			// Set extension filter
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("classlist files (*.json)",
					"*.json");
			fileChooser.getExtensionFilters().add(extFilter);
			// Show save file dialog
			final Stage primaryStage = new Stage();
			File file = fileChooser.showSaveDialog(primaryStage);
			JSONObject entry = new JSONObject();
			// read name line by line
			// throw empty lines
			JSONObject classlist = new JSONObject();

			int id = 0;
			for (String line : text_newClassList.getText().split("\\n")) {
				if (line.length() > 3) {

					JSONObject name = new JSONObject();
					System.out.println("name: " + line + "id: " + id);
					name.put("name", line);
					entry.put(String.valueOf(id), name);
					id++;
				}
			}

			BufferedWriter filewriter = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));

			filewriter.write(entry.toString(4));
			filewriter.flush();
			stage.close();
			classListFilename = file.getPath();

		} catch (Exception e) {

		}
	}

	public static String getClassListFilename() {
		return classListFilename;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}
}
