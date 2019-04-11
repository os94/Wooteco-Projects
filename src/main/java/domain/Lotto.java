/*
 * @class       Lotto class
 * @version     1.0.0
 * @date        19.04.11
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       each Lotto class.
 */

package domain;

import java.util.List;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public boolean hasNumber(int number) {
        return numbers.contains(number);
    }

    public void printLotto() {
        System.out.println(numbers);
    }
}
