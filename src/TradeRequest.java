public class TradeRequest {

    private Player fromPlayer;
    private Player toPlayer;

    private Property desiredProperty;
    private Property givenProperty;
    private int money = 0;

    private boolean isCompleted = false;

    public TradeRequest(Property desiredProperty, int price, Player fromPlayer ,Player toPlayer) {
        this.desiredProperty = desiredProperty;

        if (price > 0) {
            this.money = price;
        }
        this.toPlayer = toPlayer;
        this.fromPlayer = fromPlayer;
    }

    public TradeRequest(Property desiredProperty, Property givenProperty, Player fromPlayer ,Player toPlayer) {
        this.desiredProperty = desiredProperty;
        this.givenProperty = givenProperty;

        this.toPlayer = toPlayer;
        this.fromPlayer = fromPlayer;
    }

    public TradeRequest(Property desiredProperty, Property givenProperty, int price, Player fromPlayer ,Player toPlayer) {
        this.desiredProperty = desiredProperty;
        this.givenProperty = givenProperty;
        if (price > 0) {
            this.money = price;
        }

        this.toPlayer = toPlayer;
        this.fromPlayer = fromPlayer;
    }

    /**
     * Checks If The Configured Trade Is Valid With Both Parties. Will Check:
     * If Desired/Given Properties Have The Correct Owners
     * If Player Has Enough Money To Accept Trade
     * @return
     */
    public boolean isValidTrade() {
        if (givenProperty == null || givenProperty.getPlayerOwner().equals(fromPlayer)) { //Player Owns Given Property
            if (desiredProperty.getPlayerOwner().equals(toPlayer)){ //Other Player Owns Desired Property
                if (money < 0 || fromPlayer.getBalance() > money){ //Player Has Enough Money For Trade
                    if (toPlayer.getTradeRequest() == null) //Other Player Isn't Currently Trading Right Now
                        if (!isCompleted) { //Trade Has Not Been Completed Yet
                            return true;
                        }
                }
            }
        }

        return false;
    }

    public void doTrade() {
        desiredProperty.setOwner(fromPlayer);

        if (givenProperty != null) {
            givenProperty.setOwner(toPlayer);
        }

        if (money > 0) {
            fromPlayer.subtractBalance(money);
        }
        isCompleted = true;
    }

    public Player getFromPlayer(){
        return fromPlayer;
    }

    public Player getToPlayer(){
        return toPlayer;
    }

    public Property getDesiredProperty(){
        return desiredProperty;
    }

    public Property getGivenProperty(){
        return givenProperty;
    }

    public int getMoney() {
        return money;
    }

}
