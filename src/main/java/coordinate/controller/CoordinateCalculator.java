package coordinate.controller;

import coordinate.model.Figure;
import coordinate.model.FigureFactory;
import coordinate.view.InputView;
import coordinate.view.OutputView;

public class CoordinateCalculator {
    public void run() {
        try {
            Figure figure = FigureFactory.create(InputView.inputCoordinates());
            OutputView.showCoordinatePlane(figure.getXYCoordinates());
            OutputView.print(figure.getAreaInfo());
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
