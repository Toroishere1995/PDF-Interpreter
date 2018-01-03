package com.example.pdftree;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FileArrayAdapter extends ArrayAdapter<File> {
	private final int id;
	ArrayList<File> files;

	public FileArrayAdapter(Context context, int resource,
			ArrayList<File> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.id = resource;
		this.files = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View veiw = convertView;
		
			LayoutInflater layoutFile = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			veiw = layoutFile.inflate(id, parent,false);
		
		File obj = files.get(position);
		if (obj != null) {
			ImageView image = (ImageView) veiw.findViewById(R.id.Image);
			TextView fileText = (TextView) veiw.findViewById(R.id.namedir);
			fileText.setText(obj.getName());
			if (obj.isDirectory()) {
				image.setImageResource(R.drawable.directory);
			} else {
				image.setImageResource(R.drawable.pdf);
			}

		}
		return veiw;
	}

}
