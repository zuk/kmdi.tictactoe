package kmdi.tictactoe;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TicTacToe extends Activity {
	
	private String whosTurn;
	private boolean won;
	
	public static final int BUTTONS[] = {
			R.id.button1, R.id.button2, R.id.button3,
			R.id.button4, R.id.button5, R.id.button6,
			R.id.button7, R.id.button8, R.id.button9,
	};
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        whosTurn = "X";
        won = false;
        
        setContentView(R.layout.main);
        
        TextView status = (TextView) findViewById(R.id.status);
        status.setText(whosTurn+"'s Turn!");
    }
    
    public void mark(View view) {
    	Button b = (Button) view;
    	
    	String x = getResources().getString(R.string.x);
    	String o = getResources().getString(R.string.o);
    	
    	
    	if (b.getText() == "" // marking an already marked button should do nothing
    			&& !this.won) { // can't keep playing after someone has won
	    	if (whosTurn == o) {
	    		b.setText(o);
	    		checkWin(whosTurn);
	    		whosTurn = x;
	    	} else {
	    		b.setText(x);
	    		checkWin(whosTurn);
	    		whosTurn = o;
	    	}
	    	
	    	if (!this.won) {
	    		TextView status = (TextView) findViewById(R.id.status);
	        	status.setText(whosTurn+"'s Turn!");
	    		
	    		checkGameOver();
	    	}
    	}
    }
    
    public void checkWin(String who) {
    	int rows[][] = {
    			{R.id.button1, R.id.button2, R.id.button3},
    			{R.id.button4, R.id.button5, R.id.button6},
    			{R.id.button7, R.id.button8, R.id.button9},
    	};
    	int cols[][] = {
    			{R.id.button1, R.id.button4, R.id.button7},
    			{R.id.button2, R.id.button5, R.id.button8},
    			{R.id.button3, R.id.button6, R.id.button9},
    	};
    	int diags[][] = {
    			{R.id.button1, R.id.button5, R.id.button9},
    			{R.id.button3, R.id.button5, R.id.button7},
    	};
    	
    	String x = getResources().getString(R.string.x);
    	String o = getResources().getString(R.string.o);
    	
    	
    	for(int i = 0; i < rows.length; i++) {
    		if (checkLine(who, rows[i])) winLine(who, rows[i]);
    	}
    	
    	for(int i = 0; i < cols.length; i++) {
    		if (checkLine(who, cols[i])) winLine(who, cols[i]);
    	}
    	
    	for(int i = 0; i < diags.length; i++) {
    		if (checkLine(who, diags[i])) winLine(who, diags[i]);
    	}
    }
    
    public boolean checkLine(String who, int[] line) {
    	return ((Button) findViewById(line[0])).getText() == who 
    			&& ((Button) findViewById(line[1])).getText() == who
    			&& ((Button) findViewById(line[2])).getText() == who;
    }
    
    public void winLine(String who, int[] line) {
    	TextView status = (TextView) findViewById(R.id.status);
    	status.setText(who+" WINS!");
    	status.setTextColor(Color.GREEN);
    	
    	for(int i = 0; i < line.length; i++) {
    		((Button) findViewById(line[i])).setTextColor(Color.GREEN);
    	}
    	
    	this.won = true;
    }
    
    public void checkGameOver() {
    	Button b;
    	
    	TextView status = ((TextView) findViewById(R.id.status));
        
    	boolean allMarked = true;
    	
        for (int i = 0; i < BUTTONS.length; i++) {
        	b = ((Button) findViewById(BUTTONS[i]));
        	if (b.getText() == "") {
        		allMarked = false;
        		break;
        	}
        }
        
        if (allMarked) {
        	status.setText("GAME OVER :(");
        	status.setTextColor(Color.RED);
        }
    }
    
    
    
    // restarting the game via the menu
    
    public void restartGame() {
        Button b;
        for (int i = 0; i < BUTTONS.length; i++) {
        	b = ((Button) findViewById(BUTTONS[i]));
        	b.setTextColor(Color.BLACK);
        	b.setText("");
        }
        
        whosTurn = getResources().getString(R.string.x);
        TextView status = ((TextView) findViewById(R.id.status));
        status.setText(whosTurn+"'s Turn!");
        status.setTextColor(Color.WHITE);
        
        this.won = false;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.restart:
                restartGame();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}