package edu.phoenix;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
* @author Nelson Gonzalez
* @version 1.0
*/
public class Monitor extends JFrame implements Runnable, ActionListener {

	/**
	* Serial Version UID
	*/
	private static final long serialVersionUID = 1L;

	/**
	* Reference to PCanvas object
	*/
	private PCanvas canvas;
	/**
	* Indicates if the main thread is running
	*/
	private boolean run = false;
	/**
	* Reference to rewind button
	*/
	private JButton rewind;
	/**
	* Reference to play button
	*/
	private JButton play;
	/**
	* Reference to backward button
	*/
	private JButton backward;
	/**
	* Reference to forward button
	*/
	private JButton forward;
	/**
	* Reference to Data object
	*/
	private Data data;
	/**
	* Reference to file chooser object
	*/
	private final JFileChooser fc = new JFileChooser();
	/**
	* Reference to Loader object
	*/
	private Loader loader;
	
	/**
	* Monitor constructor
	*/
	public Monitor() {
		this.canvas = new PCanvas();
//		this.data = new Data(this.canvas);
		JPanel jplMaster = new JPanel(new BorderLayout());
		JPanel upPanel = new JPanel(new BorderLayout());	
		upPanel.add(this.canvas, BorderLayout.WEST);
		jplMaster.add(upPanel, BorderLayout.NORTH);
		jplMaster.add(this.createButtons(), BorderLayout.SOUTH);
		this.setJMenuBar(this.createMenus());
		this.getContentPane().add(jplMaster, BorderLayout.CENTER);
	}
	
	/**
	* Creates all the buttons
	* @return panel of buttons
	*/
	private JPanel createButtons() {
		JPanel downPanel = new JPanel(new BorderLayout());
		JPanel dpLeft = new JPanel(new BorderLayout());
		JPanel dpRight = new JPanel(new BorderLayout());
		this.rewind = new JButton(new ImageIcon(this.getClass().getResource("rewind.png")));
		this.play = new JButton(new ImageIcon(this.getClass().getResource("play.png")));
		/* Backward button */
		this.backward = new JButton(new ImageIcon(this.getClass().getResource("backward.png")));
		this.backward.setActionCommand("backward");
		this.backward.addActionListener(this);
		/* Forward button */
		this.forward = new JButton(new ImageIcon(this.getClass().getResource("forward.png")));
		this.forward.setActionCommand("forward");
		this.forward.addActionListener(this);
		dpLeft.add(this.rewind, BorderLayout.WEST);
		dpLeft.add(this.play, BorderLayout.EAST);
		dpRight.add(this.forward, BorderLayout.EAST);
		dpRight.add(this.backward, BorderLayout.WEST);
		downPanel.add(dpLeft, BorderLayout.WEST);
		downPanel.add(dpRight, BorderLayout.EAST);
		return downPanel;
	}
	
	/**
	* Creates all the menus
	* @return Menu bar with menus
	*/
	private JMenuBar createMenus() {
		JMenuBar menuBar = new JMenuBar();
		/* Monitor */
		JMenu monitor = new JMenu("Monitor");
		JMenuItem exit = new JMenuItem("Exit");
		exit.setActionCommand("exit");
		exit.addActionListener(this);
		monitor.add(exit);
		menuBar.add(monitor);
		/* Data */
		JMenu data = new JMenu("Data");
		JMenuItem load = new JMenuItem("Load...");
		load.setActionCommand("load");
		load.addActionListener(this);
		data.add(load);
		menuBar.add(data);
		return menuBar;
	}
	
	/**
	* {@inheritDoc}
	*/
	@Override
	public void run() {
		this.run = true;
		while (this.run) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.canvas.repaint();
		}
	}
	
	/**
	* {@inheritDoc}
	*/
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "forward":
			if (this.loader != null) this.data.onForward();
			break;
		case "backward":
			if (this.loader != null) this.data.onBackward();
			break;
		case "load":
			int returnVal = this.fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            this.loader = new Loader(file);
	            this.data = new Data(this.canvas, this.loader);
			}
			break;
		case "exit":
			System.exit(0);
			break;
		}
	}
	
	public static void main(String[] args) {
		Monitor monitor = new Monitor();
		monitor.setTitle("Phoenix Monitor");
		monitor.pack();
		monitor.setLocation(300, 60);
		monitor.setVisible(true);
		monitor.setResizable(false);
	}
	
}
