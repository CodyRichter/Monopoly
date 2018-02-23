import java.util.*;

/**
 * Monopoly Main Class
 *
 * @author Cody Richter
 * @version 1.0
 */
public class Main {
    public static Property[] property = new Property[40];
    public static int round = 1;

    private static String msg = "";
    private static String msg2 = "";
    public static String msg3 = "";

    public static void main(String[] args) throws Exception {
//--------------------------
        final boolean DEBUG = true;
        //--------------------------

        //
        // Before Game Setup
        //
        int numPlayers;
        boolean gameControl = true; //Loop For Playing Multiple Rounds of Game.
        Scanner intScan = new Scanner(System.in);
        Scanner strScan = new Scanner(System.in);
        Scanner charScan = new Scanner(System.in);
        int remainingPlayers; //Players Who Lose Will Be Added to This.

        //Loading Properties
        loadProperties();
        //
        // Initial Game Setup
        //
        while (true) //Sets Up Amount of Players In Game
        {
            System.out.println("Please Enter Number of Players:");
            numPlayers = intScan.nextInt();
            if (numPlayers > 1 && numPlayers < 10) break;
            System.out.println("\fError: You May Have Between Two - Nine Players Total.");
        }
        //----------------------------------------
        Player[] p = new Player[numPlayers];
        Board b = new Board();
        remainingPlayers = numPlayers;
        //----------------------------------------
        for (int i = 0; i < numPlayers; i++) //Allows Players to Set Usernames
        {
            System.out.println("\fPlease Enter Username For Player: " + (i + 1));
            String name = strScan.nextLine();
            p[i] = new Player(name);
        }
        //----------------------------------------
            b.addPlayers(p);
        //----------------------------------------
        while (gameControl) {
            for (int i = 0; i < numPlayers; i++) {
                //Per Player Setup
                if (remainingPlayers == 1) break; //Ends Game if One One Player is Remaining.
                if (p[i].isEliminated() != true) //Ends Game if One One Player is Remaining.
                {
                    int input = -1;
                    int[] dice = new int[3];
                    boolean inputControl = true, playerLoop = true, canDice = true;
                    String message = "";
                    msg = "";
                    msg2 = "";
                    msg3 = "";
                    //
                    if (p[i].isJailed() && (round - p[i].getTurnJailed()) > 3) {
                        message = "You Were Released From Jail After Serving Your Time...";
                        p[i].escapeJail();
                    }
                    updateBoard(p[i], b, message);
                    while (playerLoop) {
                        inputControl = true;
                        while (inputControl) {
                            input = intScan.nextInt();
                            if (input == -1 && DEBUG) //Admin Command to Move Forwards
                            {
                                msg3 = "[Admin] Moved Forward By One Space.";
                                p[i].addPosition(1);
                                checkPosition(p[i]); //Checks Player Position Against Various Criteria
                                updateBoard(p[i], b, msg, msg2, msg3);
                                inputControl = false;
                                msg = "";
                                msg2 = "";
                                msg3 = "";
                            } else if (input == -2 && DEBUG) //Admin Command to Move Backwards
                            {
                                p[i].subtractPosition(1);
                                msg3 = "[Admin] Moved Backward By One Space.";
                                checkPosition(p[i]); //Checks Player Position Against Various Criteria
                                updateBoard(p[i], b, msg, msg2, msg3);
                                inputControl = false;
                                msg = "";
                                msg2 = "";
                                msg3 = "";
                            } else if (input == -3 && DEBUG) //Admin Command to Move Backwards
                            {
                                msg3 = "[Admin] Given: Get Out Of Jail Free Card (1)";
                                p[i].giveJailCard();
                                updateBoard(p[i], b, msg);
                                inputControl = false;
                                msg = "";
                                msg2 = "";
                                msg3 = "";
                            } else if (input == -4 && DEBUG) //Admin Command to Teleport
                            {
                                msg = "[Admin] Please Enter Property To Teleport To:";
                                updateBoard(p[i], b, msg);
                                input = intScan.nextInt();
                                if (input > -1 && input < 40) {
                                    msg = "";
                                    msg2 = "";
                                    p[i].setPosition(input);
                                    checkPosition(p[i]); //Checks Player Position Against Various Criteria
                                    msg3 = "[Admin] Teleported To Property: " + property[p[i].getPosition()].getName();
                                    updateBoard(p[i], b, msg, msg2, msg3);
                                    inputControl = false;
                                }
                            } else if (input == -5 && DEBUG) //Admin Command to Move Backwards
                            {
                                msg = "[Admin] Please Enter Card ID String To Draw:";
                                updateBoard(p[i], b, msg);
                                String cardInp;
                                cardInp = strScan.nextLine();
                                p[i].drawCardDirect(cardInp);
                                checkPosition(p[i]); //Checks Player Position Against Various Criteria
                                msg3 = "[Admin] Drew Card Of Type: " + cardInp;
                                updateBoard(p[i], b, msg, msg2, msg3);
                                msg2 = "";
                                msg3 = "";
                                inputControl = false;
                            } else if (input == -6 && DEBUG) //Admin Command to Move Backwards
                            {
                                p[i].setBalance(0);
                                msg = "";
                                updateBoard(p[i], b, msg);
                                inputControl = false;
                            } else if (input < 0) {
                                message = "Error: Please Enter a Value Between [0] to [5]";
                                updateBoard(p[i], b, message);
                            } else if (input > 5) {
                                message = "Error: Please Enter a Value Between [0] to [5]";
                                updateBoard(p[i], b, message);
                            } else {
                                message = "";
                                updateBoard(p[i], b);
                                inputControl = false;
                            }
                        }
                        //
                        //
                        // Ends Player Turn and Moves On to Next Player
                        //
                        //
                        if (input == 0) //Ends Turn And Moves to Next Player
                        {
                            message = "Turn Over... Moving On To Next Player.";
                            updateBoard(p[i], b, message);
                            Thread.sleep(3000);
                            playerLoop = false;
                        }
                        //
                        //
                        // Allows Player to Roll Dice
                        //
                        //
                        else if (input == 1 && p[i].getBalance() > 0) {
                            dice = useDice(canDice);
                            if (canDice && dice[0] != 0 && dice[1] != 0) //If Player Hasn't Already Rolled
                            {
                                if (!p[i].isJailed()) //Will Be Run If Player Isn't In Jail
                                {
                                    if (dice[2] == 0) {
                                        canDice = false;
                                    }
                                    msg = "You Rolled a " + dice[0] + " and a " + dice[1] + ".";
                                    p[i].addPosition(dice[0] + dice[1]);
                                    msg2 = "";
                                    msg3 = "";
                                    checkPosition(p[i]); //Checks Player Position Against Various Criteria
                                    updateBoard(p[i], b, msg, msg2, msg3);
                                    msg = "";
                                    msg2 = "";
                                    msg3 = "";
                                } else //Will Be Run If Player Is In Jail
                                {
                                    msg = "You Rolled a " + dice[0] + " and a " + dice[1] + ".";
                                    if (dice[0] == dice[1]) {
                                        msg2 = "You Rolled Doubles And Escaped Jail!";
                                        p[i].escapeJail();
                                    } else {
                                        msg2 = "You Didn't Escape Jail, As You Didn't Roll Doubles.";
                                    }
                                    canDice = false;
                                    updateBoard(p[i], b, msg, msg2);

                                }
                            } else //If Player Has Rolled Dice
                            {
                                msg = "You Can't Roll The Dice More Than Once!";
                                updateBoard(p[i], b, msg);
                            }
                        }
                        //
                        //
                        // Displays Properties Owned By Player
                        //
                        //
                        else if (input == 2 && p[i].getBalance() > 0) //Displays Properties Owned By The Player
                        {
                            ArrayList<String> owned = new ArrayList<String>();
                            for (int k = 0; k < 39; k++) {
                                if (property[k].getOwner().equals(p[i].getName())) {
                                    owned.add(property[k].getName());
                                }
                            }
                            msg = "Owned Properties: " + owned;
                            updateBoard(p[i], b, msg);
                        }
                        //
                        //
                        // Allows Player to Interact with Cards
                        //
                        //
                        else if (input == 3 && p[i].getBalance() > 0) //Displays Cards Held By The Player
                        {
                            msg = "Cards Owned: [Get Out Of Jail Free: " + p[i].getGetOutOfJail() + "]";
                            if (p[i].isJailed() && p[i].getGetOutOfJail() > 0) //If User Is In Jail & Has Get Out Of Jail Free Cards
                            {
                                String jailPrompt;
                                boolean jailControl = true;
                                while (jailControl) {
                                    msg2 = "Use A Get Out Of Jail Free Card? (Yes/No)";
                                    updateBoard(p[i], b, msg, msg2, msg3);
                                    msg3 = "";
                                    jailPrompt = strScan.nextLine();
                                    if (jailPrompt.equalsIgnoreCase("yes")) {
                                        msg = "You Were Released From Jail After Showing The Guards Your Get Out Of Jail Free Card!";
                                        p[i].escapeJail();
                                        updateBoard(p[i], b, msg);
                                        jailControl = false;
                                    } else if (jailPrompt.equalsIgnoreCase("no")) {
                                        msg = "You Were Released From Jail After Showing The Guards Your Get Out Of Jail Free Card!";
                                        p[i].escapeJail();
                                        updateBoard(p[i], b, msg);
                                        jailControl = false;
                                    } else {
                                        msg3 = "Error! Invalid Input.";
                                    }
                                }
                            } else //Shows Player Cards
                                updateBoard(p[i], b, msg);
                        }
                        //
                        //
                        // Purchase of Properties
                        //
                        //
                        else if (input == 4 && !p[i].isJailed() && p[i].getBalance() > 0) //Allows Player to Purchase Properties
                        {
                            boolean buyControl = true;
                            while (buyControl) {
                                msg = "[Continue Game: 0] | [Purchase Property: 1] | [Construct Building: 2]";
                                updateBoard(p[i], b, msg);
                                int ans = intScan.nextInt();
                                if (ans == 0) {
                                    buyControl = false;
                                    msg = "";
                                }
                                //
                                // Allows Player to Purchase Properties
                                //
                                else if (ans == 1) {
                                    if (property[p[i].getPosition()].getPrice() > 0 && p[i].getBalance() > property[p[i].getPosition()].getPrice() && property[p[i].getPosition()].getOwner().equalsIgnoreCase("Not Owned")) {
                                        property[p[i].getPosition()].setOwner(p[i]);
                                        p[i].subtractBalance(property[p[i].getPosition()].getPrice());
                                        msg = "You Purchased The Property: " + property[p[i].getPosition()].getName() + "!";
                                    } else {
                                        msg = "Error! Unable To Purchase Property: " + property[p[i].getPosition()].getName() + "!";
                                    }
                                    buyControl = false;
                                }
                                //
                                // Allows Player to Purchase Buildings
                                //
                                else if (ans == 2) {
                                    if (property[p[i].getPosition()].getOwner().equals(p[i].getName())) //Allows Player To Purchase Buildings
                                    {
                                        int amount = 0;
                                        String id = property[p[i].getPosition()].getID();
                                        boolean canBuyHouse = false;
                                        for (Property prop : property) //Tests If Player Owns All Properties With ID Tag
                                        {
                                            if (prop.getID().equals(id) && prop.getOwner().equals(p[i].getName())) {
                                                amount++;
                                            }
                                        }
                                        if (id.equals("brown") && amount == (2)) {
                                            canBuyHouse = true;
                                        } else if (id.equals("lightBlue") && amount == (3)) {
                                            canBuyHouse = true;
                                        } else if (id.equals("purple") && amount == (3)) {
                                            canBuyHouse = true;
                                        } else if (id.equals("orange") && amount == (3)) {
                                            canBuyHouse = true;
                                        } else if (id.equals("yellow") && amount == (3)) {
                                            canBuyHouse = true;
                                        } else if (id.equals("red") && amount == (3)) {
                                            canBuyHouse = true;
                                        } else if (id.equals("green") && amount == (3)) {
                                            canBuyHouse = true;
                                        } else if (id.equals("blue") && amount == (2)) {
                                            canBuyHouse = true;
                                        } else if (id.equals("railroad") && amount == (2)) {
                                            msg = "You May Not Build Homes on a Railroad!";
                                        } else if (id.equals("utility") && amount == (2)) {
                                            msg = "You May Not Build Homes on a Utility!";
                                        } else {
                                            msg = "You Must Own All Of The Properties In a Group to Construct Buildings!";
                                        }

                                        int housesOnProp = property[p[i].getPosition()].getNumBuildings();
                                        if (canBuyHouse && p[i].getBalance() > 200 && housesOnProp <= 4) //Will Run if Player Can Purchase House By Owning All Homes In a Color Group
                                        {
                                            property[p[i].getPosition()].addBuilding();
                                            p[i].subtractBalance(200);
                                            msg = "You Built a " + property[p[i].getPosition()].getBuildings() + " on " + property[p[i].getPosition()].getName() + ".";
                                        } else if (p[i].getBalance() > 200 && canBuyHouse && housesOnProp <= 4) {
                                            msg = "You Don't Have Enough $$ To Build a House!";
                                        } else if (canBuyHouse && housesOnProp <= 4) {
                                            msg = "You Must Own All Properties In a Group to Build on Them!";
                                        } else if (housesOnProp > 4) {
                                            msg = "There Is No More Space To Build In This Property!";
                                        }
                                        buyControl = false;
                                    }
                                } else
                                    msg = "Error! Please Enter a Valid Number!";
                                updateBoard(p[i], b, msg);
                            }
                        }
                        //
                        //
                        //Displays Property Info
                        //
                        //
                        else if (input == 5 && p[i].getBalance() > 0) {
                            msg = "Property Info: \n" +
                                    "| " + "Property Owner: " + property[p[i].getPosition()].getOwner() + "\n" +
                                    "| " + "Rent: " + property[p[i].getPosition()].getRent() + "\n" +
                                    "| " + "Houses Built: " + property[p[i].getPosition()].getHouses() + "\n" +
                                    "| " + "Hotels Built: " + property[p[i].getPosition()].getHotels();
                            updateBoard(p[i], b, msg);
                        } else if (p[i].getBalance() <= 0) {
                            msg = "Error! Unable To Complete Action: You Have Lost The Game By Running Out of Money!";
                            updateBoard(p[i], b, msg);
                        }
                        if (p[i].getBalance() <= 0) //Removes Player From Game When They Run out of $$
                        {
                            remainingPlayers--;
                            playerLoop = false;
                            p[i].eliminate();

                            for (int num = 0; num < 40; num++) //Sells Properties Owned By Removed Player
                            {
                                if (property[num].getPlayerOwner() != null && property[num].getPlayerOwner().equals(p[i])) {
                                    property[num].removeOwner();
                                }
                            }

                        }
                    }
                }
                if (remainingPlayers == 1) break; //Ends Game if One One Player is Remaining.
            }
            if (remainingPlayers == 1) //What To Do When Game Is Over
            {
                gameControl = false;
                System.out.println("\f" +
                        "|------------------------------------------------------" + "\n" +
                        "| " + "-------------------==[Game Over]==-------------------\n" +
                        "| " + "Winner: " + getWinner(p) + "\n" +
                        "|------------------------------------------------------");
                System.out.println(b.getBoard());
            } else //Do This At End of Each Round
            {
                round++; //Adds Round To Total Count After All Players Have Had Their Turn
            }
        }
    }

