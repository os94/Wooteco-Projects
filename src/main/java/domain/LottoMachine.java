package domain;

public class LottoMachine {
    private static final String EXIT_MESSAGE_LACK_OF_MONEY = "금액이 부족합니다. 게임을 종료합니다.";
    private static final int PRICE_OF_LOTTO = 1000;
    private User user = new User();
    private int inputMoney;
    private WinningLotto winningLotto;

    public void run() {
        setInputMoney();

        buyLotto(getNumberOfLotto(inputMoney));

        setWinningLotto();
    }

    private void setInputMoney() {
        this.inputMoney = user.inputMoney();
    }

    private void setWinningLotto() {
        this.winningLotto = new WinningLotto(user.inputWinningLotto(), user.inputBonusNo());
    }

    private int getNumberOfLotto(int inputMoney) {
        if (inputMoney / PRICE_OF_LOTTO == 0) {
            System.out.println(EXIT_MESSAGE_LACK_OF_MONEY);
            System.exit(-1);
        }
        return inputMoney / PRICE_OF_LOTTO;
    }

    private void buyLotto(int numberOfLotto) {

    }
}
