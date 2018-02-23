
/**
 * Card Object
 *
 * @author Cody Richter
 * @version 1.0
 */
public class Card
{
    private String type = "[No Card Selected]";
    private int deck = 0;
    private String disp;

    /**
     * Constructor for objects of class Card
     */
    public Card(String deckType)
    {
        if (deckType.equalsIgnoreCase("chance"))
            deck = 1;
        if (deckType.equalsIgnoreCase("communityChest"))
            deck = 2;
        drawCard();
    }

    /**
     * Constructor For Specific Card Type
     */
    public Card(String cardType, int adminMode)
    {
        if (adminMode == 1)
        {
            type = cardType;
            disp = "[Admin] Card Drawn Of Type: " + cardType;
        }
    }

    /**
     * Returns Type of Card
     */
    public String getType()
    {
        return type;
    }

    /**
     * Returns Type of Card
     */
    public String toString()
    {
        return disp;
    }

    /**
     * Determines What Type of Card is Drawn
     */
    private void drawCard()
    {
        if (deck == 1) //Draws Chance Card
        {
            int random = (int)((Math.random()*16)+1);
            if (random == 1){
                type = "get150"; //Gets $150
                disp = "You Were Given $150!";
            }
            else if (random == 2){
                type = "get100"; //Gets $100
                disp = "You Were Given $100!";
            }
            else if (random == 3){
                type = "get50"; //Gets $50
                disp = "You Were Given $50!";
            }
            else if (random == 4){
                type = "goToBoardwalk"; //Moves Player to Boardwalk
                disp = "You Were Given a Free Ride to Boardwalk!";
            }
            else if (random == 5){
                type = "goToReadingRailroad"; //Moves Player to Reading Railroad
                disp = "You Were Given a Free Ride to the Reading Railroad!";
            }
            else if (random == 6){
                type = "pay15"; //Pays $15 Tax
                disp = "You Paid a $15 Tax.";
            }
            else if (random == 7){
                type = "repairHouse"; //Pay $40/House, $115/Hotel
                disp = "You Paid $40 For Each House You Owned and $115 For Each Hotel!";
            }
            else if (random == 8){
                type = "goDirectlyToJail"; //Go To Jail, Don't Pass Go, Don't Collect $200
                disp = "You Were Arrested! Go Directly To Jail. Do Not Pass Go. Do Not Collect $200.";
            }
            else if (random == 9){
                type = "goBack3"; //Moves Player Back 3 Spaces
                disp = "You Were Moved Back 3 Spaces.";
            }
            else if (random == 10){
                type = "getOutOfJail"; //Get Out Of Jail Free!
                disp = "You Got a Get Out Of Jail Free Card!";
            }
            else if (random == 11){
                type = "pay50"; //Pays $50 tax
                disp = "You Paid a $50 Tax.";
            }
            else if (random == 12){
                type = "goToNextRailroad"; //Moves Player to Next Railroad, If Owned, Pay Double
                disp = "You Were Given a Free Ride to the Next Railroad!";
            }
            else if (random == 13){
                type = "goToNextUtility"; //Moves Player to Next Utility, If Owned, Pay Double
                disp = "You Were Given a Free Ride to the Next Utility!";
            }
            else if (random == 14){
                type = "goToStCharlesPlace"; //Moves Player to St. Charles Place
                disp = "You Were Given a Free Ride to St. Charles Place!";
            }
            else if (random == 15){
                type = "goToIllinoisAve"; //Moves Player to Illinois Ave
                disp = "You Were Given a Free Ride to Illinois Ave!";
            }
            else if (random == 16){
                type = "goToGo"; //Moves Player To Go
                disp = "You Were Given a Free Ride to Go!";
            }
        }
        else if (deck == 2) //Draws Community Chest Card
        {
            int random = (int)((Math.random()*17)+1);
            if (random == 1){
                type = "get200"; //Gets $200
                disp = "You Were Given $200!";
            }
            else if (random == 2){
                type = "get100"; //Gets $100
                disp = "You Were Given $100!";
            }
            else if (random == 3){
                type = "get10"; //Gets $10
                disp = "You Were Given $10!";
            }
            else if (random == 4){
                type = "repairHouse"; //Pay $40/House, $115/Hotel
                disp = "You Paid $40 For Each House You Owned and $115 For Each Hotel!";
            }
            else if (random == 5){
                type = "get25"; //Gets $25
                disp = "You Were Given $25!";
            }
            else if (random == 6){
                type = "pay150"; //Lose $150
                disp = "You Paid a $150 Tax.";
            }
            else if (random == 7){
                type = "pay100"; //Lose $100
                disp = "You Paid a $100 Tax.";
            }
            else if (random == 8){
                type = "get100"; //Gets $100
                disp = "You Were Given $100!";
            }
            else if (random == 9){
                type = "get50"; //Player Gets $50
                disp = "You Were Given $50!";
            }
            else if (random == 10){
                type = "get10"; //Gets $10
                disp = "You Were Given $10!";
            }
            else if (random == 11){
                type = "get20"; //Gets $20
                disp = "You Were Given $20!";
            }
            else if (random == 12){
                type = "get100"; //Gets $100
                disp = "You Were Given $100!";
            }
            else if (random == 13){
                type = "goDirectlyToJail"; //Go To Jail, Don't Pass Go, Don't Collect $200
                disp = "You Were Arrested! Go Directly To Jail. Do Not Pass Go. Do Not Collect $200.";
            }
            else if (random == 14){
                type = "getOutOfJail"; //Get Out Of Jail Free!
                disp = "You Got a Get Out Of Jail Free Card!";
            }
            else if (random == 15){
                type = "get50"; //Gets $50
                disp = "You Were Given $50!";
            }
            else if (random == 16){
                type = "pay50"; //Lose $50
                disp = "You Paid a $50 Tax.";
            }
            else if (random == 17){
                type = "goToGo"; //Moves Player To Go
                disp = "You Were Given a Free Ride to Go!";
            }
        }
        else // Error Message
        {
            disp = "[Monopoly Card] Error: Invalid Type of Card Specified. Deck Value: " + deck;
        }
    }

