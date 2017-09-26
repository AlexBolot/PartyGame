package alexandre.bolot.partygame;

/*................................................................................................................................
 . Copyright (c)
 .
 . The PlayersActivity	 Class was Coded by : Alexandre BOLOT
 .
 . Last Modified : 23/08/17 02:43
 .
 . Contact : bolotalex06@gmail.com
 ...............................................................................................................................*/

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class PlayersActivity extends AppMaster
{
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.players_view);
    
        refreshListViewPlayer();
    
        findViewById(R.id.imgBtnAdd).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                addPlayer();
            }
        });
        
        findViewById(R.id.btnReady).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                if(players.isEmpty()) return;
    
                Intent intent = new Intent(PlayersActivity.this, TruthDareActivity.class);
                startActivity(intent);
            }
        });
    }
    
    public void addPlayer ()
    {
        RadioGroup radioGroup = ((RadioGroup) findViewById(R.id.radioGroup));
        int checkedRadioBtnId = radioGroup.getCheckedRadioButtonId();
        
        // First exit case : if none of the genders are checked
        if(checkedRadioBtnId == -1) return;
        
        int gender = radioGroup.indexOfChild(findViewById(checkedRadioBtnId));
        
        EditText editText = (EditText) findViewById(R.id.txtName);
        String name = editText.getText().toString().trim();
        
        // Second exit case : if the name is not correct (empty or contains numbers or special chars)
        if(!name.matches("^[a-zA-Z\\s]+")) return;
        
        Player player = new Player(name, gender);
        players.add(player);
        
        editText.setText("");
        
        refreshListViewPlayer();
    }
    
    public void refreshListViewPlayer ()
    {
        ArrayList<String> strings = new ArrayList<>();
        
        for (Player p : players)
        {
            String genderLabel = p.getGender() == 0 ? getString(R.string.male) : getString(R.string.female);
            
            strings.add(p.getName() + " - " + genderLabel);
        }
        
        ((ListView) findViewById(R.id.listViewPlayers)).setAdapter(new ArrayAdapter<>(this, R.layout.simplerow, strings));
    }
}