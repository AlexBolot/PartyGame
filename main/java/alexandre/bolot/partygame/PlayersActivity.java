package alexandre.bolot.partygame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;

import java.util.ArrayList;

/*................................................................................................................................
 . Copyright (c)
 .
 . The PlayersActivity	 Class was Coded by : Alexandre BOLOT
 .
 . Last Modified : 27/09/17 11:34
 .
 . Contact : bolotalex06@gmail.com
 ...............................................................................................................................*/

/**
 This class is the Activity managing the players_view.xml.<br>
 This Activity allows you to add Players to the game.<br>
 <br>
 __ Class Dependency : AppMaster, TruthDareActivity __
 */
public class PlayersActivity extends AppMaster
{
    /**
     This is the initialization method, called on the Activity's launch.<br>
     It initializes : the GUI listeners.<br>
     <br>
     __ Class Dependency : TruthDareActivity __
     
     @param savedInstanceState No idea what that does...
     */
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
    
    /**
     Whenever the '+' button is clicked, this method is called.<br>
     It adds a new Player to the AppMaster.players.<br>
     <br>
     It does a couple checks before adding the player :<br>
     — Checks if gender is selected.<br>
     — Checks if name !isEmpty() and contains only letters.<br>
     */
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
    
    /**
     Simple refresh method : re-reads the value and rebinds the Adapter.
     */
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