package model;

import lotto.model.Money;
import lotto.model.Positive;
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
    void getCountOfLotto() {
        assertThat(money.getCountOfLotto()).isEqualTo(new Positive(10));
    }

    @Test
    void lack_of_money() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Money(900).getCountOfLotto();
        });
    }

    @Test
    void getRateOfProfit() {
        assertThat(money.getRateOfProfit(10_000)).isEqualTo(100);
    }

    @AfterEach
    void tearDown() {
        money = null;
    }
}
