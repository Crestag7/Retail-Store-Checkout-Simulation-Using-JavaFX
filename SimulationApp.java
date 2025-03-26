import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SimulationApp extends Application {

    private TextArea outputArea;
    private Slider arrivalSlider;
    private ToggleGroup linesGroup;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Retail Checkout Simulation");

        // Radio buttons for selecting number of lines
        linesGroup = new ToggleGroup();
        RadioButton oneLine = new RadioButton("1 Line");
        oneLine.setToggleGroup(linesGroup);
        oneLine.setSelected(true);
        RadioButton twoLines = new RadioButton("2 Lines");
        twoLines.setToggleGroup(linesGroup);
        RadioButton threeLines = new RadioButton("3 Lines");
        threeLines.setToggleGroup(linesGroup);

        HBox linesBox = new HBox(10, oneLine, twoLines, threeLines);

        // Slider for arrival frequency
        arrivalSlider = new Slider(10, 360, 60); // default = 60 seconds
        arrivalSlider.setShowTickLabels(true);
        arrivalSlider.setShowTickMarks(true);
        arrivalSlider.setMajorTickUnit(50);
        arrivalSlider.setBlockIncrement(10);

        VBox sliderBox = new VBox(5, new Label("Customer Arrival Frequency (1 every N seconds):"), arrivalSlider);

        // Button to start simulation
        Button simulateButton = new Button("Start Simulation");
        simulateButton.setOnAction(e -> runSimulation());

        // Output area
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setPrefHeight(300);

        VBox root = new VBox(15, linesBox, sliderBox, simulateButton, outputArea);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void runSimulation() {
        int linesOpen = getSelectedLines();
        int arrivalChance = (int) arrivalSlider.getValue();

        RetailCheckoutSimulation sim = new RetailCheckoutSimulation(linesOpen, arrivalChance);
        String result = sim.runSimulation();

        outputArea.setText(result);
    }

    private int getSelectedLines() {
        RadioButton selected = (RadioButton) linesGroup.getSelectedToggle();
        String text = selected.getText();
        if (text.startsWith("1")) return 1;
        if (text.startsWith("2")) return 2;
        return 3;
    }
} 