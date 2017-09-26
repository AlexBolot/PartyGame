package alexandre.bolot.partygame;

/*................................................................................................................................
 . Copyright (c)
 .
 . The TruthDareActivity	 Class was Coded by : Alexandre BOLOT
 .
 . Last Modified : 23/08/17 13:58
 .
 . Contact : bolotalex06@gmail.com
 ...............................................................................................................................*/

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TruthDareActivity extends AppMaster
{
    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.truth_dare_view);
        
        Button btnDare = (Button) findViewById(R.id.btnDare);
        Button btnTruth = (Button) findViewById(R.id.btnTruth);
        TextView txtPlayerName = (TextView) findViewById(R.id.txtPlayerName);
        
        if(playerIndex >= players.size()) playerIndex = 0;
        
        String playerName = players.get(playerIndex).getName();
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
