package com.example.helloworldtrial;

import com.google.android.glass.app.Card;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Card card = new Card(getApplicationContext());
		card.setText("wqdwq");
		card.setFootnote("well hello there");
		View view = card.toView();
		setContentView(view);
	
	}



}
