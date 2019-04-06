
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class NewClassListGenerator extends Pane {

    private final Button btn_cancel;
    private final Button btn_saveAs;
    private final TextArea text_newClassList;
    private final Label label_newClassListHeader;
    private final Label label_newClassListDescription;

    public NewClassListGenerator() {

        btn_cancel = new Button();
        btn_saveAs = new Button();
        text_newClassList = new TextArea();
        label_newClassListHeader = new Label();
        label_newClassListDescription = new Label();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(531.0);
        setPrefWidth(381.0);

        btn_cancel.setLayoutX(47.0);
        btn_cancel.setLayoutY(474.0);
        btn_cancel.setMnemonicParsing(false);
        btn_cancel.setPrefHeight(32.0);
        btn_cancel.setPrefWidth(119.0);
        btn_cancel.setText("Cancel");

        btn_saveAs.setLayoutX(215.0);
        btn_saveAs.setLayoutY(474.0);
        btn_saveAs.setMnemonicParsing(false);
        btn_saveAs.setPrefHeight(32.0);
        btn_saveAs.setPrefWidth(119.0);
        btn_saveAs.setText("Save as...");

        text_newClassList.setLayoutX(37.0);
        text_newClassList.setLayoutY(108.0);
        text_newClassList.setPrefHeight(351.0);
        text_newClassList.setPrefWidth(307.0);

        label_newClassListHeader.setLayoutX(143.0);
        label_newClassListHeader.setLayoutY(14.0);
        label_newClassListHeader.setPrefHeight(32.0);
        label_newClassListHeader.setPrefWidth(95.0);
        label_newClassListHeader.setText("New Class List");
        label_newClassListHeader.setFont(new Font("System Bold", 14.0));

        label_newClassListDescription.setAlignment(javafx.geometry.Pos.CENTER);
        label_newClassListDescription.setLayoutX(41.0);
        label_newClassListDescription.setLayoutY(46.0);
        label_newClassListDescription.setPrefHeight(60.0);
        label_newClassListDescription.setPrefWidth(299.0);
        label_newClassListDescription.setText("Paste (Crtl+V) the names of all your students into the text area below. One person per line.");
        label_newClassListDescription.setWrapText(true);

        getChildren().add(btn_cancel);
        getChildren().add(btn_saveAs);
        getChildren().add(text_newClassList);
        getChildren().add(label_newClassListHeader);
        getChildren().add(label_newClassListDescription);

    }
}
