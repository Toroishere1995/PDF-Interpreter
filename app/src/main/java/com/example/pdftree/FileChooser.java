package com.example.pdftree;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class FileChooser extends ListActivity {
	private File CurrDir;
	private FileArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		CurrDir = new File("/");
		findFiles(CurrDir);
	}

	private void findFiles(File currDir2) {
		int k = 0;
		ArrayList<File> dir = new ArrayList<File>();
		ArrayList<File> files = new ArrayList<File>();
		File list[] = currDir2.listFiles();
		if(list!=null){
		for (k = 0; k < list.length; k++) {
			if (list[k].isDirectory()) {
				dir.add(list[k]);
			} else {
				if (String.valueOf(list[k]).endsWith(".pdf"))
					files.add(list[k]);
			}
		}
		Collections.sort(dir);
		Collections.sort(files);
		dir.addAll(files);
		adapter = new FileArrayAdapter(FileChooser.this, R.layout.listdesign,
				dir);
		this.setListAdapter(adapter);
	}}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		File filedir=(File)l.getItemAtPosition(position);
		
		if(filedir.isFile()){
			Intent intents=new Intent();
			intents.putExtra("GetPath",filedir.getAbsolutePath());
			setResult(RESULT_OK, intents);
			finish();
		}else{
			CurrDir=filedir;
			findFiles(CurrDir);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		if(CurrDir.getParentFile()!=null){
			CurrDir=CurrDir.getParentFile();
			findFiles(CurrDir);
		}
	}
}
