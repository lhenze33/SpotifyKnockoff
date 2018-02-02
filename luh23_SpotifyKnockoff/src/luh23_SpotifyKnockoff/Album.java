package luh23_SpotifyKnockoff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.UUID;
/** Represents an album for the SpotifyKnockoff application
 *
 * @author Luke Henze
 *
 */
public class Album {
	private String albumID;
	private String title;
	private String releaseDate;
	private String coverImagePath;
	private String recordingCompany;
	private int numberOfTracks;
	private String pmrcRating;
	private int length;
	private Hashtable<String, Song> albumSongs;

	/**
	 * Constructor for an album object and enters album into the database
	 * 
	 * @param title Title of the album
	 * @param releaseDate When the album was released
	 * @param recordingCompany Recording company the album belongs to
	 * @param numberOfTracks How many tracks are on the album
	 * @param pmrcRating PMRC Rating
	 * @param length How long the album is
	 */
	public Album(String title, String releaseDate, String recordingCompany, int numberOfTracks, String pmrcRating, int length) {
		this.albumID = UUID.randomUUID().toString();
		this.title = title;
		this.releaseDate = releaseDate;
		this.recordingCompany = recordingCompany;
		this.numberOfTracks = numberOfTracks;
		this.pmrcRating = pmrcRating;
		this.length = length;
		albumSongs = new Hashtable<String, Song>();

		String sql = "INSERT INTO album(album_id, title, release_date, cover_image_path, recording_company_name, number_of_tracks, PMRC_rating, length)"
				+ "VALUES (?,?,?,?,?,?,?,?);";

		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, this.albumID);
			ps.setString(2, this.title);
			ps.setString(3, this.releaseDate);
			ps.setString(4, "");
			ps.setString(5, this.recordingCompany);
			ps.setInt(6, this.numberOfTracks);
			ps.setString(7, this.pmrcRating);
			ps.setInt(8, this.length);
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
	 * Constructor to select an album all ready in the database
	 * @param albumID
	 */
	public Album(String albumID) {
		String sql = "SELECT * FROM album WHERE album_id = '" + albumID + "';";
		albumSongs = new Hashtable<String, Song>();
		DbUtilities db = new DbUtilities();
		try {
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				this.albumID = albumID;
				this.title = rs.getString("title");
				this.releaseDate = rs.getString("release_date");
				this.recordingCompany = rs.getString("recording_company_name");
				this.numberOfTracks = rs.getInt("number_of_tracks");
				this.pmrcRating = rs.getString("PMRC_rating");
				this.length = rs.getInt("length");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Deletes an album from the database
	 * @param albumID
	 */
	public void deleteAlbum(String albumID) {
		Album a = new Album(albumID);
		String sql = "DELETE FROM album WHERE album_id = '"+ albumID +"';";

		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
			db.closeDbConnection();
			db = null;
			a = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorLogger.log(e.getMessage());
		}
		System.out.println(sql);
	}
	/**
	 * Adds a song to an albums' list of songs and updates the album_song table
	 * in the database
	 * @param song
	 */
	public void addSong (Song song){
		albumSongs = new Hashtable<String, Song>();
		albumSongs.put(song.getSongID(), song);
		String sql = "INSERT INTO album_song (fk_album_id, fk_song_id) Values(?,?);";
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, this.albumID);
			ps.setString(2, song.getSongID());
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
	 * Removes a song from the albums list of songs based on songID and updates the table album_song
	 * in the database
	 * @param songID
	 */
	public void deleteSong(String songID) {
		String sql = "DELETE FROM album_song WHERE song_id = ?;";
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps;
			ps = conn.prepareStatement(sql);
			ps.setString(1, songID);
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
			albumSongs.remove(songID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorLogger.log(e.getMessage());
		}

	}
	/**
	 * Removes a song from the albums list of songs based on the song object's songID
	 * and updates the table album_song in the database
	 * @param song
	 */
	public void deleteSong(Song song) {
		String sql = "DELETE FROM album_song WHERE song_id = ?;";
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps;
			ps = conn.prepareStatement(sql);
			ps.setString(1, song.getSongID());
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
			albumSongs.remove(song.getSongID());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorLogger.log(e.getMessage());
		}
	}

	public String getCoverImagePath() {
		return coverImagePath;
	}
	/**
	 * Sets the file path for cover art of an album
	 * @param coverImagePath File path for the album's image
	 */
	public void setCoverImagePath(String coverImagePath) {
		this.coverImagePath = coverImagePath;
		String sql = "UPDATE album SET cover_image_path = ? WHERE album_id = ?;";

		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, this.coverImagePath);
			ps.setString(2, this.albumID);
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorLogger.log(e.getMessage());
		}
	}

	public String getAlbumID() {
		return albumID;
	}

	public String getTitle() {
		return title;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public String getRecordingCompany() {
		return recordingCompany;
	}

	public int getNumberOfTracks() {
		return numberOfTracks;
	}

	public String getPmrcRating() {
		return pmrcRating;
	}

	public int getLength() {
		return length;
	}

	public Hashtable<String, Song> getAlbumSongs() {
		return albumSongs;
	}


}
