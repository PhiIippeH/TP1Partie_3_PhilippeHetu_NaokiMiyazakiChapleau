package main;


import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FicLecture {

	public FicLecture() {
		
	}
	public List<String> fileChoose(){
		// TODO Auto-generated method stub
		List<String> lignes = new ArrayList<String>();
		boolean old = UIManager.getBoolean("FileChooser.readOnly");
		UIManager.put("FileChooser.readOnly", Boolean.TRUE);
		JFileChooser jFileChooser = new JFileChooser();
		UIManager.put("FileChooser.readOnly", old);
		jFileChooser.setFileFilter(new FileNameExtensionFilter(".txt", "txt"));
		if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			Path file = Paths.get(jFileChooser.getSelectedFile().getPath());
			try {
				lignes = Files.readAllLines(file,Charset.forName("ISO-8859-1"));
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
		return lignes;
	}

}
