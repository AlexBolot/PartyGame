package alexandre.bolot.partygame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*................................................................................................................................
 . Copyright (c)
 .
 . The TruthDareActivity	 Class was Coded by : Alexandre BOLOT
 .
 . Last Modified : 01/10/17 22:28
 .
 . Contact : bolotalex06@gmail.com
 ...............................................................................................................................*/

/**
 This class is the Activity managing the truth_dare_view.xml.<br>
 This Activity displays the current player's name and asks to pick either Truth or Dare.<br>
 <br>
 __ Class Dependency : AppMaster, ChallengeActivity __
 */
public class TruthDareActivity extends AppMaster
{
    /**
     This is the initialization method, called on the Activity's launch.<br>
     It initializes : the GUI listeners and pick the next player in AppMaster.players.<br>
     <br>
     __ Class Dependency : ChallengeActivity __
     
     @param savedInstanceState No idea what that does...
     */
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.truth_dare_view);
        
        Button btnDare = (Button) findViewById(R.id.btnDare);
        Button btnTruth = (Button) findViewById(R.id.btnTruth);
        TextView txtPlayerName = (TextView) findViewById(R.id.txtPlayerName);
        
        if(playerIndex >= players.size()) playerIndex = 0;
    
        String playerName = randOrder ? Player.getRandom().getName() : players.get(playerIndex).getName();
    
        txtPlayerName.setText(playerName);
        
        final Intent intent = new Intent(TruthDareActivity.this, ChallengeActivity.class);
        intent.putExtra(getString(R.string.player_name), playerName);
        
        btnDare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                intent.putExtra(getString(R.string.choice), getString(R.string.dare));
                startActivity(intent);
            }
        });
        
        btnTruth.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                intent.putExtra(getString(R.string.choice), getString(R.string.truth));
                startActivity(intent);
            }
        });
    }
}
