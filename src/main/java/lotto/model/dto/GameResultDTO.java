package lotto.model.dto;

import java.util.List;

public class GameResultDTO {
    private int first;
    private int second;
    private int third;
    private int fourth;
    private int fifth;
    private int fk_lotto_round;

    public GameResultDTO(List<Integer> results) {
        first = results.get(0);
        second = results.get(1);
        third = results.get(2);
        fourth = results.get(3);
        fifth = results.get(4);
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getThird() {
        return third;
    }

    public void setThird(int third) {
        this.third = third;
    }

    public int getFourth() {
        return fourth;
    }

    public void setFourth(int fourth) {
        this.fourth = fourth;
    }

    public int getFifth() {
        return fifth;
    }

    public void setFifth(int fifth) {
        this.fifth = fifth;
    }

    public int getFk_lotto_round() {
        return fk_lotto_round;
    }

    public void setFk_lotto_round(int fk_lotto_round) {
        this.fk_lotto_round = fk_lotto_round;
    }
}
