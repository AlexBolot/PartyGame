package alexandre.bolot.partygame;

import java.util.Random;

/*................................................................................................................................
 . Copyright (c)
 .
 . The Player	 Class was Coded by : Alexandre BOLOT
 .
 . Last Modified : 27/09/17 11:16
 .
 . Contact : bolotalex06@gmail.com
 ...............................................................................................................................*/

/**
 This class represents a Player by its name and gender.<br>
 The static methods allow to get Players from [AppMaster.players].<br>
 */
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
    
    /**
     Simple and basic Constructor.
     
     @param name   Name of the player
     @param gender Gender of the player (0 = male | 1 = female)
     */
    public Player (String name, int gender)
    {
        this.name = name;
        this.gender = gender;
    }
    
    /**
     This method is used to more likely return a female player when the current on is a male.<br>
     Algorithm :
     <ol>
     <li>Gets a random Player called newPlayer.</li>
     <li>Toss a coin if currentPlayer and newPlayer are males.</li>
     <li>Depending on the coin, will either return newPlayer or a random Female Player.</li>
     </ol>
 
     Note, the coin has 80% of being in favor of Female Player.<br>
     This can be called cheating but, as a straight male AND developper of the game, I get to pick who I wanna play against :D
 
     @param currentPlayer The currentPlayer, to find if it's a male or female.
     @return Another Player, selected more or less randomly as explained above.
     */
    public static Player getAlmostRandom (Player currentPlayer)
    {
        Player newPlayer = getRandom();
        
        boolean bothMale = currentPlayer.getGender() == 0 && newPlayer.getGender() == 0;
        boolean tossCoin = new Random().nextInt(100) > 80;
        
        return bothMale && tossCoin ? newPlayer : getRandomFemale();
    }
    
    /**
     @return A random Player from [AppMaster.players].
     */
    public static Player getRandom ()
    {
        return AppMaster.players.get(new Random().nextInt(AppMaster.players.size()));
    }
    
    /**
     @return A random Female Player from [AppMaster.players].<br>
     Calls <code>public static Player getRandom()</code> until a female is found.
     */
    public static Player getRandomFemale ()
    {
        Player femalePlayer = getRandom();
        
        while (femalePlayer.getGender() != 1)
        {
            femalePlayer = getRandom();
        }
        
        return femalePlayer;
    }
    
    /**
     @return A random Male Player from [AppMaster.players].<br>
     Calls <code>public static Player getRandom()</code> until a male is found.
     */
    public static Player getRandomMale ()
    {
        Player malePlayer = getRandom();
        
        while (malePlayer.getGender() != 0)
        {
            malePlayer = getRandom();
        }
        
        return malePlayer;
    }
    
    /**
     Finds a specific Player using it's name.
     
     @param playerName Name of the Player to find.
     @return The player of name [playerName].
     */
    public static Player getByName (String playerName)
    {
        for (Player p : AppMaster.players)
        {
            if(p.getName().equals(playerName)) return p;
        }
        
        return new Player("PlayerNotFound", 0);
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
}
