package luh23_SpotifyKnockoff;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
/**Represents a song for the Spotify Knockoff application
 * 
 * @author Luke Henze
 *
 */
public class Song {
	private String songID;
	private String title;
	private double length;
	private String filePath;
	private String releaseDate;
	private String recordDate;
	private Hashtable<String, Artist> songArtists;
	
	/**
	 * Constructor for a new song that sets all relevant instance variables and inserts 
	 * the new song into the database
	 * 
	 * @param title Title of the song
	 * @param length Length of the song in minutes
	 * @param releaseDate When the song was released
	 * @param recordDate When the song was recorded
	 */
	public Song(String title, double length, String releaseDate, String recordDate){
		this.title = title; 
		this.length = length;
		this.releaseDate = releaseDate;
		this.recordDate = recordDate;
		this.songID = UUID.randomUUID().toString();
		songArtists = new Hashtable<String, Artist>();


		String sql = "INSERT INTO song (song_id,title,length,file_path,release_date,record_date) ";
		sql += "VALUES (?, ?, ?, ?, ?, ?);";

		//System.out.println(sql);

		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, this.songID);
			ps.setString(2,  this.title);
			ps.setDouble(3, this.length);
			ps.setString(4, "");
			ps.setString(5, this.releaseDate);
			ps.setString(6, this.recordDate);
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorLogger.log(e.getMessage());
		}

	}
	/**
	 * Constructor to select a song all ready in the database
	 * @param songID
	 */
	public Song(String songID){
		String sql = "SELECT * FROM song WHERE song_id = '" + songID + "';";
		songArtists = new Hashtable<String, Artist>();
		try {
			DbUtilities db = new DbUtilities();
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				this.songID = rs.getString("song_id");
				this.title = rs.getString("title");
				this.releaseDate = rs.getDate("release_date").toString();
				this.recordDate = rs.getDate("record_date").toString();
				this.length = rs.getDouble("length");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorLogger.log(e.getMessage());
		}

	}

	/**
	 * Deleting a song from the program and the database
	 * @param songID
	 */
	public void deleteSong(String songID) {
		Song s = new Song(songID);
		String sql = "DELETE FROM song WHERE song_id = '"+ songID +"';";

		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
			db.closeDbConnection();
			db = null;
			s = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorLogger.log(e.getMessage());
		}
		System.out.println(sql);
	}
	/**
	 * Adds an artist to a list of the song's artist and updates the table song_artist
	 * in the database
	 * @param artist
	 */
	public void addArtist(Artist artist) {
		songArtists.put(artist.getArtistID(), artist);
		String sql = "INSERT INTO song_artist (fk_song_id, fk_artist_id) Values(?,?);";
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, this.songID);
			ps.setString(2, artist.getArtistID());
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorLogger.log(e.getMessage());
		}

	}
	/**
	 * Deletes an artist from the list of the song's artists and updates the table 
	 * song_artist in the database based on artistID
	 * @param artistID
	 */
	public void deleteArtist(String artistID) {
		String sql = "DELETE FROM song_artist WHERE artist_id = ?;";
		
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps;
			ps = conn.prepareStatement(sql);
			ps.setString(1, artistID);
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
			songArtists.remove(artistID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorLogger.log(e.getMessage());
		}
		
	}
	/**
	 * Deletes an artist from the list of the song's artists and updates the table
	 * song_artist in the database based on the object artist artistID
	 * @param artist
	 */
	public void deleteArtist(Artist artist) {
		String sql = "DELETE FROM song_artist WHERE artist_id = ?;";

		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps;
			ps = conn.prepareStatement(sql);
			ps.setString(1, artist.getArtistID());
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
			songArtists.remove(artist.getArtistID());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorLogger.log(e.getMessage());
		}
		
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public String getSongID() {
		return songID;
	}

	public String getTitle() {
		return title;
	}

	public double getLength() {
		return length;
	}

	public String getFilePath() {
		return filePath;
	}
	/**
	 * Setting a file path for a song object and updating its' record in the database
	 * @param filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
		String sql = "UPDATE song SET file_path = ? WHERE song_id = ?;";
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, this.filePath);
			ps.setString(2, this.songID);
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorLogger.log(e.getMessage());
		}

	}
	public String getRecordDate() {
		return recordDate;
	}

}
