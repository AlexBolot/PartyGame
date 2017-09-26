package alexandre.bolot.partygame;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;


/*................................................................................................................................
 . Copyright (c)
 .
 . The AppMaster	 Class was Coded by : Alexandre BOLOT
 .
 . Last Modified : 26/09/17 14:39
 .
 . Contact : bolotalex06@gmail.com
 ...............................................................................................................................*/

/**
 This class is meant to be used inbetween <code>AppCompatActivity</code> and the Activities.<br>
 It will allow access to the players list and the current player's index.
 */
public class AppMaster extends AppCompatActivity
{
    /**
     List of all players.<br>
     Can be accessed from any Activity extending <code>AppMaster</code>
     */
    public static ArrayList<Player> players     = new ArrayList<>();
    /**
     Index of the current player.<br>
     Used to cycle through all the players of [players]
     */
    public static int               playerIndex = 0;
}