    public void update(Player p) //Updates Player Values With Card
    {
        //----------------------------- Giving Money To Players -----------------------------//
        if (type.equals("get10"))
        {
            p.addBalance(10);
        }
        else if (type.equals("get20"))
        {
            p.addBalance(20);
        }
        else if (type.equals("get25"))
        {
            p.addBalance(25);
        }
        else if (type.equals("get50"))
        {
            p.addBalance(50);
        }
        else if (type.equals("get100"))
        {
            p.addBalance(100);
        }
        else if (type.equals("get200"))
        {
            p.addBalance(200);
        }
        //----------------------------- Taking Money From Player -----------------------------//
        else if (type.equals("pay50"))
        {
            p.subtractBalance(50);
        }
        else if (type.equals("pay100"))
        {
            p.subtractBalance(100);
        }
        else if (type.equals("pay150"))
        {
            p.subtractBalance(150);
        }
        //----------------------------- Modifying Player Position -----------------------------//
        else if (type.equals("goToBoardwalk"))
        {
            p.setPosition(39);
        }
        else if (type.equals("goBack3"))
        {
            p.subtractPosition(3);
        }
        else if (type.equals("goToReadingRailroad"))
        {
            if (p.getPosition() > 5)
                p.addBalance(200);
            p.setPosition(5);
        }
        else if (type.equals("goDirectlyToJail"))
        {
            p.setPosition(10);
            p.sendToJail();
        }
        else if (type.equals("goToNextRailroad"))
        {
            while (true)
            {
                int i = p.getPosition();
                if (Main.property[i].getID().equals("railroad"))
                {
                    p.setPosition(i);
                    break;
                }
                if (i == 39){
                    i= -1;
                    p.addBalance(200);}
                i++;
            }
        }
        else if (type.equals("goToNextUtility"))
        {
            while (true)
            {
                int i = p.getPosition();
                if (Main.property[i].getID().equals("utility"))
                {
                    p.setPosition(i);
                    break;
                }
                if (i == 39){
                    i= -1;
                    p.addBalance(200);}
                i++;
            }
        }
        else if (type.equals("goToStCharlesPlace"))
        {
            if (p.getPosition() > 11)
                p.addBalance(200);
            p.setPosition(11);
        }
        else if (type.equals("goToIllinoisAve"))
        {
            if (p.getPosition() > 24)
                p.addBalance(200);
            p.setPosition(24);
        }
        else if (type.equals("goToGo"))
        {
            p.setPosition(0);
            p.addBalance(200);
        }
        //----------------------------- Giving Player Cards -----------------------------//
        else if (type.equals("getOutOfJail"))
        {
            p.giveJailCard();
        }
        //----------------------------- Property Management -----------------------------//
        else if (type.equals("repairHouse"))
        {
            int house = 0;
            int hotel = 0;
            for (int i = 0; i < 40; i++)
            {
                if (Main.property[i].getPlayerOwner() != null && Main.property[i].getPlayerOwner().equals(p))
                {
                    house += Main.property[i].getHouses();
                }
                if (Main.property[i].getPlayerOwner() != null && Main.property[i].getPlayerOwner().equals(p))
                {
                    hotel += Main.property[i].getHotels();
                }
                int fee = (40 * house) + (120 * hotel);
                p.subtractBalance(fee);
            }
        }
    }
}
