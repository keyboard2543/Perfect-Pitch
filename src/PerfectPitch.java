import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import jm.music.data.Note;
import jm.util.Play;
import musicalManager.PitchManagerEnum;
import musicalManager.Scale;
import musicalManager.ScaleStrategy;

/**
 * src.PerfectPitch GUI Application.
 * @author Sahatsawat Kanpai
 */
public class PerfectPitch extends Application {

    /** Scale for perfect pitch tests. */
    ScaleStrategy scale;
    /** Value of constant phi is 1.61803398874989484820... (aka Golden Ratio) */
    final static double phi = (1+Math.sqrt(5))/2;
    /** Initialize first note. */
    Note note;
    /** Main stage. */
    Stage window;
    /** Display width for this device. */
    final double DISPLAY_WIDTH = Screen.getPrimary().getBounds().getMaxX();
    /** Display height for this device. */
    final double DISPLAY_HEIGHT = Screen.getPrimary().getBounds().getMaxY();
    final double WIDTH_MAGNITUDE = DISPLAY_WIDTH/1536.0;
    final double HEIGHT_MAGNITUDE = DISPLAY_HEIGHT/864.0;
    /**
     * Run as main.
     * @param args is arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start method for this Application.
     * @param primaryStage is stage
     */
    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;

        window.setTitle("Perfect Pitch");
        window.getIcons().add(new Image("https://cdn.pixabay.com/photo/2016/03/26/01/17/treble-clef-1279909_960_720.png"));

