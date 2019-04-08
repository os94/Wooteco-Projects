/*
 * @class       Random class
 * @version     1.0.0
 * @date        19.04.04
 * @author      OHSANG SEO (tjdhtkd@gmail.com)
 * @brief       just generate random number.
 */

package domain;

public class Random {
    private int randomNumber;
    private static final int GENERATING_RANDOM_NUMBER_STANDARDS = 10;

    public Random() {
        this.randomNumber = (int) (Math.random() * GENERATING_RANDOM_NUMBER_STANDARDS);
    }

    public int getRandomNumber() {
        return randomNumber;
    }
}