    /**
     * Updates Board With Current Values For Player
     */
    private static void updateBoard(Player p, Board b) {
        for (int i = 0; i < 40; i++)
        {
            System.out.print("\n");
        }
        System.out.println("\f" +
                "| " + "Player: " + p.getName() + " (Round " + round + ")\n" +
                "| " + "Balance: " + p.getBalance() + "\n" +
                "|------------------------------------------------------");
        System.out.println(b.getBoard());
        System.out.println("" +
                "|------------------------------------------------------" + "\n" +
                "| " + "Currently On Property: " + property[p.getPosition()].getName() + " (Group: " + property[p.getPosition()].getID() + ")\n" +
                "| " + "Owned By: " + property[p.getPosition()].getOwner());
        if (property[p.getPosition()].getOwner().equalsIgnoreCase("Not Owned") && property[p.getPosition()].getPrice() > 0) { //Displays Purchase Price Information
            System.out.println("" + "| " + "Purchase Price: " + property[p.getPosition()].getPrice());
        } else {
            System.out.println("" + "| " + "Purchase Price: **Not For Sale**");
        }
        System.out.println("" +
                "|------------------------------------------------------");
        System.out.println("\n" +
                "| " + "[End Turn: 0] | [Roll Dice: 1] | [Owned Properties: 2] \n" +
                "| " + "[Use Cards: 3] | [Purchase: 4] | [Property Info: 5]" + "\n" +
                "|------------------------------------------------------");
    }

