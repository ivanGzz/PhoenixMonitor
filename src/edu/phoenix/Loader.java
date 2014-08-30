package edu.phoenix;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Loader {

	/**
	* Regex for positions
	*/
	private Pattern position_regex;
	/**
	* Regex for observer
	*/
	private Pattern observer_regex;
	/**
	* Buffered reader
	*/
	private BufferedReader reader;
	/**
	* List of last positions loaded
	*/
	private Vector<Position> positions = new Vector<>();
	/**
	* Las position loaded for the observer
	*/
	private Position observer;
	
	/**
	* Loader constructor
	* @param file File object
	*/
	public Loader(File file) {
		this.position_regex = Pattern.compile("\\(([\\d\\.\\-e]+),\\s*([\\d\\.\\-e]+),\\s*(\\d+),\\s*([\\d\\.\\-e]+),\\s*([u|v])\\)");
		this.observer_regex = Pattern.compile("\\(([\\d\\.\\-e]+),\\s*([\\d\\.\\-e]+),\\s*([\\d\\.\\-e]+)\\)");
		try {
			DataInputStream player_in = new DataInputStream(new FileInputStream(file));
			this.reader = new BufferedReader(new InputStreamReader(player_in));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	* Load next cycle
	*/
	boolean loadNextCycle() {
		this.positions = new Vector<>();
		String line;
		try {
			if ((line = this.reader.readLine()) != null) {
				Matcher observer_matcher = this.observer_regex.matcher(line);
				if (observer_matcher.find()) {
					double x = Double.parseDouble(observer_matcher.group(1));
					double y = Double.parseDouble(observer_matcher.group(2));
					double d = Double.parseDouble(observer_matcher.group(3));
					this.observer = new Position(x, y, d);
				}
				Matcher position_matcher = this.position_regex.matcher(line);
				while (position_matcher.find()) {
					double x = Double.parseDouble(position_matcher.group(1));
					double y = Double.parseDouble(position_matcher.group(2));
					int id = Integer.parseInt(position_matcher.group(3));
					String visible = position_matcher.group(5);
					Position pos = new Position(x, y);
					pos.id = id;
					if (visible.equals("v")) {
						pos.inSight = true;
					} else {
						pos.inSight = false;
					}
					this.positions.add(pos);
				}
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// This statement will only be executed if the try fails
		return false;
	}
	
	/**
	* Get the last observer position loaded
	* @return Observer position 
	*/
	Position getNewObserverPosition() {
		return this.observer;
	}
	
	/**
	* Get the last vector of positions loaded
	* @return Vector of positions
	*/
	Vector<Position> getNewPositionList() {
		return this.positions;
	}
	
}
