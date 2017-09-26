package alexandre.bolot.partygame;

/*................................................................................................................................
 . Copyright (c)
 .
 . The ChallengeActivity	 Class was Coded by : Alexandre BOLOT
 .
 . Last Modified : 23/08/17 05:36
 .
 . Contact : bolotalex06@gmail.com
 ...............................................................................................................................*/

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

public class ChallengeActivity extends AppMaster
{
    private ProgressBar progressBar;
    private TextView    txtPlayerName;
    private TextView    txtChallenge;
    private Button      btnStartTimer;
    private Button      btnNext;
    
    private Boolean hasTimer = false;
    private int duration = 0;
    
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
    
    private void startCounter ()
    {
        if(duration == 0) return;
        
        progressBar.setVisibility(View.VISIBLE);
        btnStartTimer.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.INVISIBLE);
        
        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 100); // see this max value coming back here, we animate towards that value
        animation.setDuration(duration); //in milliseconds
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
        
        animation.addListener(new Animator.AnimatorListener()
        {
            @Override
            public void onAnimationStart (Animator animation)
            {
                // Nothing to do here.
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
