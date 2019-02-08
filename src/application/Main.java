package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane mainPane = (AnchorPane) FXMLLoader.load(Main.class.getResource("MainWindow.fxml"));
			primaryStage.setTitle("Sitzplan Generator");
			primaryStage.getIcons().add(new Image("file:src/application/ship-icon.png"));

			primaryStage.setScene(new Scene(mainPane));
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
