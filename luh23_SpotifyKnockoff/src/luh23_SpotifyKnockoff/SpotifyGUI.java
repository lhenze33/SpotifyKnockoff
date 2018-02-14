package luh23_SpotifyKnockoff;

import java.awt.Color;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SpotifyGUI {

	private JFrame frame;
	private JTextField txtSearch;
	private ButtonGroup btnGroup;
	private JRadioButton radShowAlbums;
	private JRadioButton radShowArtists;
	private JRadioButton radShowSongs;
	private JTable tblData;
	private DefaultTableModel musicData;

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
		frame.getContentPane().setLayout(null);
		
		JLabel lblViewSelector = new JLabel("Select View");
		lblViewSelector.setBounds(20, 30, 99, 16);
		frame.getContentPane().add(lblViewSelector);
		
		btnGroup = new ButtonGroup();
		
		radShowAlbums = new JRadioButton("Albums");
		radShowAlbums.setSelected(true);
		btnGroup.add(radShowAlbums);
		radShowAlbums.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(radShowAlbums.isSelected()){
					musicData = searchAlbums(txtSearch.getText());
					
					tblData.setModel(musicData);
				}
			}
		});
		radShowAlbums.setBounds(40, 60, 150, 25);
		frame.getContentPane().add(radShowAlbums);
		
		
		radShowArtists = new JRadioButton("Artists");
		btnGroup.add(radShowArtists);
		radShowArtists.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(radShowArtists.isSelected()){
					musicData = searchArtists(txtSearch.getText());
					tblData.setModel(musicData);
				}
			}
		});
		radShowArtists.setBounds(40, 85, 150, 25);
		frame.getContentPane().add(radShowArtists);
		
		
		radShowSongs = new JRadioButton("Songs");
		btnGroup.add(radShowSongs);
		radShowSongs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(radShowSongs.isSelected()){
					musicData = searchSongs(txtSearch.getText());
					tblData.setModel(musicData);
				}
			}
		});
		radShowSongs.setBounds(40, 110, 150, 25);
		frame.getContentPane().add(radShowSongs);
		
		
		JLabel lblSearch = new JLabel("Search");
		lblSearch.setBounds(20, 290, 100, 20);
		frame.getContentPane().add(lblSearch);
		
		frame.getContentPane().add(lblViewSelector);
		txtSearch = new JTextField();
		txtSearch.setBounds(20, 315, 200, 30);
		frame.getContentPane().add(txtSearch);
		txtSearch.setColumns(10);
		
		musicData = searchAlbums("");
		tblData = new JTable(musicData);
		new JScrollPane(tblData, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tblData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		tblData.setBounds(299, 45, 600, 400);
		tblData.setFillsViewportHeight(true);
		tblData.setShowGrid(true);
		tblData.setGridColor(Color.BLACK);
		frame.getContentPane().add(tblData);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(radShowSongs.isSelected()){
					musicData = searchSongs(txtSearch.getText());
					tblData.setModel(musicData);
				}else if(radShowArtists.isSelected()){
					musicData = searchArtists(txtSearch.getText());
					tblData.setModel(musicData);
				}else if(radShowAlbums.isSelected()) {
					musicData = searchAlbums(txtSearch.getText());
					tblData.setModel(musicData);
				}
			}
		});
		
		btnSearch.setBounds(103, 357, 117, 29);
		
		frame.getContentPane().add(btnSearch);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public DefaultTableModel searchSongs(String searchTerm){
		String sql = "SELECT song_id, title, length, release_date, record_date FROM song ";
		if(!searchTerm.equals("")){
				sql += " WHERE title LIKE '%" + searchTerm + "%';";
		}
		
		try {
			DbUtilities db = new DbUtilities();
			String[] columnNames = {"Song ID", "Title", "Length", "Release Date", "Record Date"};
			return db.getDataTable(sql, columnNames);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "Unable to connect to database");
			ErrorLogger.log(e.getMessage());
		}
		return null;
	}
	public DefaultTableModel searchArtists(String searchTerm){
		String sql = "SELECT artist_id, first_name, last_name, band_name, bio FROM artist ";
		if(!searchTerm.equals("")){
				sql += " WHERE band_name LIKE '%" + searchTerm + "%' OR first_name LIKE '%"+searchTerm+"%' "
						+ "OR last_name LIKE '%"+searchTerm+"%';";
		}
		
		try {
			DbUtilities db = new DbUtilities();
			String[] columnNames = {"Artist ID", "First Name", "Last Name", "Band Name", "Bio"};
			return db.getDataTable(sql, columnNames);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "Unable to connect to database");
			ErrorLogger.log(e.getMessage());
		}
		return null;
	}
	public DefaultTableModel searchAlbums(String searchTerm){
		String sql = "SELECT album_id, title, release_date, recording_company_name FROM album ";
		if(!searchTerm.equals("")){
				sql += " WHERE title LIKE '%" + searchTerm + "%';";
		}
		
		try {
			DbUtilities db = new DbUtilities();
			String[] columnNames = {"Album ID", "Title", "Release Date", "Record Company"};
			return db.getDataTable(sql, columnNames);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "Unable to connect to database");
			ErrorLogger.log(e.getMessage());
		}
		return null;
	}
	
	
	
}

