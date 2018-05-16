
/**
 * Monopoly Property Object
 *
 * @author Cody Richter
 * @version 1.0
 */
public class Property
{
    private String name;
    private String type;
    private Player owner;
    private int location;
    private int price = -1;
    private int rent = 0;
    private int rentOrig = 0;
    private int building = 0;
    private int hotel = 0;

    //---------------------
    //---------------------------------
    // Constructors
    //---------------------------------
    //---------------------
    public Property(int place, String nameInput, int cost, int rentPay ,String classification)
    {
        location = place;
        name = nameInput;
        price = cost;
        rent = rentPay;
        type = classification;
    }

    public Property(int place, String nameInput, int cost, int rentPay)
    {
        location = place;
        name = nameInput;
        price = cost;
        rent = rentPay;
        rentOrig = rentPay;

    }

    public Property(int place, String nameInput, int cost, String classification)
    {
        location = place;
        name = nameInput;
        price = cost;
        type = classification;
    }

    public Property(int place, String nameInput, String classification)
    {
        location = place;
        name = nameInput;
        type = classification;
    }

    //---------------------
    //---------------------------------
    // Setters
    //---------------------------------
    //---------------------

    /**
     * Sets Property Owner
     */
    public void setOwner(Player p)
    {
        owner = p;
    }

    /**
     * Removes Property Owner & Their Modifications to The Property.
     */
    public void removeOwner()
    {
        owner = null;
        rent = rentOrig;
        building = 0;
        hotel = 0;
    }

    /**
     * Builds House/Hotel On Property.
     */
    public void addBuilding()
    {
        if (building < 4)
        {
            building++;
            rent += 50;
        }
        else
        {
            hotel++;
            rent += 200;
        }
    }

    //---------------------
    //---------------------------------
    // Getters
    //---------------------------------
    //---------------------
    /**
     * Returns Position of Property
     */
    public int getPosition()
    {
        return location;
    }

    /**
     * Returns ID of Property
     */
    public String getID()
    {
        return type;
    }

    /**
     * Returns Name of Property
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns Price of Property
     */
    public int getPrice()
    {
        return price;
    }

    /**
     * Returns Rent Price of Property
     */
    public int getRent()
    {
        if (type.equalsIgnoreCase("railroad")) //Will Return Correct Rent If Property Is a Railroad
        {
            int num = 0;
            for (Property p: MonopolyInterface.property)
            {
                if (p.getID().equalsIgnoreCase("railroad") && !p.getOwner().equalsIgnoreCase("Not Owned") && p.getOwner().equalsIgnoreCase(owner.getName()))
                {
                    num++;
                    Main.getGame().msg3 = ("Total Matching Railroads: " + num);
                }
            }

            return (int)(12.5*Math.pow(2, num));
        }
        return rent;
    }

    /**
     * Returns What Buildings Are Constructed On Property
     */
    public String getBuildings()
    {
        if (building <= 4 && building > 0)
        {
            return "House";
        }
        else if (building > 4)
            return "Hotel";
        return "No Buildings.";
    }

    public int getNumBuildings()
    {
        return building+hotel;
    }

    public int getHouses()
    {
        return building;
    }

    public int getHotels()
    {
        return hotel;
    }

    /**
     * Returns Property Owner
     */
    public String getOwner()
    {
        if (owner == null) return "Not Owned";
        return owner.getName();
    }

    /**
     * Returns Property Owner as Player
     */
    public Player getPlayerOwner()
    {
        return owner;
    }
}
