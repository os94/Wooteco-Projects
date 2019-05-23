package coordinate.controller;

import coordinate.model.Figure;
import coordinate.view.InputView;
import coordinate.view.OutputView;

public class CoordinateCalculator {
    public void run() {
        Figure figure = InputView.inputCoordinates();
        OutputView.showCoordinatePlane(figure);
        OutputView.showArea(figure);
    }
}
