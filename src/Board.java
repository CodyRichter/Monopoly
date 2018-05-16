
/**
 * Displays Monopoly Board
 *
 * @author Cody Richter
 * @version 1.0
 */
public class Board
{
    Player p1, p2, p3;
    Player[] players;
    int numPlayers;
    int[] location = new int[40];
    public Board()
    {

    }

    public void addPlayers (Player[] p)
    {
        players = new Player[p.length];
        for (int i = 0 ; i < p.length; i++) {
            players[i] = p[i];
        }
    }

    /**
     * Creates Board To Display From Individual Tiles
     * @return Board String
     */
    public String getBoard()
    {
        StringBuilder buffer = new StringBuilder();
        for(int i = 0; i < players.length*9; i++) {
            buffer.append(" ");
        }
        return ""+
                getTile(20)+""+getTile(21)+getTile(22)+getTile(23)+getTile(24)+getTile(25)+getTile(26)+getTile(27)+getTile(28)+getTile(29)+getTile(30)+ "\n" +
                getTile(19) + "                  " + buffer.toString() + getTile(31) + "\n" +
                getTile(18) + "                  " + buffer.toString() + getTile(32) + "\n" +
                getTile(17) + "                  " + buffer.toString() + getTile(33) + "\n" +
                getTile(16) + "                  " + buffer.toString() + getTile(34) + "\n" +
                getTile(15) + "                  " + buffer.toString() + getTile(35) + "\n" +
                getTile(14) + "                  " + buffer.toString() + getTile(36) + "\n" +
                getTile(13) + "                  " + buffer.toString() + getTile(37) + "\n" +
                getTile(12) + "                  " + buffer.toString() + getTile(38) + "\n" +
                getTile(11) + "                  " + buffer.toString() + getTile(39) + "\n" +
                getTile(10)+""+getTile(9)+getTile(8)+getTile(7)+getTile(6)+getTile(5)+getTile(4)+getTile(3)+getTile(2)+getTile(1)+getTile(0);
    }

    /**
     * Sets Individual Tile To Show Players
     * Expanded to accomodate up to 9 players 02/22/2018
     */
    private String getTile(int position)
    {
        char unownedSymbol = '-';
        char ownedSymbol = '+';

        char pos[] = new char[players.length];

        for (int i = 0; i < pos.length; i++) {
            pos[i] = unownedSymbol;
        }

        if (MonopolyInterface.property[position].getPlayerOwner() != null)
        {
            for (int i = 0; i < pos.length; i++) {
                pos[i] = ownedSymbol;
            }
        }

        for (int i = 0; i < players.length; i++) {
            if (players[i].getPosition() == position && !players[i].isEliminated())
            {
                String number = ""+(i+1);
                pos[i] = number.charAt(0);
            }
            else if (players[i].isEliminated() && players[i].getPosition() == position)
            {
                pos[i] = '✟';
            }
        }
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < players.length; i++) {
            result.append(pos[i]);
        }
        result.append("]");
        return result.toString();
    }
}
