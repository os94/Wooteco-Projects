package ladderGame.view;

import ladderGame.constant.Contants;
import ladderGame.domain.*;

public class OutputView {
    private static final String NEW_LINE = "\n";

    public static void printLadder(Ladder ladder, Members members, Goals goals) {
        System.out.println(NEW_LINE + Contants.MESSAGE_LADDER_RESULT + NEW_LINE);
        printMembers(members);
        emptyLine();
        printLadderShape(ladder, members.size());
        printGoals(goals);
        emptyLine();
    }

    private static void printMembers(Members members) {
        for (String member : members.getMembers()) {
            System.out.printf("%6s", member);
        }
    }

    private static void printGoals(Goals goals) {
        for (String goal : goals.getGoals()) {
            System.out.printf("%6s", goal);
        }
    }

    private static void printLadderShape(Ladder ladder, int size) {
        for (Row row : ladder.getRows()) {
            printRow(row, size);
        }
    }

    private static void printRow(Row row, int columnNumber) {
        System.out.print(Contants.MESSAGE_INDENT + Contants.MESSAGE_COLUMN);
        for (int index = 0; index < columnNumber - 1; index++) {
            printBridge(row, index);
            System.out.print(Contants.MESSAGE_COLUMN);
        }
        emptyLine();
    }

    private static void printBridge(Row row, int index) {
        if (row.isConnected(index)) {
            System.out.print(Contants.MESSAGE_BRIDGE);
        }
        if (!row.isConnected(index)) {
            System.out.print(Contants.MESSAGE_BLANK);
        }
    }

    public static void printResult(String target, GameResult gameResult) {
        validateTarget(target, gameResult);
        OutputView.printResultMessage();
        if (Contants.MESSAGE_ALL.equals(target)) {
            System.out.println(gameResult.toString());
            return;
        }
        System.out.println(gameResult.of(target));
    }

    private static void validateTarget(String target, GameResult gameResult) {
        if (!gameResult.has(target) && !Contants.MESSAGE_ALL.equals(target)) {
            throw new IllegalArgumentException(Contants.ERROR_NOT_EXIST_MEMBER);
        }
    }

    private static void printResultMessage() {
        System.out.println(NEW_LINE + Contants.MESSAGE_GAME_RESULT);
    }

    private static void emptyLine() {
        System.out.println();
    }
}