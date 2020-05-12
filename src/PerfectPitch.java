import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import crypter.Crypter;
import crypter.Decrypter;
import crypter.Encrypter;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * src.PerfectPitch GUI Application.
 * @author Sahatsawat Kanpai
 */
public class PerfectPitch extends Application {

    /** User to be used to manage the data. */
    static User user;
    /** Key for Crypter. */
    static final int KEY = 47;
    /** Scale for perfect pitch tests. */
    static ScaleStrategy scale;
    /** Value of constant phi is 1.61803398874989484820... (aka Golden Ratio) */
    final static double phi = (1+Math.sqrt(5))/2;
    /** Initialize first note. */
    static Note note;
    /** Main stage. */
    static Stage window;
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

        window.setScene(new Scene(loginRoot()));
        window.setResizable(false);
        window.show();

        User.setUsers(readAndReturnInitUsers());
    }

    /**
     * MenuBar for every root.
     * @return MenuBar for every root
     */
    private MenuBar menuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");
        MenuItem menuItemExit = new MenuItem("Exit");
        menuItemExit.setOnAction(actionEvent -> System.exit(0));

        menu.getItems().addAll(menuItemExit);
        menuBar.getMenus().addAll(menu);
        return menuBar;
    }

    public VBox loginRoot() {
        VBox vBoxMinor = new VBox();
        vBoxMinor.setPadding(new Insets(32.0*HEIGHT_MAGNITUDE));
        vBoxMinor.setAlignment(Pos.CENTER);
        vBoxMinor.setSpacing(10.0*HEIGHT_MAGNITUDE);

        Label labelHeader = new Label("Login/Registration");

        HBox usernameHBox = new HBox();
        usernameHBox.setAlignment(Pos.CENTER);
        HBox passwordHBox = new HBox();
        passwordHBox.setAlignment(Pos.CENTER);

        Label labelUsername = new Label("Username: ");
        Label labelPassword = new Label("Password: ");

        TextField textFieldUsername = new TextField();
        textFieldUsername.setPromptText("Input username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Input password");

        Label labelSuggestion = new Label("New user can register with just type username and password and press login");

        usernameHBox.getChildren().addAll(labelUsername, textFieldUsername);
        passwordHBox.getChildren().addAll(labelPassword, passwordField);

        Button buttonLogin = new Button("Login");
        buttonLogin.setOnAction(actionEvent -> {
            String usernameInput = textFieldUsername.getText();
            String passwordInput = passwordField.getText();
            if (usernameInput.isBlank() || passwordInput.isBlank()) {
                Alert alertBlank = new Alert(Alert.AlertType.WARNING, "Username and Password cannot be blank");
                alertBlank.showAndWait();
            } else if (usernameInput.contains(" ") || passwordInput.contains(" ")) {
                Alert alertSpace = new Alert(Alert.AlertType.WARNING, "No spacing in Username and Password");
                alertSpace.showAndWait();
            } else {
                user = User.findOrCreateUser(usernameInput, passwordInput);
                if (user == null) {
                    Alert alertWrong = new Alert(Alert.AlertType.ERROR, "Wrong username or password");
                    alertWrong.showAndWait();
                } else {
                    Alert alertWelcome = new Alert(Alert.AlertType.NONE,
                            String.format("Welcome %s to PerfectPitch", user.getUsername()), ButtonType.OK);
                    alertWelcome.showAndWait();
                    writeUsersData();
                    window.setScene(new Scene(mainMenuRoot()));
                }
            }
        });

        vBoxMinor.getChildren().addAll(labelHeader, usernameHBox, passwordHBox, labelSuggestion, buttonLogin);

        VBox vBoxMajor = new VBox();
        vBoxMajor.getChildren().addAll(menuBar(), vBoxMinor);

        return vBoxMajor;
    }

    public VBox passwordChangeRoot() {
        VBox vBoxMinor = new VBox();
        vBoxMinor.setPadding(new Insets(32.0*HEIGHT_MAGNITUDE));
        vBoxMinor.setAlignment(Pos.CENTER);
        vBoxMinor.setSpacing(10.0*HEIGHT_MAGNITUDE);

        Label labelHeader = new Label("Login/Registration");

        HBox passwordHBox = new HBox();
        passwordHBox.setAlignment(Pos.CENTER);
        HBox newPasswordHBox = new HBox();
        newPasswordHBox.setAlignment(Pos.CENTER);
        HBox retypeNewPasswordHBox = new HBox();
        retypeNewPasswordHBox.setAlignment(Pos.CENTER);
        HBox hBoxButton = new HBox();
        hBoxButton.setAlignment(Pos.CENTER);
        hBoxButton.setSpacing(10.0*HEIGHT_MAGNITUDE);

        Label labelOldPassword = new Label("Old Password: ");
        Label labelNewPassword = new Label("New Password: ");
        Label labelRetypePassword = new Label("Retype New Password: ");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Input old password");
        PasswordField newPasswordField = new PasswordField();
        passwordField.setPromptText("Input new password");
        PasswordField retypeNewPasswordField = new PasswordField();
        retypeNewPasswordField.setPromptText("Retype new password");

        Label labelSuggestion = new Label("Never tell your password to anyone!");

        Button buttonLogin = new Button("Apply");
        Button buttonCancel = new Button("Cancel");

        passwordHBox.getChildren().addAll(labelOldPassword, passwordField);
        newPasswordHBox.getChildren().addAll(labelNewPassword, newPasswordField);
        retypeNewPasswordHBox.getChildren().addAll(labelRetypePassword, retypeNewPasswordField);
        hBoxButton.getChildren().addAll(buttonLogin, buttonCancel);

        buttonLogin.setOnAction(actionEvent -> {
            String passwordInput = passwordField.getText();
            String newPasswordInput = newPasswordField.getText();
            String retypeNewPasswordInput = retypeNewPasswordField.getText();
            if (passwordInput.isBlank() || newPasswordInput.isBlank() || retypeNewPasswordInput.isBlank()) {
                Alert alertBlank = new Alert(Alert.AlertType.WARNING, "Password cannot be blank", ButtonType.OK);
                alertBlank.showAndWait();
            } else if (newPasswordInput.contains(" ") || retypeNewPasswordInput.contains(" ")) {
                Alert alertSpacing = new Alert(Alert.AlertType.WARNING, "No spacing for password", ButtonType.OK);
                alertSpacing.showAndWait();
            } else if (passwordInput.equals(user.getPassword()) && retypeNewPasswordInput.equals(newPasswordInput)) {
                user.setPassword(newPasswordInput);
                writeUsersData();
                Alert alertSuccessful = new Alert(Alert.AlertType.INFORMATION, "Success", ButtonType.OK);
                alertSuccessful.showAndWait();
                window.setScene(new Scene(mainMenuRoot()));
            } else if (!passwordInput.equals(user.getPassword())) {
                Alert alertWrongPassword = new Alert(Alert.AlertType.ERROR, "Wrong password", ButtonType.OK);
                alertWrongPassword.showAndWait();
            } else {
                Alert alertRetype = new Alert(Alert.AlertType.WARNING,
                        "Retyped new password was not the same", ButtonType.OK);
                alertRetype.showAndWait();
            }
        });

        buttonCancel.setOnAction(actionEvent -> window.setScene(new Scene(mainMenuRoot())));

        vBoxMinor.getChildren().addAll(labelHeader, passwordHBox, newPasswordHBox, retypeNewPasswordHBox,
                labelSuggestion, hBoxButton);

        VBox vBoxMajor = new VBox();
        vBoxMajor.getChildren().addAll(menuBar(), vBoxMinor);

        return vBoxMajor;
    }

    /**
     * Root of MainMenu.
     * @return GridPane root of MainMenu
     */
    private VBox mainMenuRoot() {
        VBox vBox = new VBox();

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10.0*HEIGHT_MAGNITUDE);
        gridPane.setPadding(new Insets(64.0*HEIGHT_MAGNITUDE));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(96.0*HEIGHT_MAGNITUDE);
        gridPane.setStyle("-fx-background-color: darkgray");

        Label labelPerfectPitch = new Label("Perfect\nPitch");
        labelPerfectPitch.setStyle("-fx-text-fill: steelblue; -fx-font-size: 72.0; -fx-font-family: Forte ");

        Button buttonTest = new Button("Test");
        Button buttonStatistics = new Button("Statistics");
        Button buttonChangePassword = new Button("Change Password");
        buttonTest.setStyle("-fx-font-size: 24.0; -fx-background-color: cadetblue");
        buttonStatistics.setStyle("-fx-font-size: 24.0; -fx-background-color: cadetblue");
        buttonChangePassword.setStyle("-fx-font-size: 18.0; -fx-background-color: cadetblue");

        buttonTest.setOnAction(actionEvent -> window.setScene(new Scene(scaleChoosingRoot())));
        buttonStatistics.setOnAction(actionEvent -> window.setScene(new Scene(statisticsRoot())));
        buttonChangePassword.setOnAction(actionEvent -> window.setScene(new Scene(passwordChangeRoot())));

        gridPane.add(labelPerfectPitch, 0, 0, 1, 3);
        gridPane.add(buttonTest, 1, 0);
        gridPane.add(buttonStatistics, 1, 1);
        gridPane.add(buttonChangePassword, 1, 2);

        vBox.getChildren().addAll(menuBar(), gridPane);

        return vBox;
    }

    private VBox statisticsRoot() {
        VBox vBoxMinor = new VBox();
        vBoxMinor.setSpacing(10.0*HEIGHT_MAGNITUDE);
        vBoxMinor.setPadding(new Insets(32.0*HEIGHT_MAGNITUDE));
        vBoxMinor.setAlignment(Pos.CENTER);

        Label labelStatistics = new Label("Statistics");
        labelStatistics.setFont(new Font(36.0));

        Label labelYourStatistic = new Label(String.format("Your statistic is: %d of %d which is %.2f%%" ,
                user.getCorrect(), user.getTested(), user.getAccuracy()));

        Button buttonMainMenu = new Button("Main Menu");
        buttonMainMenu.setOnAction(actionEvent -> window.setScene(new Scene(mainMenuRoot())));

        vBoxMinor.getChildren().addAll(labelStatistics, labelYourStatistic, userTableView(), buttonMainMenu);

        VBox vBoxMajor = new VBox();
        vBoxMajor.getChildren().addAll(menuBar(), vBoxMinor);

        return vBoxMajor;
    }

    private TableView<UserForObserve> userTableView() {
        // Username column
        TableColumn<UserForObserve, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setPrefWidth(256.0*HEIGHT_MAGNITUDE);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        // Tested column
        TableColumn<UserForObserve, Integer> testedColumn = new TableColumn<>("Tested");
        testedColumn.setPrefWidth(128.0*HEIGHT_MAGNITUDE);
        testedColumn.setCellValueFactory(new PropertyValueFactory<>("tested"));

        // Correct column
        TableColumn<UserForObserve, Integer> correctColumn = new TableColumn<>("Correct");
        correctColumn.setPrefWidth(128.0*HEIGHT_MAGNITUDE);
        correctColumn.setCellValueFactory(new PropertyValueFactory<>("correct"));

        // Accuracy column
        TableColumn<UserForObserve, Double> accuracyColumn = new TableColumn<>("Accuracy (Percentage)");
        accuracyColumn.setPrefWidth(128.0*HEIGHT_MAGNITUDE);
        accuracyColumn.setCellValueFactory(new PropertyValueFactory<>("accuracy"));

        TableView<UserForObserve> table = new TableView<>();
        table.setItems(getObservableUser());
        table.getColumns().addAll(usernameColumn, testedColumn, correctColumn, accuracyColumn);

        return table;
    }

    private ObservableList<UserForObserve> getObservableUser() {
        ObservableList<UserForObserve> users = FXCollections.observableArrayList();
        for (User user : User.getUsers()) {
            users.add(new UserForObserve(user.getUsername(), user.getTested(), user.getCorrect(), user.getAccuracy()));
        }
        return users;
    }

    private VBox scaleChoosingRoot() {
        VBox vBoxMinor = new VBox();
        vBoxMinor.setAlignment(Pos.CENTER);
        vBoxMinor.setPadding(new Insets(32.0*HEIGHT_MAGNITUDE));
        vBoxMinor.setSpacing(10.0*HEIGHT_MAGNITUDE);

        Label topicLabel = new Label("Choose a scale");
        topicLabel.setFont(new Font(36.0));
        Label suggestionLabel = new Label("(Only A chromatic scale is a real perfect pitch)");
        suggestionLabel.setFont(new Font(24.0));

        HBox scaleChoosingHBox = new HBox();
        scaleChoosingHBox.setAlignment(Pos.CENTER);
        Label labelScale = new Label("Scale: ");
        labelScale.setFont(new Font(24.0));
        ComboBox<Scale> scaleComboBox = new ComboBox<>();
        scaleComboBox.setPrefSize(128.0*WIDTH_MAGNITUDE, 48.0*HEIGHT_MAGNITUDE);
        scaleComboBox.getItems().addAll(Scale.values());
        scaleComboBox.setValue(Scale.ChromaticScale);
        scaleChoosingHBox.getChildren().addAll(labelScale, scaleComboBox);

        Button buttonReady = new Button("Ready");
        buttonReady.setPrefSize(128.0*WIDTH_MAGNITUDE, 48.0*HEIGHT_MAGNITUDE);
        buttonReady.setOnAction(actionEvent -> {
            scale = scaleComboBox.getValue().getScaleStrategy();
            note = scale.getNewRandomNote();
            window.setScene(new Scene(earTestRoot()));
        });

        vBoxMinor.getChildren().addAll(topicLabel, suggestionLabel, scaleChoosingHBox, buttonReady);

        VBox vBoxMajor = new VBox();
        vBoxMajor.getChildren().addAll(menuBar(), vBoxMinor);

        return vBoxMajor;
    }

    /**
     * Root for chromatic scale test.
     * @return VBox root of chromatic scale test
     */
    private VBox earTestRoot() {
        VBox vBoxMinor = new VBox();
        vBoxMinor.setFillWidth(true);
        vBoxMinor.setPrefSize(DISPLAY_WIDTH * 0.5/phi, DISPLAY_HEIGHT * 0.5);
        vBoxMinor.setSpacing(32.0);
        vBoxMinor.setAlignment(Pos.CENTER);
        vBoxMinor.setPadding(new Insets(32.0*HEIGHT_MAGNITUDE));

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
        buttonEnd.setOnAction(actionEvent -> { // User data save here
            user.setTested(user.getTested() + PitchManagerEnum.getAllTested());
            user.setCorrect(user.getCorrect() + PitchManagerEnum.getAllCorrect());
            writeUsersData();
            window.setScene(new Scene(statisticRoot()));
        });

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
                    if (note.getName().equals(pitch.getPitchName())) { // Pitch correct
                        PitchManagerEnum.getPitchByNote(note).testedAndCorrect();
                        labelStatus.setText("Status: Correct!");
                        labelStatus.setStyle("-fx-text-fill: green");
                    } else {
                        PitchManagerEnum.getPitchByNote(note).testedAndIncorrect(); // Pitch incorrect
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

        vBoxMinor.getChildren().addAll(hBoxTotal, hBoxStatus, hBoxMenu, flowPanePitch);

        VBox vBoxMajor = new VBox();
        vBoxMajor.getChildren().addAll(menuBar(), vBoxMinor);
        return vBoxMajor;
    }

    /**
     * Root for statistic
     * @return VBox root of statistic
     */
    private VBox statisticRoot() {
        VBox vBoxMinor = new VBox();
        vBoxMinor.setAlignment(Pos.CENTER);
        vBoxMinor.setPadding(new Insets(32.0*WIDTH_MAGNITUDE));
        vBoxMinor.setSpacing(32.0*HEIGHT_MAGNITUDE);

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
            window.setScene(new Scene(mainMenuRoot()));
            PitchManagerEnum.resetAll();
        });

        vBoxMinor.getChildren().addAll(labelStatistic, labelResult, gridPane, buttonMainMenu);

        VBox vBoxMajor = new VBox();
        vBoxMajor.getChildren().addAll(menuBar(), vBoxMinor);

        return vBoxMajor;
    }

    /**
     * Read a datasheet csv file and return Users.
     * @return ArrayList of Users
     */
    private List<User> readAndReturnInitUsers() {
        List<User> usersInitList = new ArrayList<>();
        List<String[]> rowList = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new FileReader("src/data/datasheet.csv"));
            reader.readNext(); // skip header column
            rowList = reader.readAll(); // then get remaining column
            reader.close();
        } catch (IOException | CsvException e) {
            Alert dialog = new Alert(Alert.AlertType.ERROR, "datasheet.csv not found, " +
                    "and will be created", ButtonType.OK);
            dialog.show();
        }

        Crypter decrypter = new Decrypter();
        for (String[] columnArray : rowList) {
            String username = decrypter.unicode(columnArray[0], KEY);
            String password = decrypter.unicode(columnArray[1], KEY);
            int tested = Integer.parseInt(decrypter.unicode(columnArray[2], KEY));
            int correct = Integer.parseInt(decrypter.unicode(columnArray[3], KEY));
            usersInitList.add(new User(username, password, tested, correct));
        }
        return usersInitList;
    }

    /**
     * Write data of Users in a datasheet csv file.
     */
    private void writeUsersData() {
        Crypter encrypter = new Encrypter();
        String[] headerColumn = {"username", "password", "tested", "correct"};
        try {
            CSVWriter writer = new CSVWriter(new FileWriter("src/data/datasheet.csv"));
            writer.writeNext(headerColumn);
            for (User user : User.getUsers()) {
                String encryptedUsername = encrypter.unicode(user.getUsername(), KEY);
                String encryptedPassword = encrypter.unicode(user.getPassword(), KEY);
                String encryptedTested = encrypter.unicode(Integer.toString(user.getTested()), KEY);
                String encryptedCorrect = encrypter.unicode(Integer.toString(user.getCorrect()), KEY);
                String[] columnArray = {encryptedUsername, encryptedPassword, encryptedTested, encryptedCorrect};
                writer.writeNext(columnArray);
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static class UserForObserve {
        private final String username;
        private final double accuracy;
        private final int correct;
        private final int tested;

        public UserForObserve(String username, int tested, int correct, double accuracy) {
            this.username = username;
            this.tested = tested;
            this.correct = correct;
            this.accuracy = accuracy;
        }

        public String getUsername() {
            return username;
        }

        public int getTested() {
            return tested;
        }

        public int getCorrect() {
            return correct;
        }

        public double getAccuracy() {
            return accuracy;
        }
    }
}