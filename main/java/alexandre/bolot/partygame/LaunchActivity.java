package alexandre.bolot.partygame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*................................................................................................................................
 . Copyright (c)
 .
 . The LaunchActivity	 Class was Coded by : Alexandre BOLOT
 .
 . Last Modified : 27/09/17 11:23
 .
 . Contact : bolotalex06@gmail.com
 ...............................................................................................................................*/

/**
 This class is the Activity managing the main_view.xml.<br>
 This activity only displays a "play" button.<br>
 <br>
 __ Class Dependency : AppMaster, PlayersActivity __
 */
public class LaunchActivity extends AppMaster
{
    /**
     This is the initialization method, called on the Activity's launch.<br>
     It will clear the players list and inits the button's listener.<br>
     <br>
     __ Class Dependency : PlayersActivity __
 
     @param savedInstanceState No idea what that does...
     */
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
        
        players.clear();
        
        Button btnPlayers = (Button) findViewById(R.id.btnPlay);
        
        btnPlayers.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                Intent intent = new Intent(LaunchActivity.this, PlayersActivity.class);
                startActivity(intent);
            }
        });
    }
}
