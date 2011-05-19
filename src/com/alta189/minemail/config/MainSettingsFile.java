package com.alta189.minemail.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.alta189.minemail.MineMail;

public class MainSettingsFile {
	private MineMail plugin;
	public String fileName = "settings.properties";
	public File file;
	@SuppressWarnings("unused")
	private HashMap<String,String> FileContents = new HashMap<String,String>();
	
	//Declare Settings\\
	public Integer primaryColor = 10;
	public Integer secondaryColor = 12;
	public Integer headerColor = 8;
	public Integer errorColor = 7;
	
	
	public MainSettingsFile(MineMail instance) {
		this.plugin = instance;
	}

	private void create() {
		//First we are going to load the file from within the jar in the package com.alta189.minemail.config.defaults
		String contents = null;
		try {
			InputStream is = MainSettingsFile.class.getResourceAsStream("defaults/Settings.properties");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
		    
			String line = null;
			
			
			while (null != (line = br.readLine())) {
		         contents = contents + line;
		    }
			if (is != null) is.close();
			if (br != null) br.close();
		} catch (IOException ex) {
			plugin.log.severe(plugin.logPrefix + "Error loading settings.properties file. Contact alta189!");
			return;
		}
		
		try {
			
		    
			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(contents);
			
		} catch (IOException ex) {
			plugin.log.severe(plugin.logPrefix + "Error creating settings.properties file!");
			return;
		}
		
	}
	
	public void load(Boolean force) { //If force is true than it will delete the file and recreate it
		if (!file.exists() || force) {
			if (force) this.delete();
			create();
		}
		
		this.FileContents = loadFileContents();
		
	}
	
	private void delete() {
		File delFile = file;
		
		if (delFile.exists()) {
			delFile.delete();
		}
	}

	private HashMap<String,String> loadFileContents(){
		HashMap<String,String> result = new HashMap<String,String>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String word = null;
			
			while((word = br.readLine()) != null) {
				if ((word.isEmpty()) || (word.startsWith("#")) || (!word.contains(":"))) continue;
				String[] args = word.split(":");
				result.put(args[0], args[1]);
			}
			
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			plugin.log.severe(plugin.logPrefix + "Error loading settings.properties file!");
		}
		
		return result;
	}
	
	public String getPropertyString(String property){
		if (!file.exists()) {
			load(false);
		}
		try {
			if (FileContents.containsKey(property)) {
				return FileContents.get(property);
			}
			
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public Boolean getPropertyBoolean(String property) {
		if (!file.exists()) {
			load(false);
		}
		try {
			String result = FileContents.get(property);
			if (result.equalsIgnoreCase("true") || result.equalsIgnoreCase("false")) {
				return Boolean.valueOf(result.toLowerCase());
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
		
	}

	public Integer getPropertyInteger(String property) {
		if (!file.exists()) {
			load(false);
		}
		try {
			String result = FileContents.get(property);
			//StruckDown.log.info(property + "=" + result);
			return Integer.valueOf(result);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 10;
	}
}
