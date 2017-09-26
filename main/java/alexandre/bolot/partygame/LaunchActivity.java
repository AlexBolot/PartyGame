package alexandre.bolot.partygame;

/*................................................................................................................................
 . Copyright (c)
 .
 . The LaunchActivity	 Class was Coded by : Alexandre BOLOT
 .
 . Last Modified : 23/08/17 02:42
 .
 . Contact : bolotalex06@gmail.com
 ...............................................................................................................................*/

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LaunchActivity extends AppMaster
{
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
        
        players.clear();
    
        players.add(new Player("Alex", 0));
        players.add(new Player("Romain", 0));
        players.add(new Player("Marie", 1));
        players.add(new Player("Coralie", 1));
        
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
