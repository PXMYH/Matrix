/*
 * Matrix Style
 * Author: Michael Hu
 * Date: February 4, 2014
 * Copyright (c) ProjectX 
 */

/*
 * To Do: 
 * 2. adding more characters
 * 3. adding textures
 * 4. green color shining
 * 5. distribution, make an running applet
 */


import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import acm.graphics.*;
import acm.program.*;
import acm.util.ErrorException;
import acm.util.RandomGenerator;
import acmx.export.java.io.FileReader;

public class matrix extends GraphicsProgram {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int random_number;
	private static double start_x = 1.0;
	private static double start_y = 2.0;
	private static double dx = 3.0;
	private static double dy;
	
	// random generator declaration
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	// ArrayList holding display content
	private ArrayList <GLabel> number_list;
	private ArrayList <String> char_list = new ArrayList<String>();;
	
	// main running function
	public void run(){
		
		// set background to be black
		setBackground(Color.BLACK);
		
		number_list = new ArrayList<GLabel>();
		
		String normal_digit, upside_down_digit, character;
		
		extractChar("Char_Japan.txt");
		extractChar("Char_Greek.txt");
		extractChar("Char_Digit.txt");
		
		while (true) {
			// generate Matrix code content
			normal_digit = genDigit();
			upside_down_digit = genUpDownDigit();
			character = genChar();
			
			// display normal digital number across screen
			//display(normal_digit);
			
			// display upside-down digital number across screen
			//display(upside_down_digit);
			
			// display characters
			display(character);
			
		}
		
	}
	
	private String genDigit() {
		double pos_vert = start_x;
		String display_number = "";
		
		// 21.0 is optimized for MacBook Pro 13-inch Retina Display 
		while (pos_vert < (getWidth() - 21.0)) {
			random_number = rgen.nextInt(0,9);
			display_number += "        " + random_number;
			pos_vert += dx;
		}
		
		return display_number;
	}
	
	private String genUpDownDigit() {
		double pos_vert = start_x;
		String up_down_digit = "";
		
		while (pos_vert < (getWidth() - 21.0)) {
			random_number = rgen.nextInt(0,9);
			up_down_digit += "        " + random_number;
			pos_vert += dx;
		}
		
		return up_down_digit;
	}
	
	private String genChar() {
		String character = "";
		double pos_vert = start_x;
		int index = 0;
		
		while (pos_vert < (getWidth() - 21.0)){
			index = rgen.nextInt(0,char_list.size()-1);
			character += "        " + char_list.get(index);
			pos_vert += dx;
		}
		
		return character;
	}
	
	private void extractChar(String file_name) {
		
		// read in Japanese characters
		String line;
		BufferedReader rd = openFile(file_name);
		
		try {
			while (true) {
				line = rd.readLine();
				if (line == null) break;
				
				// add characters to ArrayList
				char_list.add(line);
			}
			// close file
			rd.close();
		} catch (IOException ex) {
			throw new ErrorException(ex);
		}
		
	}
	
	
	private void display(String content) {
		
		// create symbol
		GLabel symbol = new GLabel (content, start_x, start_y);
		symbol.setColor(Color.GREEN);
		symbol.setFont("Calibri-10");
		
		// move the number down one letter space
		dy = symbol.getHeight();
		for (int index = 0; index < number_list.size(); index ++) {
			number_list.get(index).move(0, dy);
		}
		
		// attach number to the canvas in one column
		add(symbol);
		number_list.add(symbol);
	}
	
	private BufferedReader openFile(String file_name) {
		BufferedReader rd = null;
		
		while (rd == null) {
			try {
				//rd = new BufferedReader(new FileReader(file_name));
				rd = new BufferedReader(new InputStreamReader(new FileInputStream(file_name), "UTF-8"));
			} catch (IOException ex){
				throw new ErrorException(ex);
			}
		}
		
		return rd;
	}
	
}