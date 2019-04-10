package domain;

public class LottoMachine {
    private User user = new User();
    private int inputMoney;

    public void run() {
        setInputMoney();
    }

    private void setInputMoney() {
        this.inputMoney = user.inputMoney();
    }
}
