package lotto.model.dto;

import lotto.model.LottoNumber;

public class WinningLottoDTO {
    private int first;
    private int second;
    private int third;
    private int fourth;
    private int fifth;
    private int sixth;
    private int bonus;
    private int fk_lotto_round;

    public WinningLottoDTO(LottoDTO dto, LottoNumber bonusNo) {
        first = dto.getFirst();
        second = dto.getSecond();
        third = dto.getThird();
        fourth = dto.getFourth();
        fifth = dto.getFifth();
        sixth = dto.getSixth();
        bonus = bonusNo.value();
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

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public int getFk_lotto_round() {
        return fk_lotto_round;
    }

    public void setFk_lotto_round(int fk_lotto_round) {
        this.fk_lotto_round = fk_lotto_round;
    }
}
