/**
 * 
 */
package application;

import java.io.IOException;
import java.util.Arrays;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import application.ClassListHandler;
import application.ClassRoomGenerator;

/**
 * @author mails
 *
 */
public class ClassRoomGenerator extends Application {

	public ClassRoomGenerator(int seatsPerRow, int rows, boolean showGUI) {

		this.rows = rows;
		this.seatsPerRow = seatsPerRow;
		this.table = new String[rows][seatsPerRow];
		this.showGUI = showGUI;
		this.btn = new Button[rows][seatsPerRow];

		CreateSeatTable();

	}

	ClassListHandler classLists;
	int rows;
	int seatsPerRow;
	boolean showGUI;

	private String[][] table;
	private Button[][] btn;

	Stage stage = new Stage();

	public void CreateSeatTable() {

		Pane mainPane = new Pane();
		mainPane.setPadding(new Insets(20));

		VBox vbox = new VBox();
		GridPane gridPane = new GridPane();

		HBox top_box = new HBox(10);
		Label tafel = new Label(
				"\n" + "                                  Tafel                                  \n" + "\n \n");
		tafel.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold");
		top_box.setAlignment(Pos.CENTER);
		top_box.getChildren().add(tafel);

		vbox.getChildren().add(top_box);

		int i = 0;
		int j = 0;
		int x = 0;

		for (j = 0; j < rows; j++) {
			x++;
			for (i = 0; i < seatsPerRow; i++) {

				btn[j][i] = new Button(String.valueOf(i + 1) + "." + String.valueOf(j + 1));
				table[j][i] = "";
				btn[j][i].setPrefSize(150, 50);
				btn[j][i].setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						System.out.println("button pressed");
						try {
							callback_toggleTableState(event);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
				gridPane.add(btn[j][i], x, i);

			}
			if (((j % 2) != 0) && (j < rows - 1)) {
				x++;
				gridPane.add(new Label("               "), x, i);
			}

		}
		gridPane.setHgap(3);
		gridPane.setVgap(40);

		if (showGUI) {

			gridPane.setPadding(new Insets(10, 10, 10, 10));

			vbox.getChildren().add(gridPane);
			HBox hbox = new HBox();

			Button btn_ok = new Button("OK");

			hbox.getChildren().add(btn_ok);
			btn_ok.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					// TODO Auto-generated method stub
					stage.close();
				}

			});

			// hbox.setSpacing(30.0); //In your case

			vbox.getChildren().add(hbox);

			mainPane.getChildren().add(vbox);

			// gridPane.getChildren().add(hbox);

			Scene scene = new Scene(mainPane);

			stage.setScene(scene);

			stage.show();
		}
	}

	public void callback_toggleTableState(ActionEvent event) throws IOException {
		System.out.println("button pressed");

		String chair_as_String = event.getSource().toString();
		// chair_as_String = chair_as_String.substring(chair_as_String.indexOf("'"),
		// chair_as_String.lastIndexOf("'"));

		System.out.println("source: " + chair_as_String);

		int chair = (Integer.parseInt(
				chair_as_String.substring(chair_as_String.indexOf('\'') + 1, chair_as_String.indexOf('.')).trim())) - 1;
		int row = (Integer.parseInt(
				chair_as_String.substring(chair_as_String.indexOf('.') + 1, chair_as_String.length() - 1).trim())) - 1;
		System.out.println("row: " + row);
		System.out.println("chair: " + chair);

		if (table[row][chair].equals("-")) {
			table[row][chair] = "";
			btn[row][chair].setDisable(false);
		} else {
			table[row][chair] = "-";
			btn[row][chair].setDisable(true);
			System.out.println("disabled");
		}
	}

	public String[][] getSeatingTable() {

		return Arrays.stream(table).map(r -> r.clone()).toArray(String[][]::new);

	}

	public Button[][] getButtonTable() {
		// return Arrays.stream(btn).map(r -> r.clone()).toArray(Button[][]::new);
		return copyButtonArr(Arrays.stream(btn).map(r -> r.clone()).toArray(Button[][]::new));

	}

	Button[][] copyButtonArr(Button arr[][]) {

		Button[][] ret = new Button[arr.length][arr[0].length];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				ret[i][j] = arr[i][j];
			}
		}
		return ret;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}
}
