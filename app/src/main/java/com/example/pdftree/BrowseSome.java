package com.example.pdftree;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;

public class BrowseSome extends Activity implements OnClickListener {
	EditText etPage;
	Button btBrowser, bStart, bStop;
	TextView tv;
	String currentFileName, ctr;
	int currentPage = 1;
	private static final int REQUESTCODE = 1;
	TextToSpeech tts;
	AsyncThread ast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browse_lay);
		etPage = (EditText) findViewById(R.id.searchText);
		btBrowser = (Button) findViewById(R.id.browseButton);
		bStart = (Button) findViewById(R.id.readButton);
		btBrowser.setOnClickListener(this);
		bStart.setOnClickListener(this);
		bStop = (Button) findViewById(R.id.pauseButton);
		bStop.setOnClickListener(this);
		tv=(TextView) findViewById(R.id.enterText);

		tts = new TextToSpeech(this, new OnInitListener() {

			@Override
			public void onInit(int status) {

				// TODO Auto-generated method stub
				if (status != TextToSpeech.ERROR) {
					Log.w("Me", "OnInit called");
					tts.setLanguage(Locale.ENGLISH);
					tts.setSpeechRate(.85f);

				}

			}
		});

	}

	@Override
	public void onClick(View arg0) {

		// TODO Auto-generated method stub
		if (arg0 == btBrowser) {
			Intent intent = new Intent(this, FileChooser.class);
			startActivityForResult(intent, REQUESTCODE);
		} else if (arg0 == bStart) {

			ast= new AsyncThread(this,tv.getText().toString(),etPage.getText().toString(),tts);
			ast.execute();
		} else if (arg0 == bStop) {

			if (!ast.isCancelled()) {
				tts.stop();
				ast.cancel(true);
				ast = null;

			}
		}

	}
	protected void onPause() {
		tts.stop();
		if (ast!= null && !ast.isCancelled())
			ast.cancel(true);

		super.onPause();

	}

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUESTCODE && resultCode == RESULT_OK) {
			currentFileName = data.getStringExtra("GetPath");
			tv.setText(currentFileName);
		}
	}

	
}
