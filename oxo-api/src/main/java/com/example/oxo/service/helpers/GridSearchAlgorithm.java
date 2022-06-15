package com.example.oxo.service.helpers;
import com.example.oxo.model.SearchDirection;
import org.springframework.stereotype.Service;

@Service
public class GridSearchAlgorithm {

    public boolean directNeighbourSearch(SearchDirection direction, AlignmentStepper stepper) {

        if (!isNextSymbolMatching(direction, stepper)) {

            return searchTheOtherWayAround(direction, stepper);
        } else {
            stepper.stepUp();
            if (stepper.getGoal() != stepper.scoreUp()) {
                return directNeighbourSearch(direction, stepper);
            }
            return true;
        }
    }

    /////////////////////
    // private methods //
    /////////////////////

    /**
     * permit stepping back in a direction whenever the new searched position is either
     * - out of bound
     * - different from the desired symbol.
     *
     * @param direction the direction to search.
     * @param stepper   the object containing the stepping in the symbol research.
     * @return
     */
    private boolean searchTheOtherWayAround(SearchDirection direction, final AlignmentStepper stepper) {
         /*
                the research blocked twice in a direction
                => too many times out of bound or different symbols
            */
        if (stepper.flagTheDirection() == 2) {
            return false;
        }

        direction.turnBack();

        // starting over from the step one. But it will be in the other way
        stepper.resetStep();

        return directNeighbourSearch(direction, stepper);
    }

    private boolean isNextSymbolMatching(SearchDirection direction, final AlignmentStepper stepper) {
        int nextLine = stepper.getMove().getLine() + stepper.getStep() * direction.getLine();
        int nextColumn = stepper.getMove().getColumn() + stepper.getStep() * direction.getColumn();

        return stepper.getSymbol().equals(stepper.getSymbole(nextLine, nextColumn));
    }
}
