package alexandre.bolot.partygame;

/*................................................................................................................................
 . Copyright (c)
 .
 . The Player	 Class was Coded by : Alexandre BOLOT
 .
 . Last Modified : 23/08/17 02:42
 .
 . Contact : bolotalex06@gmail.com
 ...............................................................................................................................*/

import java.util.Random;

@SuppressWarnings("WeakerAccess")
public class Player
{
    private String name;
    
    /**
     The gender of the Player.
     0 = male
     1 = female
     */
    private int gender;
    
    public Player (String name, int gender)
    {
        this.name = name;
        this.gender = gender;
    }
    
    //region Getters and Setters
    public String getName ()
    {
        return name;
    }
    
    public void setName (String name)
    {
        this.name = name;
    }
    
    public int getGender ()
    {
        return gender;
    }
    
    public void setGender (int gender)
    {
        this.gender = gender;
    }
    //endregion
    
    public static Player getAlmostRandom (Player currentPlayer)
    {
        Player newPlayer = getRandom();
        
        boolean bothMale = currentPlayer.getGender() == 0 && newPlayer.getGender() == 0;
        boolean tossCoin = new Random().nextInt(100) > 80;
        
        return bothMale && tossCoin ? newPlayer : getRandomFemale();
    }
    
    public static Player getRandom ()
    {
        return AppMaster.players.get(new Random().nextInt(AppMaster.players.size()));
    }
    
    public static Player getRandomFemale ()
    {
        Player femalePlayer = getRandom();
        
        while (femalePlayer.getGender() != 1)
        {
            femalePlayer = getRandom();
        }
        
        return femalePlayer;
    }
    
    public static Player getRandomMale ()
    {
        Player malePlayer = getRandom();
        
        while (malePlayer.getGender() != 0)
        {
            malePlayer = getRandom();
        }
        
        return malePlayer;
    }
    
    public static Player getByName (String playerName)
    {
        for (Player p : AppMaster.players)
        {
            if(p.getName().equals(playerName)) return p;
        }
        
        return new Player("PlayerNotFound", 0);
    }
}
