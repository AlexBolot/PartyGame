package alexandre.bolot.partygame;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

/*................................................................................................................................
 . Copyright (c)
 .
 . The ChallengeActivity	 Class was Coded by : Alexandre BOLOT
 .
 . Last Modified : 27/09/17 10:33
 .
 . Contact : bolotalex06@gmail.com
 ...............................................................................................................................*/

/**
 This class is the Activity managing the challenge_view.xml.<br>
 This activity is the one displaying the challenge :
 <ol>
 <li>It shows the name of the current player.</li>
 <li>It shows the content of the challenge.</li>
 <li>It offers to go to the next challenge.</li>
 <li>It offers to start a timer, if the challenge requires it.</li>
 </ol>
 <br>
 __ Class Dependency : AppMaster __
 */
public class ChallengeActivity extends AppMaster
{
    private ProgressBar progressBar;
    private TextView    txtPlayerName;
    private TextView    txtChallenge;
    private Button      btnStartTimer;
    private Button      btnNext;
    
    private boolean hasTimer = false;
    private int     duration = 0;
    
    /**
     This is the initialization method, called on the Activity's launch.<br>
     It initializes :
     <ol>
     <li>the GUI components</li>
     <li>the GUI listeners</li>
     <li>the values of the TextFields (challenge and playerName)</li>
     </ol>
     
     It reads from the value files.
     
     @param savedInstanceState No idea what that does...
     */
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge_view);
        
        String choice = getIntent().getStringExtra(getString(R.string.choice));
        
        //region --> Init gui objects
        txtPlayerName = (TextView) findViewById(R.id.txtPlayerName);
        txtChallenge = (TextView) findViewById(R.id.txtChallenge);
        txtChallenge.setMovementMethod(new ScrollingMovementMethod());
        btnStartTimer = (Button) findViewById(R.id.btnStartTimer);
        btnNext = (Button) findViewById(R.id.btnNext);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //endregion
        
        //region --> Init listeners
        btnNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                Intent intent = new Intent(ChallengeActivity.this, TruthDareActivity.class);
                startActivity(intent);
                
                playerIndex++;
            }
        });
        
        btnStartTimer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v)
            {
                startCounter();
            }
        });
        //endregion
        
        String[] dares = getResources().getStringArray(R.array.dares);
        String[] truths = getResources().getStringArray(R.array.truths);
        
        Random rand = new Random();
        int index;
        
        String playerName = getIntent().getStringExtra(getString(R.string.player_name));
        
        txtPlayerName.setText(playerName);
        
        if(choice.equals(getString(R.string.dare)))
        {
            index = rand.nextInt(dares.length);
            txtChallenge.setText(replaceTags(dares[index], Player.getByName(playerName)));
        }
        else
        {
            index = rand.nextInt(truths.length);
            txtChallenge.setText(replaceTags(truths[index], Player.getByName(playerName)));
        }
        
        //region --> Set visibility
        if(hasTimer)
        {
            btnStartTimer.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
        else
        {
            btnStartTimer.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
        //endregion
    }
    
    /**
     This method can replace the OTHER_PLAYER, FEMALE_PLAYER, etc tags from a challenge's content.<br>
     If the tag is OTHER_PLAYER, it will avoid using the [currentPlayer] from the parameters.<br>
     If the TIME tag is found, it will show the ProgressBar and set it with the appropriate time.
 
     @param challenge     Challenge containing the tags.
     @param currentPlayer Player to avoid when OTHER_PLAYER tag is found.
     @return the new Challenge, without the tags.
     */
    private String replaceTags (String challenge, Player currentPlayer)
    {
        //region --> Replacing when OTHER PLAYER is found (gender neutral)
        while (challenge.contains(getString(R.string.OTHER_PLAYER)))
        {
            Player newPlayer = Player.getAlmostRandom(currentPlayer);
            
            while (challenge.contains(newPlayer.getName()) || newPlayer.getName().equals(currentPlayer.getName()))
            {
                newPlayer = Player.getAlmostRandom(currentPlayer);
            }
            
            challenge = challenge.replaceFirst(getString(R.string.OTHER_PLAYER), newPlayer.getName());
        }
        //endregion
        
        //region --> Replacing when FEMALE PLAYER is found (gender selective)
        while (challenge.contains(getString(R.string.FEMALE_PLAYER)))
        {
            String newName = Player.getRandomFemale().getName();
            
            while (challenge.contains(newName) || newName.equals(currentPlayer.getName()))
            {
                newName = Player.getRandomFemale().getName();
            }
            
            challenge = challenge.replaceFirst(getString(R.string.FEMALE_PLAYER), newName);
        }
        //endregion
        
        //region --> Replacing when MALE PLAYER is found (gender selective)
        while (challenge.contains(getString(R.string.MALE_PLAYER)))
        {
            String newName = Player.getRandomMale().getName();
            
            while (challenge.contains(newName) || newName.equals(currentPlayer.getName()))
            {
                newName = Player.getRandomMale().getName();
            }
            
            challenge = challenge.replaceFirst(getString(R.string.MALE_PLAYER), newName);
        }
        //endregion
        
        //region --> Replacing when TIMER is found (time selection)
        while (challenge.contains(getString(R.string.TIMER)))
        {
            int index = challenge.indexOf(getString(R.string.TIMER));
            
            hasTimer = true;
            
            String time = challenge.substring(index - 3, index - 1);
            
            duration = Integer.parseInt(time) * 1000;
            
            challenge = challenge.replaceAll(getString(R.string.TIMER), getString(R.string.gui_secondes));
        }
        //endregion
        
        return challenge;
    }
    
    /**
     Starts the progress of the ProgressBar and blocks the GUI until the progress.
     */
    private void startCounter ()
    {
        if(duration == 0) return;
        
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 100); // see this max value coming back here, we animate towards that value
        animation.setDuration(duration); //in milliseconds
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
        
        animation.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart (Animator animation)
            {
                progressBar.setVisibility(View.VISIBLE);
                btnStartTimer.setVisibility(View.INVISIBLE);
                btnNext.setVisibility(View.INVISIBLE);
            }
            
            @Override
            public void onAnimationEnd (Animator animation)
            {
                progressBar.clearAnimation();
                btnNext.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
            
            @Override
            public void onAnimationCancel (Animator animation)
            {
                // Nothing to do here.
            }
            
            @Override
            public void onAnimationRepeat (Animator animation)
            {
                // Nothing to do here.
            }
        });
        
    }
}
