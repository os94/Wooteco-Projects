package ladderGame.view;

import ladderGame.domain.Ladder;
import ladderGame.domain.Line;
import ladderGame.domain.Member;
import ladderGame.domain.MemberGroup;

public class OutputView {
    public static void print(MemberGroup memberGroup, Ladder ladder) {
        printMember(memberGroup);
        System.out.println();
        printLadder(memberGroup, ladder);
    }

    private static void printMember(MemberGroup memberGroup) {
        for (Member member : memberGroup.getMembers()) {
            System.out.printf("%6s", member.getName());
        }
    }

    private static void printLadder(MemberGroup memberGroup, Ladder ladder) {
        for (Line line : ladder.getLines()) {
            System.out.print("     |");
            printLine(memberGroup, line);
            System.out.println();
        }
    }

    private static void printLine(MemberGroup memberGroup, Line line) {
        for (int i = 0; i < memberGroup.getCountOfPerson() - 1; i++) {
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