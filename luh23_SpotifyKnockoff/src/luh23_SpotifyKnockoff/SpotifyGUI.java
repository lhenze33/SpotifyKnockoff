package luh23_SpotifyKnockoff;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class SpotifyGUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SpotifyGUI window = new SpotifyGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SpotifyGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Spotify");
		frame.setBounds(100, 100, 1000, 600);
		frame.setLayout(null);//forcing everything to be set in terms of pixels
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
