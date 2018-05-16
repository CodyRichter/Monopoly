/**
 * Monopoly - PACKAGE_NAME
 *
 * @author MonopolyInterface
 * @version 1.0
 */
public abstract class MonopolyInterface {

    public static Property[] property = new Property[40]; //Holds All Property Values In Monopoly Game

    protected int round;
    protected int numPlayers;
    protected int remainingPlayers;

    /**
     * Method To Run Monopoly Game and Check All Valid User Inputs
     */
    abstract void playGame();

    /**
     * Initialize All Variables and Configure Number of Players Before Starting to Play Game.
     */
    abstract void preGameSetup();

    /**
     * Monopoly Game User Option: Allow For Admin Commands To Be Inputted When In Debug Mode.
     */
    abstract void adminCommands();

    /**
     * Monopoly Game User Option: End Turn and Move to Next Player
     */
    abstract void endTurn();

    /**
     * Monopoly Game User Option: Roll Dice To Change Position.
     */
    abstract void rollDice();

    /**
     * Monopoly Game User Option: Display List of Every Owned Property To User
     */
    abstract void displayOwnedProperties();

    /**
     * Monopoly Game User Option: Show User Currently Owned Cards, and Allow User To Use Them.
     */
    abstract void useCards();

    /**
     * Monopoly Game User Option: Purchase Property That Player Is On.
     * Additionally, Allows For Construction of Buildings on Player Owned Properties
     */
    abstract void purchaseProperty();

    /**
     * Monopoly Game User Option: Show Info On Property Player Is On
     */
    abstract void propertyInfo();


    public int getRound() {
        return round;
    }

    /**
     * Loads All Properties Into The Game and Stores Their Values In Property Array.
     * This Method Should Only Be Called Once During Startup.
     */
    protected static void loadProperties() {
        //Special Locations (Location, Display Name, Price, Rent, ID)
        property[0] = new Property(0, "Go", "go");
        property[10] = new Property(10, "Jail", "jail");
        property[20] = new Property(20, "Free Parking", "freeParking");
        property[30] = new Property(30, "Go To Jail", "goToJail");
        //First Road
        property[1] = new Property(1, "Mediterranean Ave.", 60, 2, "brown");
        property[2] = new Property(2, "** Community Chest **", "communityChest");
        property[3] = new Property(3, "Baltic Ave.", 60, 4, "brown");
        property[4] = new Property(4, "Income Tax", -100, "tax");
        property[5] = new Property(5, "Reading Railroad", 200, "railroad");
        property[6] = new Property(6, "Oriental Ave.", 100, 6, "lightBlue");
        property[7] = new Property(7, "?? Chance ??", "chance");
        property[8] = new Property(8, "Vermont Ave.", 100, 6, "lightBlue");
        property[9] = new Property(9, "Connecticut Ave.", 120, 8, "lightBlue");
        //Second Road
        property[11] = new Property(11, "St Charles Place", 140, 10, "purple");
        property[12] = new Property(12, "Electric Company", 150, "utility");
        property[13] = new Property(13, "States Ave.", 140, 10, "purple");
        property[14] = new Property(14, "Virginia Ave.", 160, 12, "purple");
        property[15] = new Property(15, "Pennsylvania Railroad", 200, "railroad");
        property[16] = new Property(16, "St. James Place", 180, 14, "orange");
        property[17] = new Property(17, "** Community Chest **", "communityChest");
        property[18] = new Property(18, "Tennessee Ave.", 180, 14, "orange");
        property[19] = new Property(19, "New York Ave.", 200, 16, "orange");
        //Third Road
        property[21] = new Property(21, "Kentucky Ave.", 220, 18, "red");
        property[22] = new Property(22, "?? Chance ??", "chance");
        property[23] = new Property(23, "Indiana Ave.", 220, 18, "red");
        property[24] = new Property(24, "Illinois Ave.", 240, 20, "red");
        property[25] = new Property(25, "B. & O. Railroad", 200, "railroad");
        property[26] = new Property(26, "Atlantic Ave.", 260, 22, "yellow");
        property[27] = new Property(27, "Ventnor Ave.", 260, 22, "yellow");
        property[28] = new Property(28, "Water Works", 150, "utility");
        property[29] = new Property(29, "Marvin Gardens", 280, 24, "yellow");
        //Fourth Road
        property[31] = new Property(31, "Pacific Ave.", 300, 26, "green");
        property[32] = new Property(32, "North Carolina Ave.", 300, 26, "green");
        property[33] = new Property(33, "** Community Chest **", "communityChest");
        property[34] = new Property(34, "Pennsylvania Ave.", 320, 28, "green");
        property[35] = new Property(35, "Short Line", 200, "railroad");
        property[36] = new Property(36, "?? Chance ??", "chance");
        property[37] = new Property(37, "Park Place", 350, 35, "blue");
        property[38] = new Property(38, "** Lurury Tax **", -100, "tax");
        property[39] = new Property(39, "Boardwalk", 400, 50, "blue");
    }
}
