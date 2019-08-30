package controller;

import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

public class AboutWindow {

	private static final String titleTxt = "Sitzplan Generator - About";

	Stage primaryStage = new Stage();

	public void showWindow() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titleTxt);
		alert.setHeaderText("About Sitzplan Generator");

		FlowPane fp = new FlowPane();

		String s = "Dokumentation gibt es hier: ";
		Hyperlink link = new Hyperlink("https://stockinger.bitbucket.io");

		String contact = "Bei Fragen, wünschen und Beschwerden: ";
		Hyperlink mail = new Hyperlink("sitzplan.gen@gmail.com");
		Label contactLabel = new Label(contact);
		Label docuLabel = new Label(s);

		fp.getChildren().add(docuLabel);
		fp.getChildren().add(link);
		fp.getChildren().add(contactLabel);
		fp.getChildren().add(mail);

		alert.getDialogPane().contentProperty().set(fp);
		alert.showAndWait();
	}
}
