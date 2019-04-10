package com.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	private final static int WIDTH = 1200;
	private final static int HEIGHT = 800;
	private final static int MAXDATA = WIDTH;
	private final static int DATAHEIGHT = HEIGHT;
	
	static boolean running;
	static int passes;
	
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
		
		initialiseData(data);
		
		while (running) {
			update(data, g);
		}
	}
	
	private static void update(int[] data, Graphics g) {
		running = !bubbleSort(data);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(Color.GREEN);
		int i = 0;
		for (int x : data) {
			g.drawLine(i, HEIGHT, i, HEIGHT - x);
			i++;
		}
		
		g.setColor(Color.RED);
		g.drawString("Number of data items: " + MAXDATA, 16, 16);
		g.drawString("Max height of data items: " + DATAHEIGHT, 16, 32);
		g.drawString("Passes: " + passes, 16, 48);
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static void initialiseData(int[] data) {
		for (int i = 0; i < MAXDATA; i++) {
			data[i] = (int)(Math.random() * DATAHEIGHT);
		}
	}
	
	private static boolean bubbleSort(int[] data) {
		boolean complete = true;
		for (int i = 0; i < MAXDATA - 1; i++) {
			if (data[i] > data[i + 1]) {
				int temp = data[i + 1];
				data[i + 1] = data[i];
				data[i] = temp;
				complete = false;
			}
		}
		passes++;
		return complete;
	}
}
