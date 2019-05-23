package coordinate.controller;

import coordinate.model.AbstractFigure;
import coordinate.view.InputView;
import coordinate.view.OutputView;

public class CoordinateCalculator {
    public void run() {
        AbstractFigure abstractFigure = InputView.inputCoordinates();
        OutputView.showCoordinatePlane(abstractFigure);
        OutputView.showArea(abstractFigure);
    }
}