        window.setScene(new Scene(mainMenu()));
        window.setResizable(false);
        window.show();
    }

    private MenuBar menuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");

        menuBar.getMenus().addAll(menu);
        return menuBar;
    }

    /**
     * Root of MainMenu.
     * @return GridPane root of MainMenu
     */
    private GridPane mainMenu() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(64.0*HEIGHT_MAGNITUDE));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(96.0*HEIGHT_MAGNITUDE);
        gridPane.setStyle("-fx-background-color: darkgray");

        Label labelPerfectPitch = new Label("Perfect\nPitch");
        labelPerfectPitch.setStyle("-fx-text-fill: steelblue; -fx-font-size: 72.0; -fx-font-family: Forte ");

        Button buttonTest = new Button("Test");
        Button buttonAbout = new Button("About");
        buttonTest.setStyle("-fx-font-size: 36.0; -fx-background-color: cadetblue");
        buttonAbout.setStyle("-fx-font-size: 36.0; -fx-background-color: cadetblue");
        buttonTest.setOnAction(actionEvent -> window.setScene(new Scene(scaleChoosing())));

        gridPane.add(labelPerfectPitch, 0, 0, 1, 2);
        gridPane.add(buttonTest, 1, 0);
        gridPane.add(buttonAbout, 1, 1);

        return gridPane;
    }

    private VBox scaleChoosing() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(32.0*HEIGHT_MAGNITUDE));

        Label topicLabel = new Label("Choose a scale");
        topicLabel.setFont(new Font(48.0));
        Label suggestionLabel = new Label("(Only A chromatic scale is a real perfect pitch)");
        suggestionLabel.setFont(new Font(36.0));

        HBox scaleChoosingHBox = new HBox();
        Label labelScale = new Label("Scale: ");
        labelScale.setFont(new Font(24.0));
        ComboBox<Scale> scaleComboBox = new ComboBox<>();
        scaleComboBox.setPrefSize(128.0*WIDTH_MAGNITUDE, 32.0*HEIGHT_MAGNITUDE);
        scaleComboBox.getItems().addAll(Scale.values());
        scaleComboBox.setValue(Scale.ChromaticScale);
        scaleChoosingHBox.getChildren().addAll(labelScale, scaleComboBox);

        Button buttonReady = new Button("Ready");
        buttonReady.setPrefSize(128.0*WIDTH_MAGNITUDE, 32.0*HEIGHT_MAGNITUDE);
        buttonReady.setOnAction(actionEvent -> {
            scale = scaleComboBox.getValue().getScaleStrategy();
            note = scale.getNewRandomNote();
            window.setScene(new Scene(earTest()));
        });

        vBox.getChildren().addAll(topicLabel, suggestionLabel, scaleChoosingHBox, buttonReady);

        return vBox;
    }

    /**
     * Root for chromatic scale test.
     * @return VBox root of chromatic scale test
     */
    private VBox earTest() {
        VBox vbox = new VBox();
        vbox.setFillWidth(true);
        vbox.setPrefSize(DISPLAY_WIDTH * 0.5/phi, DISPLAY_HEIGHT * 0.5);
        vbox.setSpacing(32.0);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(32.0*HEIGHT_MAGNITUDE));

        HBox hBoxMenu = new HBox();
        hBoxMenu.setAlignment(Pos.CENTER);
        HBox hBoxTotal = new HBox();
        hBoxTotal.setAlignment(Pos.CENTER);
        HBox hBoxStatus = new HBox();
        hBoxStatus.setAlignment(Pos.CENTER);
        FlowPane flowPanePitch = new FlowPane();
        flowPanePitch.setAlignment(Pos.CENTER);

        hBoxMenu.setSpacing(10.0*HEIGHT_MAGNITUDE);
        hBoxTotal.setSpacing(10.0*HEIGHT_MAGNITUDE);
        hBoxStatus.setSpacing(10.0*HEIGHT_MAGNITUDE);
        flowPanePitch.setHgap(10.0*HEIGHT_MAGNITUDE);
        flowPanePitch.setVgap(10.0*HEIGHT_MAGNITUDE);

        // initiate statuses
        Label labelTotal = new Label("Total: correct/tested");
        labelTotal.setFont(new Font(24.0));
        Label labelStatus = new Label("Status: Testing");
        labelStatus.setFont(new Font(24.0));

        // initiate menus
        Button buttonEnd = new Button("End test");
        buttonEnd.setPrefSize(96.0*WIDTH_MAGNITUDE, 48.0*HEIGHT_MAGNITUDE);
        buttonEnd.setAlignment(Pos.CENTER);
        buttonEnd.setOnAction(actionEvent -> window.setScene(new Scene(statistic())));

        Button buttonHear = new Button("Hear");
        buttonHear.setPrefSize(96.0*WIDTH_MAGNITUDE, 48.0*HEIGHT_MAGNITUDE);
        buttonHear.setAlignment(Pos.CENTER);
        buttonHear.setOnAction(actionEvent -> Play.midi(note));

        Button buttonNext = new Button("Next");
        buttonNext.setDisable(true);
        buttonNext.setPrefSize(96.0*WIDTH_MAGNITUDE, 48.0*HEIGHT_MAGNITUDE);
        buttonNext.setAlignment(Pos.CENTER);
        buttonNext.setOnAction(actionEvent -> {
            buttonNext.setDisable(true);
            buttonHear.setDisable(false);
            flowPanePitch.setDisable(false);
            labelStatus.setText("Status: Testing");
            labelStatus.setStyle("-fx-text-fill: black");
            note = scale.getNewRandomNote();
            Play.midi(note);
        });

        hBoxMenu.getChildren().addAll(buttonHear, buttonNext);
        hBoxTotal.getChildren().addAll(labelTotal, buttonEnd);
        hBoxStatus.getChildren().addAll(labelStatus);

        for (PitchManagerEnum pitch: PitchManagerEnum.values()) {
            if (scale.isInScale(pitch)) {
                Button pitchButton = new Button(pitch.getPitchName());
                pitchButton.setAlignment(Pos.CENTER);
                pitchButton.setPrefSize(48.0*HEIGHT_MAGNITUDE, 48.0*HEIGHT_MAGNITUDE);
                pitchButton.setOnAction(actionEvent -> {
                    if (note.getName().equals(pitch.getPitchName())) {
                        PitchManagerEnum.getPitchByNote(note).testedAndCorrect();
                        labelStatus.setText("Status: Correct!");
                        labelStatus.setStyle("-fx-text-fill: green");
                    } else {
                        PitchManagerEnum.getPitchByNote(note).testedAndIncorrect();
                        labelStatus.setText("Status: Incorrect!");
                        labelStatus.setStyle("-fx-text-fill: red");
                    }
                    buttonNext.setDisable(false);
                    buttonHear.setDisable(true);
                    flowPanePitch.setDisable(true);
                    labelTotal.setText(String.format("Total: %d/%d", PitchManagerEnum.getAllCorrect(), PitchManagerEnum.getAllTested()));
                });
                flowPanePitch.getChildren().add(pitchButton);
            }
        }

        // Play first note since this root has been initiated.
        Play.midi(note);

        vbox.getChildren().addAll(hBoxTotal, hBoxStatus, hBoxMenu, flowPanePitch);
        return vbox;
    }

    private VBox statistic() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(32.0*WIDTH_MAGNITUDE));
        vBox.setSpacing(32.0*HEIGHT_MAGNITUDE);

        int allCorrect = PitchManagerEnum.getAllCorrect();
        int allTested = PitchManagerEnum.getAllTested();
        double allAccurate = PitchManagerEnum.getAllAccurate();
        Label labelStatistic = new Label("Statistic");
        labelStatistic.setFont(new Font(36.0));
        Label labelResult = new Label(String.format("You got %d of %d which is %.2f%%", allCorrect, allTested, allAccurate));
        labelResult.setFont(new Font(24.0));

        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(48.0*WIDTH_MAGNITUDE);
        gridPane.setVgap(4.0*HEIGHT_MAGNITUDE);

        Label labelPitch = new Label("Pitch");
        Label labelScore = new Label("Score");
        Label labelAccuracy = new Label("Accuracy");
        labelPitch.setFont(new Font(16.0));
        labelScore.setFont(new Font(16.0));
        labelAccuracy.setFont(new Font(16.0));

        gridPane.add(labelPitch, 0,0);
        gridPane.add(labelScore, 1, 0);
        gridPane.add(labelAccuracy, 2, 0);
        for (PitchManagerEnum pitch : PitchManagerEnum.values()) {
            if (pitch.isTested()) {
                Label labelPitchName = new Label(pitch.getPitchName());
                Label labelScorePitch = new Label(String.format("%d of %d", pitch.getCorrect(), pitch.getTested()));
                Label labelAccuracyPitch = new Label(String.format("%.2f%%", pitch.getAccuracy()));
                labelPitchName.setFont(new Font(16.0));
                labelScorePitch.setFont(new Font(16.0));
                labelAccuracyPitch.setFont(new Font(16.0));
                int rowCount = gridPane.getRowCount();
                gridPane.add(labelPitchName, 0, rowCount + 2);
                gridPane.add(labelScorePitch, 1, rowCount + 2);
                gridPane.add(labelAccuracyPitch, 2, rowCount + 2);
            }
        }

        Button buttonMainMenu = new Button("Main Menu");
        buttonMainMenu.setPrefSize(96.0*WIDTH_MAGNITUDE, 48.0*HEIGHT_MAGNITUDE);
        buttonMainMenu.setOnAction(actionEvent -> {
            window.setScene(new Scene(mainMenu()));
            PitchManagerEnum.resetAll();
        });

        vBox.getChildren().addAll(labelStatistic, labelResult, gridPane, buttonMainMenu);

        return vBox;
    }
}