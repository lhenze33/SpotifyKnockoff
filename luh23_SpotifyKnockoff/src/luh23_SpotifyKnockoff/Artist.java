package luh23_SpotifyKnockoff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**Represents an artist for the Spotify Knockoff application
 * 
 * @author Luke Henze
 *
 */

@Entity
@Table(name="artist")
public class Artist {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	@Column(name="artist_id")
	private String artistID;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="band_name")
	private String bandName;
	
	@Column(name="bio")
	private String bio;
	
	/**
	 * Default constructor for JPA
	 */
	public Artist() {
		super();
	}
	
	/**
	 * Creates a new artist object and adds it to the database
	 * @param Unique identifier for the artist
	 * @param firstName First name of the artist
	 * @param lastName Last Name of the artist
	 * @param bandName Band name of the artist
	 */
	public Artist(String firstName, String lastName, String bandName) {
		this.artistID = UUID.randomUUID().toString();
		this.firstName = firstName;
		this.lastName = lastName;
		this.bandName = bandName;
	
		String sql = "INSERT INTO artist (artist_id,first_name,last_name,band_name,bio) ";
		sql += "VALUES (?, ?, ?, ?, ?);";
		
		//System.out.println(sql);
		
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);;
			ps = conn.prepareStatement(sql);
			ps.setString(1, this.artistID);
			ps.setString(2,  this.firstName);
			ps.setString(3, this.lastName);
			ps.setString(4, this.bandName);
			ps.setString(5, "");
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
	 * @param artistID
	 */
	public Artist(String artistID) {
		String sql = "SELECT * FROM artist WHERE artist_id = '" + artistID + "';";
		
		try {
			DbUtilities db = new DbUtilities();
			ResultSet rs = db.getResultSet(sql);
			while(rs.next()){
				this.artistID = rs.getString("artist_id");
				this.firstName = rs.getString("first_name");
				this.lastName = rs.getString("last_name");
				this.bandName = rs.getString("band_name");
				this.bio = rs.getString("bio");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorLogger.log(e.getMessage());
		}
	}
	/**
	 * Deleting an artist from the database and program based on artistID
	 * @param artistID
	 */
	public void deleteArtist(String artistID) {
		Artist a = new Artist(artistID);
		String sql = "DELETE FROM artist WHERE artist_id = '"+ artistID +"';";
		DbUtilities db = new DbUtilities();
		try {
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
	}
	
	public String getBio() {
		return bio;
	}
	/**
	 * Setting a biography for an artist object and adding it to the database
	 * @param bio
	 */
	public void setBio(String bio) {
		this.bio = bio;
		String sql = "UPDATE artist SET bio = ? WHERE artist_id = ?;";
		try {
			DbUtilities db = new DbUtilities();
			Connection conn = db.getConn();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, this.bio);
			ps.setString(2, this.artistID);
			ps.executeUpdate();
			db.closeDbConnection();
			db = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ErrorLogger.log(e.getMessage());
		}
	}

	public String getArtistID() {
		return artistID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getBandName() {
		return bandName;
	}
	
}
