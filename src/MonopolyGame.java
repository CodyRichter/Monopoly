import java.util.ArrayList;
import java.util.Scanner;

/**
 * Monopoly - PACKAGE_NAME
 *
 * @author MonopolyGame
 * @version 1.0
 */
public class MonopolyGame extends MonopolyInterface {

    public int round = 1;

    private String msg = "";
    private String msg2 = "";
    public String msg3 = "";

    private Scanner intScan = new Scanner(System.in);
    private Scanner strScan = new Scanner(System.in);

    private int numPlayers;
    private int remainingPlayers;

    private Player[] p;
    private Board b;

    public MonopolyGame() {

    }


    public void playGame() {
        //--------------------------
        final boolean DEBUG = false; //Debug Mode. Allows For Special Cheaty Commands To Bypass Gameplay Elements. Turn Off Before Actually Playing
        //--------------------------

        boolean gameControl = true; //Loop For Playing Multiple Rounds of Game.

        loadProperties(); //Loading Property Values Into Array.

        preGameSetup(); //Setup Number of Players and Populate Player Array

        while (gameControl) {
            for (int i = 0; i < numPlayers; i++) {
                //Per Player Setup
                if (remainingPlayers == 1) break; //Ends Game if One One Player is Remaining.
                if (!p[i].isEliminated()) //Ends Game if One One Player is Remaining.
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
                            try {
                                Thread.sleep(3000);
                            } catch (Exception ignored) {}
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
                                    owned.add(property[k].getName() + " #:" + k);
                                }
                            }
                            msg = "Owned Properties: " + owned;
                            msg2 = "[Continue: 0] | [Initiate a Trade: 1] | [Confirm a Trade: 2]";

                            int propertyPrompt = 0;
                            boolean propertyControl = true;
                            while (propertyControl) {
                                updateBoard(p[i], b, msg, msg2);
                                propertyPrompt = intScan.nextInt();

                                if (propertyPrompt < 0 || propertyPrompt > 2) {
                                    msg = "Invalid Input! Enter a Number From 0 -> 2";
                                    continue;
                                }
                                propertyControl = false;
                            }

                            if (propertyPrompt == 0) {
                                //Continue Game
                                msg = "";
                                break;
                            }
                            //
                            // Initiate a Trade
                            //
                            if (propertyPrompt == 1) {
                                // TODO: Display Valid Player To Trade To
                                // TODO: Offer Player Properties To Trade
                            }
                            //
                            // Confirm a Trade
                            //
                            else if (propertyPrompt == 2) {
                                if (p[i].getTradeRequest() == null) {
                                    msg = "You Do Not Have Any Pending Trade Requests.";
                                } else {
                                    // TODO: Respond To a Trade Request

                                    //
                                    // What To Do When A Player Is Responding To a Trade Request
                                    //

                                }
                            }


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
     * Sets Up Number Of Players and Player Names Before Game Begins.
     * This Must Be Run at the Start Of a Game to Avoid NullPointerExceptions.
     */
    protected void preGameSetup() {
        boolean lc = true;
        while (lc) //Sets Up Amount of Players In Game
        {
            System.out.println("Please Enter Number of Players:");
            try {
                numPlayers = intScan.nextInt();
                if (numPlayers > 1 && numPlayers < 10) lc = false;
            } catch (Exception e) {
                System.out.println("\fError: You May Have Between Two -> Nine Players Total.");
            }
        }

        p = new Player[numPlayers];
        b = new Board();
        remainingPlayers = numPlayers;

        for (int i = 0; i < numPlayers; i++) //Allows Players to Set Usernames
        {
            System.out.println("\fPlease Enter Username For Player: " + (i + 1));
            String name = strScan.nextLine();
            p[i] = new Player(name);
        }
        b.addPlayers(p); //Add All Players To Board
    }

    protected void adminCommands() { //Former Option -1

    }

    protected void endTurn() { //Former Option 0

    }

    protected void rollDice() { //Former Option 1

    }

    protected void displayOwnedProperties() { //Former Option 2

    }

    protected void useCards() { //Former Option 3

    }

    protected void purchaseProperty() {

    }

    protected void propertyInfo() {

    }




    /**
     * Updates Board With Current Values For Player
     */
    private void updateBoard(Player p, Board b) {
        for (int i = 0; i < 40; i++) {
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

    private void updateBoard(Player p, Board b, String message1) {
        updateBoard(p, b);
        System.out.println("" +
                "| " + "Messages:" + "\n" +
                "| " + message1 + "\n" +
                "|------------------------------------------------------");
    }

    private void updateBoard(Player p, Board b, String message1, String message2) {
        updateBoard(p, b, message1);
        System.out.println("" +
                "| " + message2 + "\n" +
                "|------------------------------------------------------");
    }

    private void updateBoard(Player p, Board b, String message1, String message2, String message3) {
        updateBoard(p, b, message1, message2);
        System.out.println("" +
                "| " + message3 + "\n" +
                "|------------------------------------------------------");
    }

    /**
     * Rolls Dice For Player, And Returns Result Of Both Rolls, As Well As If a Double is Rolled.
     * @param success Whether Dice Are Allowed to be Rolled. If success==false, Method Will Return Empty Array.
     * @return Array Containing Dice Rolls. If Rolls Are The Same, The 2nd Index Will be 1.
     */
    private int[] useDice(boolean success) {
        int[] val = new int[3];
        if (!success) { //Returns Empty Array If Not Successful
            return val;
        }

        Dice d1 = new Dice();
        for (int i = 0; i < 2; i++) { //Rolls Dice Twice And Stores Value In Array.
            d1.roll();
            val[i] = d1.getFaceValue();
        }

        if (val[0] == val[1]) { //If Doubles Have Been Rolled, Mark Accordingly In Return Array.
            val[2] = 1; //1 In Last Index Marks That First Two Results Are The Same
        }

        return val;
    }

    /**
     * Checks a Position On Board For All Changes That Must Be Made From Landing at a Position
     * @param p Player to check position of
     */
    private void checkPosition(Player p) {
        if (!checkSpecialPropertiesOnPlayer(p)) { //Checks If Player Is On Special Property
            //If Player Isn't On Special Property, Check If Property Is Owned By Another Player for Rent Payment
            checkPropertyPayment(p);
        }
    }

    /**
     * Checks Whether The Property a Player Is On Contains a Special Function, and Executes The Function if it Exists.
     * @param p Player To Check Property
     * @return Whether The Player Has Landed on a Special Property
     */
    private boolean checkSpecialPropertiesOnPlayer(Player p) {
        if (property[p.getPosition()].getID().equals("tax")) {
            p.subtractBalance(100);
            msg2 = "You Paid $100 In Taxes!";
            return true;
        } else if (property[p.getPosition()].getID().equals("chance")) {
            p.drawCard("chance");
            msg2 = "You Drew A Chance Card!";
            msg3 = "Card: " + p.getCardType();
            return true;
        } else if (property[p.getPosition()].getID().equals("communityChest")) {
            p.drawCard("communityChest");
            msg2 = "You Drew A Community Chest Card!";
            msg3 = "Card: " + p.getCardType();
            return true;
        } else if (property[p.getPosition()].getID().equals("goToJail")) {
            p.setPosition(10);
            p.sendToJail();
            msg2 = "You Were Arrested! Do Not Pass Go. Do Not Collect $200.";
            return true;
        }
        return false;
    }

    /**
     * Checks Property Player Is On For Rent Payments.
     * @param p Player To Check Property
     * @return Whether Player Has Paid Rent For a Property
     */
    private boolean checkPropertyPayment(Player p) {
        if (!property[p.getPosition()].getOwner().toLowerCase().contains("not owned") && !property[p.getPosition()].getOwner().equals(p.getName())) {
            msg2 = "You Paid $" + property[p.getPosition()].getRent() + " to " + property[p.getPosition()].getOwner() + ".";
            p.subtractBalance(property[p.getPosition()].getRent()); //Takes $$ From Player Who Landed on Tile
            property[p.getPosition()].getPlayerOwner().addBalance(property[p.getPosition()].getRent()); //Pays Rent $$ To Property Owner
            return true; //Player Has Paid Rent
        }
        return false; //Nothing Has Updated
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
}
