package com.example.pdftree;

import android.util.Log;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.io.IOException;

//import com.itextpdf.kernel.pdf.PdfReader;
//import java.io.IOException;

public class PDFileReader {
	private String currentname;

	public PDFileReader(String currentFileName) {
		// TODO Auto-generated constructor stub
		this.currentname = currentFileName;
	}

	public String read(int i) {
		String retrieveText = null;
		try {
			PdfReader pdr = new PdfReader(currentname);
			int NoOfPages = pdr.getNumberOfPages();
			if (i <= NoOfPages) {
				retrieveText = PdfTextExtractor.getTextFromPage(pdr, i);

				return retrieveText;
			} else {
				return null;
			}

		} catch (Exception e) {
			return "Bang On,It's a Image....Wait Loading next page.";
		}

	}
	public int getNoPages(){
		PdfReader pdr = null;
		try {
			pdr = new PdfReader(currentname);

			int NoOfPages = pdr.getNumberOfPages();
return NoOfPages;
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}

	}
}
