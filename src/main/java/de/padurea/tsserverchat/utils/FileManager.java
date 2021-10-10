package de.padurea.tsserverchat.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author Noel91x
 * 
 * @informations
 * CopyRight by Padurea.de,
 * do not distribute. 
 * 
 * */
public class FileManager {
	
	private File file;
	private Path path;
	
	public FileManager(File file) {
		this.file = file;
		this.setPath(Paths.get(file.getPath()));
	}
	
	public FileManager(String filePath) {
		this(new File(filePath));
	}
	
	public void createFile() {
		try {
			file.mkdirs();
			file.delete();
			file.createNewFile();
		} catch (IOException e) {}
	}
	
	public void write(String name, String value) throws IOException {
		FileWriter fw = new FileWriter(file, true);//Append
		fw.write(name + ": " + value + "\n");
		fw.flush(); //just makes sure that any buffered data is written to disk
		fw.close(); //flushes the data and indicates that there isn't any more data.
	}
	
	public void overwrite(String name, String value) throws IOException {
		if (!getValueNames().contains(name)) {
			FileWriter fw = new FileWriter(file, true);//Append
			fw.write(name + ": " + value + "\n");
			fw.flush();
			fw.close();
		} else {
			for (int i = 0; i < getCompleteValues().size(); i++) {
				String currentText = getCompleteValues().get(i);
				if (currentText.split(": ")[0].equals(name)) {
					List<String> lines = getCompleteValues();
					lines.set(i, name + ": " + value);
					file.delete();
					file.createNewFile();
					Files.write(path, lines);
					break;
				}
			}
		}
	}
	
	@SuppressWarnings("resource")
	public String getValue(String name) {
		Scanner scanner;
		String currentLine = "";
		
		try {
			scanner = new Scanner(file);
			
			while (scanner.hasNextLine()) {
				currentLine = scanner.nextLine();
				if (name.equals(currentLine.split(": ")[0])) {
					try {
						return currentLine.split(": ")[1];
					} catch (ArrayIndexOutOfBoundsException e) {
						return "";
					}
				}
				if (!scanner.hasNextLine()) {
					scanner.close();
					break;
				}
			}
		} catch (IOException | IllegalStateException e) {}
		return null;
	}
	
	public List<String> getValueNames() {
		List<String> list = new ArrayList<String>();
		Scanner scanner;
		
		try {
			scanner = new Scanner(file);
			
			while (scanner.hasNextLine()) {
				list.add(scanner.nextLine().split(": ")[0]);
				if (!scanner.hasNextLine()) {
					scanner.close();
					break;
				}
			}
		} catch (IOException | IllegalStateException e) {}
		return list;
	}
	
	public List<String> getCompleteValues() {
		List<String> list = new ArrayList<String>();
		Scanner scanner;
		
		try {
			if (!file.exists()) {
				return null;
			}
			scanner = new Scanner(file);
			
			while (scanner.hasNextLine()) {
				list.add(scanner.nextLine());
				if (!scanner.hasNextLine()) {
					scanner.close();
					break;
				}
			}
		} catch (IOException | IllegalStateException e) {}
		return list;
	}
	
	public boolean fileExist() {
		return file.exists();
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}
	
	public File getFile() {
		return file;
	}
}