    private static void updateBoard(Player p, Board b, String message1) {
        updateBoard(p, b);
        System.out.println("" +
                "| " + "Messages:" + "\n" +
                "| " + message1 + "\n" +
                "|------------------------------------------------------");
    }

    private static void updateBoard(Player p, Board b, String message1, String message2) {
        updateBoard(p, b, message1);
        System.out.println("" +
                "| " + message2 + "\n" +
                "|------------------------------------------------------");
    }

    private static void updateBoard(Player p, Board b, String message1, String message2, String message3) {
        updateBoard(p, b, message1, message2);
        System.out.println("" +
                "| " + message3 + "\n" +
                "|------------------------------------------------------");
    }

    /**
     * Rolls Dice For Player
     */
    private static int[] useDice(boolean success) {
        int[] val = new int[3];
        if (!success) {
            val[0] = 0;
            val[1] = 0;
            val[2] = 0;
            return val;
        }
        Dice d1 = new Dice();
        Dice d2 = new Dice();
        d1.roll();
        d2.roll();
        val[0] = d1.getFaceValue();
        val[1] = d2.getFaceValue();
        val[2] = 0;
        if (val[0] == val[1]) {
            val[2] = 1;
        }
        return val;
    }

    /**
     * Checks Player Position Against Various Criteria
     */
    private static void checkPosition(Player p) {
        //In-Game Modifiers
        if (property[p.getPosition()].getID().equals("tax")) {
            p.subtractBalance(100);
            msg2 = "You Paid $100 In Taxes!";
        } else if (property[p.getPosition()].getID().equals("chance")) {
            p.drawCard("chance");
            msg2 = "You Drew A Chance Card!";
            msg3 = "Card: " + p.getCardType();
        } else if (property[p.getPosition()].getID().equals("communityChest")) {
            p.drawCard("communityChest");
            msg2 = "You Drew A Community Chest Card!";
            msg3 = "Card: " + p.getCardType();
        } else if (property[p.getPosition()].getID().equals("goToJail")) {
            p.setPosition(10);
            p.sendToJail();
            msg2 = "You Were Arrested! Do Not Pass Go. Do Not Collect $200.";
        }

        //
        // Checking If Property Is Owned.
        //
        if (property[p.getPosition()].getOwner().toLowerCase().contains("not owned") || property[p.getPosition()].getOwner().equals(p.getName())) {
        } //Paying $$ To Property Owner
        else {
            msg2 = "You Paid $" + property[p.getPosition()].getRent() + " to " + property[p.getPosition()].getOwner() + ".";
            p.subtractBalance(property[p.getPosition()].getRent()); //Takes $$ From Player Who Landed on Tile
            property[p.getPosition()].getPlayerOwner().addBalance(property[p.getPosition()].getRent()); //Pays Rent $$ To Property Owner
        }
    }

    /**
     * Returns Winner of Game
     */
    private static String getWinner(Player[] p) {
        String winner = "[Error] No Player Has Won.";
        for (int i = 0; i < p.length; i++) {
            if (!p[i].isEliminated()) {
                winner = p[i].getName();
            }
        }
        return winner;
    }

    /**
     * Loads All Property Properties Into Game
     */
    private static void loadProperties() {
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