/**
 * Monopoly Main Class
 *
 * @author Cody Richter
 * @version 1.0
 */
public class Main {

    private static MonopolyGame game;

    public static void main(String[] args) {
        game = new MonopolyGame();
        game.playGame();
    }

    public static MonopolyGame getGame() {
        return game;
    }
}