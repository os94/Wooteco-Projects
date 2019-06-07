package model;

import lotto.model.Money;
import lotto.model.PositiveNumber;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoneyTest {
    Money money;

    @BeforeEach
    void setUp() {
        money = new Money(10_000);
    }

    @Test
    void 구매가능한_로또개수_계산() {
        assertThat(money.countOfLotto()).isEqualTo(new PositiveNumber(10));
    }

    @Test
    void 금액이_부족한_경우_예외처리() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Money(900).countOfLotto();
        });
    }

    @Test
    void 수익률_계산() {
        assertThat(money.rateOfProfit(10_000)).isEqualTo(100);
    }

    @AfterEach
    void tearDown() {
        money = null;
    }
}
