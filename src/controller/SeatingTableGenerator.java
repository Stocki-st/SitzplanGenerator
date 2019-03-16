/**
 * 
 */
package controller;

import java.awt.List;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.text.AttributeSet.CharacterAttribute;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.bouncycastle.asn1.eac.UnsignedInteger;

import application.ClassListHandler;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author mails
 *
 */
public class SeatingTableGenerator extends Application {

	public SeatingTableGenerator(String[][] seatingTable, ClassListHandler classList) {
		this.seatsPerRow = seatingTable.length;
		this.rows = seatingTable[0].length;
		this.seatingTable = seatingTable.clone();

		this.btn = new Button[seatsPerRow][rows];

		this.classList = new ClassListHandler(classList);
		
		System.out.println("in ctor");

	}

	String[][] seatingTable;
	int rows;
	int seatsPerRow;
	Button[][] btn;
	boolean showGUI;
	ClassListHandler classList;

	Stage stage = new Stage();

	public void CreateSeatTable() {

		Pane mainPane = new Pane();
		mainPane.setPadding(new Insets(20));

		VBox vbox = new VBox();
		GridPane gridPane = new GridPane();
		drawClassrom(gridPane);
		try {
			SeatTheBeasts();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText(e.getMessage());
			e.printStackTrace();
			alert.showAndWait();
		}

		// draw classroom with names

		System.out.println("rows " + rows);
		System.out.println("seatsPerRow " + seatsPerRow);

		for (int j = 0; j < rows; j++) {
			for (int i = 0; i < seatsPerRow; i++) {

				// btn[i][j].setText(j + "-" + i);

			}
		}
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		gridPane.setPadding(new Insets(10, 10, 10, 10));

		HBox top_box = new HBox();
		Label tafel = new Label(
				"\n" + "                                  Tafel                                  \n" + "\n \n");
		tafel.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold");
		top_box.setAlignment(Pos.CENTER);
		top_box.getChildren().add(tafel);

		vbox.getChildren().add(top_box);

		vbox.getChildren().add(gridPane);
		HBox hbox = new HBox(25);

		Button btn_ok = new Button("OK");
		btn_ok.setPrefSize(90, 40);
		Button btn_print = new Button("Drucken");
		btn_print.setPrefSize(90, 40);

		Button btn_swap = new Button("vertauschen");
		btn_swap.setPrefSize(90, 40);

		btn_print.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("print button pressed");
				SaveSeatingTableToPdf(mainPane);

			}

		});

		hbox.getChildren().add(btn_ok);
		hbox.getChildren().add(btn_print);
		hbox.getChildren().add(btn_swap);
		hbox.setAlignment(Pos.CENTER);
		btn_ok.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				stage.close();

			}

		});
		vbox.getChildren().add(new Label(" "));
		vbox.getChildren().add(hbox);

		mainPane.getChildren().add(vbox);

		// gridPane.getChildren().add(hbox);
		Scene scene = new Scene(mainPane);

		stage.setScene(scene);

		stage.show();

	}

	public void SeatTheBeasts() throws Exception {

		// disable unavailable tables
		for (int i = 0; i < seatingTable.length; i++) {
			for (int j = 0; j < seatingTable[0].length; j++) {

				System.out.println(i + "." + j + ": " + seatingTable[i][j]);

				if ("-".equals(seatingTable[i][j])) {

					System.out.println("equals");

					btn[i][j].setText(" ");
					btn[i][j].setDisable(true);
					seatingTable[i][j] = "-";

				}
			}
		}

		int empty_chairs = 0;

		// sit fixed chairs
		int row = 0;
		int chair = 0;
		for (Entry<String, String> mapEntry : ClassListHandler.fixedChairMap.entrySet()) {
			String chair_as_String = mapEntry.getValue();
			String name = mapEntry.getKey();
			System.out.println(name + ": " + chair_as_String);

			row = Integer.parseInt(chair_as_String.substring(0, chair_as_String.indexOf('.')).trim());
			chair = Integer.parseInt(
					chair_as_String.substring(chair_as_String.indexOf('.') + 1, chair_as_String.length()).trim());
			System.out.println("row: " + row);
			System.out.println("chair: " + chair);

			if (!isNullOrEmpty(seatingTable[chair - 1][row - 1])) {
				throw new Exception("Du möchtest " + name
						+ " auf einen bereits vergebenen oder nicht vorhandenen Platz setzen! -> bitte Sitzplatz Konfiguration bearbeiten!");
			}

			btn[chair - 1][row - 1].setText(name);
			seatingTable[chair - 1][row - 1] = name;
			checkSitAloneFlag(row, chair, name);
			ClassListHandler.removeNameFromLists(name);
		}

		// sit first row
		empty_chairs = 0;

		for (int i = 0; i < seatingTable.length; i++) {
			if (isNullOrEmpty(seatingTable[i][0]))
				empty_chairs++;
		}
		for (String name : ClassListHandler.firstRowList) {
			System.out.println("first row: " + name);

			Random rn = new Random();
			int desiredChair = 0;
			if (--empty_chairs <= 0)
				throw new Exception(
						"Du möchtest mehr Personen in die erste Reihe setzen, als es Plätze gibt! -> bitte Sitzplatz Konfiguration bearbeiten!");
			do {
				desiredChair = rn.nextInt(seatingTable.length);

				System.out.println("desiredChair: " + desiredChair);

			} while (!(isNullOrEmpty(seatingTable[desiredChair][0])));

			btn[desiredChair][0].setText(name);
			seatingTable[desiredChair][0] = name;
			checkSitAloneFlag(row, chair, name);
			ClassListHandler.removeNameFromLists(name);
		}

		// sit allone

		/*
		 * sit alone feature is part of RC2
		 */

		// sit all
		int studentIterator = 0;
		empty_chairs = 0;
		Random rn = new Random();

		for (int row_ = 0; row_ < seatingTable[0].length; row_++) {
			for (int j = 0; j < seatingTable.length; j++) {
				System.out.println("i: " + row_ + " j: " + j);

				if (classList.studentList.isEmpty())
					return;
				if (isNullOrEmpty(seatingTable[j][row_])) {
					int studentsLeft = classList.studentList.size();
					if (studentsLeft > 0) {
						studentIterator = rn.nextInt(studentsLeft);
					} else {
						studentIterator = 0;
					}

					if (classList.studentList.isEmpty()) {
						return;
					} else {
						String name = classList.studentList.get(studentIterator);

						btn[j][row_].setText(name);
						seatingTable[j][row_] = name;
						checkSitAloneFlag(j, row_, name);
						classList.studentList.remove(classList.studentList.get(studentIterator));
						if (studentsLeft == 0)
							return;
					}
				}
			}
		}

	}

	/**
	 * @param row
	 * @param chair
	 * @param name
	 */
	public void checkSitAloneFlag(int row, int chair, String name) {
		if (classList.sitAloneList.contains(name)) {
			if ((chair & 1) == 0) {
				btn[chair - 2][row - 1].setText("-");
				seatingTable[chair - 2][row - 1] = "-";
			} else {
				btn[chair][row - 1].setText("-");
				seatingTable[chair][row - 1] = "-";
			}
		}
	}

	public static boolean isNullOrEmpty(String str) {
		if (str != null && !str.isEmpty())
			return false;
		return true;
	}

	public void SaveSeatingTableToPdf(Pane bar) {

		WritableImage nodeshot = bar.snapshot(new SnapshotParameters(), null);
		File file = new File("chart.png");

		try {
			ImageIO.write(SwingFXUtils.fromFXImage(nodeshot, null), "png", file);
		} catch (IOException e) {

		}

		PDDocument doc = new PDDocument();
		PDPage page = new PDPage();
		PDImageXObject pdimage;
		PDPageContentStream content;
		try {
			pdimage = PDImageXObject.createFromFile("chart.png", doc);

			content = new PDPageContentStream(doc, page);
			content.drawImage(pdimage, 0, 0);
			content.close();
			doc.addPage(page);
			doc.save("pdf_file.pdf");
			doc.close();
			file.delete();
		} catch (IOException ex) {
		}
	}

	public void callback_toggleTableState(ActionEvent event) throws IOException {
		/*
		 * int i = 0; int j = 0;
		 * 
		 * System.out.println("button pressed");
		 * 
		 * if ("-".equals(btn[j][i].getText())) { btn[j][i].setText(j + "." + i);
		 * 
		 * } else { btn[j][i].setText("-"); }
		 */
	}

	public void callback_CloseSeatTable(ActionEvent event) throws IOException {
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}

	void drawClassrom(GridPane pane) {
		int i = 0;
		int j = 0;
		int x = 0;

		System.out.println("rows " + rows);
		System.out.println("seatsPerRow " + seatsPerRow);

		for (j = 0; j < seatsPerRow; j++) {
			x++;
			for (i = 0; i < rows; i++) {

				System.out.println("i " + i);
				System.out.println("j " + j);

				btn[j][i] = new Button(""); // String.valueOf(i + 1) + "." + String.valueOf(j + 1));
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
				pane.add(btn[j][i], x, i);

			}
			if (((j % 2) != 0) && (j < seatsPerRow - 1)) {
				x++;
				pane.add(new Label("               "), x, i);
			}

		}
	}
}
