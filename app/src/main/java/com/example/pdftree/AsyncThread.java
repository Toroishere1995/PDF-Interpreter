package com.example.pdftree;

import java.util.HashMap;
import java.util.Locale;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.util.Log;

import com.example.pdftree.PDFileReader;

public class AsyncThread extends AsyncTask<Void, Integer, Void> {

	TextToSpeech tts;
	Context context;
	PDFileReader extracter;
	final String path;
	final int total;
	private int pagecounter;

	private HashMap<String, String> map;

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
	public AsyncThread(Context context, String path, String pageNo,TextToSpeech t) {
		this.context = context;

		this.path = path;
		tts = t;
		extracter=new PDFileReader(path);
		total=extracter.getNoPages();
		if(pageNo.equals(""))
			pagecounter=1;
		else
			pagecounter=Integer.parseInt(pageNo);

		map = new HashMap<String, String>();
		map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "uniqueID");


		tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {

			@Override
			public void onStart(String arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(String arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onDone(String arg0) {
				Log.e("Inside background", "next page");
				if (pagecounter <= total) {
					String content=extracter.read(pagecounter);
					if (content != null) {
						tts.speak(content, TextToSpeech.QUEUE_FLUSH, map);
					} else {
						tts.speak("No Content Found on page " + pagecounter,
								TextToSpeech.QUEUE_FLUSH, map);
					}
					pagecounter++;
				}



			}
		});

	}

	@Override
	protected void onCancelled(Void result) {
		tts.stop();

		Log.e("Inside background", "Cancelled!");

	}

	@Override
	protected void onPreExecute() {

	}

	@Override
	protected Void doInBackground(Void... arg0) {
		if (pagecounter <= total) {
			String content=extracter.read(pagecounter);
			if (content != null) {
				tts.speak(content, TextToSpeech.QUEUE_FLUSH, map);
			} else {
				tts.speak("No Content Found on page " + pagecounter,
						TextToSpeech.QUEUE_FLUSH, map);
			}
			pagecounter++;
		}





		return null;
	}

}
