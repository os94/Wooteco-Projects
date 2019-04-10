package domain;

public class LottoMachine {
    private User user = new User();
    private int inputMoney;
    private WinningLotto winningLotto;

    public void run() {
        setInputMoney();

        setWinningLotto();
    }

    private void setInputMoney() {
        this.inputMoney = user.inputMoney();
    }

    private void setWinningLotto() {
        this.winningLotto = new WinningLotto(user.inputWinningLotto(), user.inputBonusNo());
    }
}
