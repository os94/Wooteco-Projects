package ladderGame.controller;

import ladderGame.domain.Ladder;
import ladderGame.domain.Member;
import ladderGame.domain.MemberGroup;
import ladderGame.view.InputView;

import java.util.ArrayList;
import java.util.List;

public class LadderGame {
    public void run() {
        String[] names = InputView.inputName().split(",");
        int height = InputView.inputHeight();
        MemberGroup memberGroup = new MemberGroup(makeMembers(names));

        Ladder ladder = new Ladder(memberGroup.getCountOfPerson(), height);
        ladder.connectLine();
    }

    private List<Member> makeMembers(String[] names) {
        List<Member> memberGroup = new ArrayList<>();

        for (String name : names) {
            memberGroup.add(new Member(name));
        }

        return memberGroup;
    }
}
