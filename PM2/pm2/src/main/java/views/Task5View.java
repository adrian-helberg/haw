package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import structures.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Task5View implements Initializable {

    @FXML
    public ListView<String> listView;
    public Canvas canvas;
    public Label label;
    public Spinner<Integer> depth;
    public TextField z1;
    public TextField z1i;
    public TextField z2;
    public TextField z2i;
    public TextField z3;
    public TextField z3i;
    // Panes
    public Pane pane_depth;
    public Pane pane_z1;
    public Pane pane_z1i;
    public Pane pane_z2;
    public Pane pane_z2i;
    public Pane pane_z3;
    public Pane pane_z3i;
    public Pane pane_zoom;
    // Buttons
    public Button button_draw;
    public Button zoomIn;
    public Button zoomOut;

    private FractalShape selectedFractal;
    private double zoom = 1.0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Initially disable fields
        pane_depth.setDisable(true);
        pane_z1.setDisable(true);
        pane_z1i.setDisable(true);
        pane_z2.setDisable(true);
        pane_z2i.setDisable(true);
        pane_z3.setDisable(true);
        pane_z3i.setDisable(true);
        pane_zoom.setDisable(true);
        button_draw.setDisable(true);

        // Depth
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 7, 0);
        depth.setValueFactory(valueFactory);
        depth.valueProperty().addListener((observable, oldValue, newValue) -> {
            clearCanvas();
            selectedFractal = new KochCurve(depth.getValue(), 9, canvas);
        });

        // Draw button
        button_draw.setOnAction(e -> draw());

        // Zoom button
        zoomIn.setOnAction(e ->  {
            zoom -= 0.1;
            draw();
        });

        zoomOut.setOnAction(e ->  {
            zoom += 0.1;
            draw();
        });

        // List items
        ObservableList<String> items = FXCollections.observableArrayList(
                "Koch Kurve", "Mandelbrot Menge", "Julia Menge");
        listView.setItems(items);

        // List item select handler
        listView.getSelectionModel().selectedItemProperty().addListener((arg0, oldval, newVal) -> {
            if (newVal == null) return;
            label.setText(newVal);

            switch (newVal) {
                case "Koch Kurve":
                    pane_depth.setDisable(false);
                    pane_z1.setDisable(true);
                    pane_z1i.setDisable(true);
                    pane_z2.setDisable(true);
                    pane_z2i.setDisable(true);
                    pane_z3.setDisable(true);
                    pane_z3i.setDisable(true);
                    pane_zoom.setDisable(true);
                    button_draw.setDisable(false);
                    break;
                case "Mandelbrot Menge":
                    pane_depth.setDisable(true);
                    pane_z1.setDisable(false);
                    pane_z1i.setDisable(false);
                    pane_z2.setDisable(false);
                    pane_z2i.setDisable(false);
                    pane_z3.setDisable(true);
                    pane_z3i.setDisable(true);
                    pane_zoom.setDisable(false);
                    button_draw.setDisable(false);

                    // Set default values
                    z1.setText("-2");
                    z1i.setText("-1");
                    z2.setText("1");
                    z2i.setText("1");

                    break;
                case "Julia Menge":
                    pane_depth.setDisable(true);
                    pane_z1.setDisable(false);
                    pane_z1i.setDisable(false);
                    pane_z2.setDisable(false);
                    pane_z2i.setDisable(false);
                    pane_z3.setDisable(false);
                    pane_z3i.setDisable(false);
                    pane_zoom.setDisable(false);
                    button_draw.setDisable(false);

                    // Set default values
                    z1.setText("-1.5");
                    z1i.setText("-1.5");
                    z2.setText("1.5");
                    z2i.setText("1.5");
                    z3.setText("0.3");
                    z3i.setText("-0.5");

                    break;
                default:
                    System.out.println("Cannot recognize fractal");
                    break;
            }
        });
    }

    private double getTextFieldValue(TextField textField) {
        return textField.getText().equals("") ? 0.0 : Double.parseDouble(textField.getText());
    }

    private void clearCanvas() {
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void draw() {
        ImmutableComplex min, max, z;
        switch(listView.getSelectionModel().getSelectedItem()) {
            case "Koch Kurve":
                clearCanvas();
                selectedFractal = new KochCurve(depth.getValue(), 9, canvas);
                break;
            case "Mandelbrot Menge":
                min = new ImmutableComplex(
                        getTextFieldValue(z1),
                        getTextFieldValue(z1i)
                );

                max = new ImmutableComplex(
                        getTextFieldValue(z2),
                        getTextFieldValue(z2i)
                );

                clearCanvas();
                selectedFractal = new MandelbrotSet(min, max, 50, canvas, zoom);
                break;
            case "Julia Menge":
                min = new ImmutableComplex(
                        getTextFieldValue(z1),
                        getTextFieldValue(z1i)
                );

                max = new ImmutableComplex(
                        getTextFieldValue(z2),
                        getTextFieldValue(z2i)
                );

                z = new ImmutableComplex(
                        getTextFieldValue(z3),
                        getTextFieldValue(z3i)
                );

                clearCanvas();
                selectedFractal = new JuliaSet(min, max, 50, canvas, z, zoom);
                break;
            default:
                System.out.println("Cannot recognize fractal");
                break;
        }
    }
}
