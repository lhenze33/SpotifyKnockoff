package luh23_SpotifyKnockoff;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SpotifyManager {

	public static void main(String[] args) {
		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("luh23_SpotifyKnockoff");
		EntityManager emanager = emfactory.createEntityManager();
		
		emanager.getTransaction().begin();
		Song s = emanager.find(Song.class, "be766dc7-5973-4e8e-8359-a68d7164e24d");
		s.setTitle("Friend of the Devil");
		//s.setFilePath("songs/friendofthedevil.mp3");
		//emanager.remove(s);
		
		//Album a = emanager.find(Album.class, "10ea48a7-c541-4f5f-a174-e82bc9c4607e");
		//a.setCoverImagePath("this/is/the/file/path.mp3");
		
		//Artist a = emanager.find(Artist.class, "e5c53d6d-25cd-4e47-82a9-b06974f72d9d");
		//a.setBio("this is the bio");
		
		//emanager.persist(s);
		//emanager.persist(a);
		//emanager.persist(a);
		emanager.persist(s);
		emanager.getTransaction().commit();
		emanager.close();
		emfactory.close();
		

	}

}
