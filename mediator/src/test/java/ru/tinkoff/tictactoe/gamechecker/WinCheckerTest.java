package ru.tinkoff.tictactoe.gamechecker;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.tinkoff.tictactoe.gamechecker.impl.GameCheckerImpl;
import ru.tinkoff.tictactoe.session.Figure;

public class WinCheckerTest {

    GameChecker gameChecker = new GameCheckerImpl(
        List.of(
            new OnTheReverseDiagonalLineWinChecker(),
            new OnTheHorizontalLineWinChecker(),
            new OnTheVerticalLineWinChecker(),
            new OnTheDiagonalLineWinChecker()
        ),
        List.of()
    );

    @ParameterizedTest(name = "{index}: {0} test")
    @MethodSource("args")
    public void test(
        String name,
        String gameField,
        WinCheckerResults expectedResult
    ) {
        assertThat(gameChecker.isWin(gameField, Figure.CROSS)).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> args() {
        return Stream.of(
            Arguments.of(
                "Vertical lines",
                "" +
                    "xoxoxoxoxoxoxoxoxox" +
                    "xoxoxoxoxoxoxoxoxox" +
                    "xoxoxoxoxoxoxoxoxox" +
                    "xoxoxoxoxoxoxoxoxox" +
                    "x__________________" +
                    "___________________" +
                    "___________________" +
                    "___________________" +
                    "___________________" +
                    "___________________" +
                    "___________________" +
                    "___________________" +
                    "___________________" +
                    "___________________" +
                    "___________________" +
                    "___________________" +
                    "___________________" +
                    "___________________" +
                    "___________________",
                new GameWinResult(
                    "" +
                        "Xoxoxoxoxoxoxoxoxox" +
                        "Xoxoxoxoxoxoxoxoxox" +
                        "Xoxoxoxoxoxoxoxoxox" +
                        "Xoxoxoxoxoxoxoxoxox" +
                        "X__________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________"
                )
            ),
            Arguments.of(
                "Horizontal lines",
                "" +
                    "xxxxx______________" +
                    "oooo_______________" +
                    "xxxx_______________" +
                    "oooo_______________" +
                    "xxxx_______________" +
                    "oooo_______________" +
                    "xxxx_______________" +
                    "oooo_______________" +
                    "xxxx_______________" +
                    "oooo_______________" +
                    "xxxx_______________" +
                    "oooo_______________" +
                    "xxxx_______________" +
                    "oooo_______________" +
                    "xxxx_______________" +
                    "oooo_______________" +
                    "xxxx_______________" +
                    "oooo_______________" +
                    "xxxx_______________",
                new GameWinResult(
                    "" +
                        "XXXXX______________" +
                        "oooo_______________" +
                        "xxxx_______________" +
                        "oooo_______________" +
                        "xxxx_______________" +
                        "oooo_______________" +
                        "xxxx_______________" +
                        "oooo_______________" +
                        "xxxx_______________" +
                        "oooo_______________" +
                        "xxxx_______________" +
                        "oooo_______________" +
                        "xxxx_______________" +
                        "oooo_______________" +
                        "xxxx_______________" +
                        "oooo_______________" +
                        "xxxx_______________" +
                        "oooo_______________" +
                        "xxxx_______________"
                )
            ),
            Arguments.of(
                "Diagonal line",
                "" +
                    "x_______o__________" +
                    "_x______o__________" +
                    "__x_____o__________" +
                    "___x____o__________" +
                    "____x______________" +
                    "___________________" +
                    "___________________" +
                    "___________________" +
                    "___________________" +
                    "___________________" +
                    "___________________" +
                    "___________________" +
                    "___________________" +
                    "___________________" +
                    "___________________" +
                    "___________________" +
                    "___________________" +
                    "___________________" +
                    "___________________",
                new GameWinResult(
                    "" +
                        "X_______o__________" +
                        "_X______o__________" +
                        "__X_____o__________" +
                        "___X____o__________" +
                        "____X______________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________" +
                        "___________________"
                )
            )
        );
    }
}
