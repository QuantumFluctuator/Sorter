package com.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	private final static int WIDTH = 1200;
	private final static int HEIGHT = 800;
	private static int MAXDATA = WIDTH;
	private static int DATAHEIGHT = HEIGHT;
	
	static boolean running;
	static int passes;
	static boolean complete;
	
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		JFrame window;
		JPanel panel;
		Graphics g;
		int[] data;
		
		window = new JFrame("Sorter");
		panel = new JPanel();
		
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		window.setContentPane(panel);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		g = panel.getGraphics();
		
		data = new int[MAXDATA];
		
		running = true;
		complete = false;
				
		while (running) {
			update(data, g, -1, -1, -1);
			passes = 0;
			
			int in;
			do {
				printMenu();
				in = sc.nextInt();
			} while (!valid(in));
			
			System.out.println();
			
			switch (in) {
			case 1:
				initialiseData(data);
				break;
			case 2:
				complete = false;
				while (!complete) {
					bubbleSort(data, g);
				}
				break;
			case 3:
				complete = false;
				while (!complete) {
					insertionSort(data, g);
				}
				break;
			case 4:
				complete = false;
				quickSort(data, 0, MAXDATA - 1, g);
				break;
			case 9:
				boolean wantsToLeave = false;
				int input = 0;
				while (!wantsToLeave) {
					printOptions();
					if (sc.hasNextInt()) {
						input = sc.nextInt();
					}
					switch (input) {
					case 1:
						MAXDATA = getDataAmount();
						update(data, g, -1, -1, -1);
						break;
					case 0:
						wantsToLeave = true;
						break;
					default:
						break;
					}
				}
				break;
			case 0:
				running = false;
				break;
			default:
				break;
			}
		}
		
		sc.close();
		System.exit(0);
	}
	
	private static void update(int[] data, Graphics g, int a, int b, int c) {
		Graphics2D g2d = (Graphics2D) g;
		
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D imageGraphics = image.createGraphics();
		
		imageGraphics.setColor(Color.BLACK);
		imageGraphics.fillRect(0, 0, WIDTH, HEIGHT);
		
		imageGraphics.setColor(Color.GREEN);
		int i = 0;
		for (int x : data) {
			if (i == a || i == b || i == c) {
				imageGraphics.setColor(Color.RED);
			}
			imageGraphics.fillRect(i*WIDTH/MAXDATA, HEIGHT - x, WIDTH/MAXDATA, x);
			if (i == a || i == b || i == c) {
				imageGraphics.setColor(Color.GREEN);
			}
			i++;
		}
		
		imageGraphics.setColor(Color.RED);
		imageGraphics.drawString("Number of data items: " + MAXDATA, 16, 16);
		imageGraphics.drawString("Max height of data items: " + DATAHEIGHT, 16, 32);
		imageGraphics.drawString("Passes: " + passes, 16, 48);
		
		if (complete) {
			imageGraphics.drawString("Pass complete!", 16, 64);
		}
		
		g2d.drawImage(image, 0, 0, null);
	}
	
	private static void initialiseData(int[] data) {
		for (int i = 0; i < MAXDATA; i++) {
			data[i] = (int)(Math.random() * DATAHEIGHT);
		}
	}
	
	private static void bubbleSort(int[] data, Graphics g) {
		complete = true;
		for (int i = 0; i < MAXDATA - (1 + passes); i++) {
			if (data[i] > data[i + 1]) {
				int temp = data[i + 1];
				data[i + 1] = data[i];
				data[i] = temp;
				complete = false;
			}
			update(data, g, i+1, i, -1);
		}
		passes++;
	}
	
	private static void quickSort(int[] data, int min, int max, Graphics g) {
	    if (min < max) {
	        int p = partition(data, min, max, g);
	        quickSort(data, min, p - 1, g);
	        quickSort(data, p + 1, max, g);
	    }
	}
	
	private static int partition(int[] data, int min, int max, Graphics g) {
		passes++;
	    int pivot = data[max];
	    int i = min;
	    for (int j = min; j <= max - 1; j++) {
	    	update(data, g, i, j, -1);
	    	complete = false;
	        if (data[j] < pivot) {
	        	int temp = data[i];
	        	data[i] = data[j];
	        	data[j] = temp;
	            i = i + 1;
	        }
	    }
	    int temp = data[i];
    	data[i] = data[max];
    	data[max] = temp;
    	complete = true;
	    return i;
	}
	
	private static void insertionSort(int[] data, Graphics g) {
		int i = 1;
		while (i < data.length) {
		    int j = i;
		    while (j > 0 && data[j-1] > data[j]) {
		    	int temp = data[j];
		    	data[j] = data[j-1];
		    	data[j-1] = temp;
		    	update(data, g, j, j-1, -1);
		        j--;
		    }
		    i++;
		    passes++;
		}
	}
	
	private static boolean valid(int x) {
		return x == 0 || x == 1 || x == 2 || x == 3 || x == 4 || x == 9;
	}
	
	private static void printMenu() {
		System.out.println("  1 to initialise data randomly\n  2 for Bubble Sort\n  3 for Insertion Sort\n  4 for Quick Sort\n  9 to edit options\n  0 to quit");
		System.out.print("Enter an option: ");
	}
	
	private static void printOptions() {
		System.out.println("  1 to change max number of data items\n  0 to return to main menu");
		System.out.print("Enter an option: ");
	}
	
	private static int getDataAmount() {
		System.out.print("Enter an amount of data items, must be < 1200: ");
		int out = sc.nextInt();
		return out;
	}
}
