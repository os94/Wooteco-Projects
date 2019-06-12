package lotto.model.dto;

import lotto.model.LottoNumber;

import java.util.List;

public class LottoDTO {
    private int first;
    private int second;
    private int third;
    private int fourth;
    private int fifth;
    private int sixth;
    private int fk_lotto_round;

    public LottoDTO(List<LottoNumber> lottoNumbers) {
        first = lottoNumbers.get(0).value();
        second = lottoNumbers.get(1).value();
        third = lottoNumbers.get(2).value();
        fourth = lottoNumbers.get(3).value();
        fifth = lottoNumbers.get(4).value();
        sixth = lottoNumbers.get(5).value();
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

    public int getSixth() {
        return sixth;
    }

    public void setSixth(int sixth) {
        this.sixth = sixth;
    }

    public int getFk_lotto_round() {
        return fk_lotto_round;
    }

    public void setFk_lotto_round(int fk_lotto_round) {
        this.fk_lotto_round = fk_lotto_round;
    }
}
