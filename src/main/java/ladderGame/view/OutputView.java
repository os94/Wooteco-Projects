package ladderGame.view;

import ladderGame.domain.*;

import java.util.List;

public class OutputView {
    public static void print(GameData gameData, Ladder ladder) {
        printMember(gameData);
        System.out.println();
        printLadder(gameData, ladder);
        printResults(gameData);
    }

    private static void printMember(GameData gameData) {
        for (Member member : gameData.getMembers()) {
            System.out.printf("%6s", member.getName());
        }
    }

    private static void printResults(GameData gameData) {
        for (Goal goal : gameData.getGoals()) {
            System.out.printf("%6s", goal.getPlayResult());
        }
    }

    private static void printLadder(GameData gameData, Ladder ladder) {
        for (Line line : ladder.getLines()) {
            System.out.print("     |");
            printLine(gameData.getMembers(), line);
            System.out.println();
        }
    }

    private static void printLine(List<Member> members, Line line) {
        for (int i = 0; i < members.size() - 1; i++) {
            printPointLine(line, i);
            System.out.print("|");
        }
    }

    private static void printPointLine(Line line, int i) {
        if (line.isConnected(i)) {
            System.out.print("-----");
        }
        if (!line.isConnected(i)) {
            System.out.print("     ");
        }
    }
}