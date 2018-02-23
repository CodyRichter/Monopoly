
/**
 * Player Object
 *
 * @author Cody Richter
 * @version 1.0
 */
public class Player
{
    // instance variables - replace the example below with your own
    private int balance;
    private int position;
    private boolean jail = false;
    private int getOutOfJail = 0;
    private int turnJailed;
    private String name;
    private Card lastCard;
    private boolean isEliminated = false;

    /**
     * Constructor for objects of class Player
     */
    public Player(String username)
    {
        name = username;
        balance = 1500;
        position = 0;
    }
    //---------------------
    //---------------------------------
    // Setters
    //---------------------------------
    //---------------------

    /**
     * Gives Player Money
     */
    public void addBalance(int amount)
    {
        if (amount > 0)balance += amount;
    }

    /**
     * Takes Player's Money
     */
    public void subtractBalance(int amount)
    {
        if (amount > 0)balance -= amount;
    }

    /**
     * Sets Player's Money
     */
    public void setBalance(int amount)
    {
        if (amount >= 0)balance = amount;
    }

    /**
     * Sets Player As "In Jail"
     */
    public void sendToJail()
    {
        jail = true;
        turnJailed = Main.round;
    }

    /**
     * Sets Player As "Out Of Jail"
     */
    public void escapeJail()
    {
        jail = false;
    }

    /**
     * Eliminates Player From Game
     */
    public void eliminate()
    {
        isEliminated = true;
    }

    /**
     * Gives Player "Get Out Of Jail Free Card"
     */
    public void giveJailCard()
    {
        getOutOfJail++;
    }

    /**
     * Takes Player "Get Out Of Jail Free Card"
     */
    public void takeJailCard()
    {
        if(getOutOfJail > 0)
            getOutOfJail--;
    }

    /**
     * Adds Position, Will Compensate For Passing Go
     */
    public void addPosition(int value)
    {
        if (value <= 0)
            return;
        for (int i = 0; i < value; i++)
        {
            if (position == 39)
            {
                position = 0;
                addBalance(200);
            }
            else
            {
                position++;
            }
        }
    }

    /**
     * Subtracts Position, Will Compensate For Passing Go
     */
    public void subtractPosition(int value)
    {
        if (value <= 0)
            return;
        for (int i = 0; i < value; i++)
        {
            if (position == 0)
            {
                position = 39;
            }
            else
            {
                position--;
            }
        }
    }

    /**
     * Sets Players Position On The Board, No Checks For Spaces Passed By.
     */
    public void setPosition(int value)
    {
        if (value < 39 && value >= 0) position = value;
    }

    //---------------------
    //---------------------------------
    // Actions
    //---------------------------------
    //---------------------

    /**
     * Draws Card For Player From Specified Deck
     */
    public void drawCard(String type)
    {
        Card card = new Card(type);
        card.update(this);
        lastCard = card;
    }

    public void drawCardDirect(String type)
    {
        Card card = new Card(type, 1);
        card.update(this);
        lastCard = card;
    }

    public void endGame()
    {
        System.out.println("Game Over!");
    }

    //---------------------
    //---------------------------------
    // Getters
    //---------------------------------
    //---------------------
    /**
     * Returns Player Balance
     */
    public int getBalance()
    {
        return balance;
    }

    /**
     * Returns Player Username
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns Amount of Get Out of Jail Cards
     */
    public int getGetOutOfJail()
    {
        return getOutOfJail;
    }

    /**
     * Gets Player Position on Board
     */
    public int getPosition()
    {
        return position;
    }

    /**
     * Returns If Player is In Jail
     */
    public boolean isJailed()
    {
        return jail;
    }

    /**
     * Returns If Player is Eliminated
     */
    public boolean isEliminated()
    {
        return isEliminated;
    }

    /**
     * Returns Turn Player Was Jailed
     */
    public int getTurnJailed()
    {
        return turnJailed;
    }

    /**
     * Returns Card Type
     */
    public String getCardType()
    {
        if (lastCard.toString() == null) return "[Error] Card Has Not Been Drawn Yet.";
        return lastCard.toString();
    }
}

