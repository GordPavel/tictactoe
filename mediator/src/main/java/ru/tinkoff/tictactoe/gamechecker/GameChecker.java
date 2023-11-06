package ru.tinkoff.tictactoe.gamechecker;

import ru.tinkoff.tictactoe.session.Figure;

public interface GameChecker {
    WinCheckerResults isWin(String gameField, Figure figure);

    void validate(String currGameField, String newGameField, Figure figure);
}
