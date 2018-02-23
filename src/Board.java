
/**
 * Displays Monopoly Board
 *
 * @author Cody Richter
 * @version 1.0
 */
public class Board
{
    Player p1, p2, p3;
    int numPlayers;
    int[] location = new int[40];
    public Board()
    {

    }

    public void addPlayers (Player pi1, Player pi2, Player pi3)
    {
        p1 = pi1;
        p2 = pi2;
        p3 = pi3;
        numPlayers = 3;
    }

    public void addPlayers (Player pi1, Player pi2)
    {
        p1 = pi1;
        p2 = pi2;
        numPlayers = 2;
    }

    public String getBoard()
    {
        return ""+
                getTile(20)+""+getTile(21)+getTile(22)+getTile(23)+getTile(24)+getTile(25)+getTile(26)+getTile(27)+getTile(28)+getTile(29)+getTile(30)+ "\n" +
                getTile(19) + "                                             " + getTile(31) + "\n" +
                getTile(18) + "                                             " + getTile(32) + "\n" +
                getTile(17) + "                                             " + getTile(33) + "\n" +
                getTile(16) + "                                             " + getTile(34) + "\n" +
                getTile(15) + "                                             " + getTile(35) + "\n" +
                getTile(14) + "                                             " + getTile(36) + "\n" +
                getTile(13) + "                                             " + getTile(37) + "\n" +
                getTile(12) + "                                             " + getTile(38) + "\n" +
                getTile(11) + "                                             " + getTile(39) + "\n" +
                getTile(10)+""+getTile(9)+getTile(8)+getTile(7)+getTile(6)+getTile(5)+getTile(4)+getTile(3)+getTile(2)+getTile(1)+getTile(0);
    }

    /**
     * Sets Individual Tile To Show Players
     */
    private String getTile(int position)
    {
        char pos1 = '•';
        char pos2 = '•';
        char pos3 = '•';
        //         if (Main.property[position].getPlayerOwner() != null && Main.property[position].getPlayerOwner().equals(p1))
        //         {
        //             pos1 = Main.symbol[0];
        //             pos2 = Main.symbol[0];
        //             pos3 = Main.symbol[0];
        //         }
        //         if (Main.property[position].getPlayerOwner() != null && Main.property[position].getPlayerOwner().equals(p1))
        //         {
        //             pos1 = Main.symbol[1];
        //             pos2 = Main.symbol[1];
        //             pos3 = Main.symbol[1];
        //         }
        //         if (Main.property[position].getPlayerOwner() != null && Main.property[position].getPlayerOwner().equals(p1))
        //         {
        //             pos1 = Main.symbol[2];
        //             pos2 = Main.symbol[2];
        //             pos3 = Main.symbol[2];
        //         }
        if (Main.property[position].getPlayerOwner() != null)
        {
            pos1 = '*';
            pos2 = '*';
            pos3 = '*';
        }

        if (numPlayers == 3)
        {
            if (p1.getPosition() == position && p1.isEliminated() == false)
            {
                pos1 = '1';
            }
            else if (p1.isEliminated() && p1.getPosition() == position)
            {
                pos1 = '✟';
            }
            if (p2.getPosition() == position && p2.isEliminated() == false)
            {
                pos2 = '2';
            }
            else if (p2.isEliminated() && p2.getPosition() == position)
            {
                pos2 = '✟';
            }

            if (p3.getPosition() == position && p3.isEliminated() == false)
            {
                pos3 = '3';
            }
            else if (p3.isEliminated() && p3.getPosition() == position)
            {
                pos3 = '✟';
            }
        }
        if (numPlayers == 2)
        {
            if (p1.getPosition() == position && p1.isEliminated() == false)
            {
                pos1 = '1';
            }
            else if (p1.isEliminated() && p1.getPosition() == position)
            {
                pos1 = '✟';
            }

            if (p2.getPosition() == position && p2.isEliminated() == false)
            {
                pos3 = '2';
            }
            else if (p2.isEliminated() && p2.getPosition() == position)
            {
                pos3 = '✟';
            }
        }
        return "["+pos1+pos2+pos3+"]";
    }
}
