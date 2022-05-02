package com.example.oxo.model.enums;

public enum ConclusionType {
    // the game is still ongoing until no move is possible
    ONGOING,

    /*
    When someone wins and the victory of 1-N players matters.
    Or the game is impossible to be continued.
    This state is not accountable to tell which position on podium
    players are if there are more than 2 players
    */
    FINISHED,

    // when two players compete or more and none of them wins or loses
    DRAW,

    // There is no game to be retrieved
    NON_EXISTENT,

    // One move chosen cannot be allowed
    FAULTED,

    // There may be a problem regarding some preferences
    INVALID,
}
