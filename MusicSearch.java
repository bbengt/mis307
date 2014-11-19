package musicsearch;

import java.io.File;
import java.util.Scanner;

public class MusicSearch {

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		
		while(true) {
			System.out.print("What are you looking for (artist, album)? ");
			String searchBy = in.next();
			System.out.print("Enter search term: ");
			String term = in.next();
			System.out.print("What's the path to your music library? ");
			String path = in.next();
			
			if(searchBy.equalsIgnoreCase("artist")) {
				boolean success = artistSearch(term, path);
				if(!success) {
					System.out.println("Artist not found.");
				}
				break;
			} else if(searchBy.equalsIgnoreCase("album")) {
				//boolean success = albumSearch(term, path);
				System.out.println("Album");
				break;
			} else {
				System.out.println("Invalid search type.");
				System.out.println();
			}
			
		}
		in.close();
		
		
	}
	
	public static boolean artistSearch(String artist, String searchPath) {

		String pathToArtist = null;

		// Get list of all files & folders in path specified
		File searchPathFolder = new File(searchPath);
		String[] searchPathFiles = searchPathFolder.list();

		// Search through list of files & folders to look for artist
		for(String filename : searchPathFiles) {
			
			String absolutePath = searchPath + "/" + filename;
			File file = new File(absolutePath);

			// Artist should be a directory
			if(file.isDirectory()) {
				if(file.getName().contains(artist)) {
					pathToArtist = file.getPath();
					break;
				}
			}
		}

		// If we've gotten through all files/folders in the current path and haven't found the artist, it doesn't exist
		if(pathToArtist == null) {
			return false;
		}
		
		File artistRoot = new File(pathToArtist);

		// Get list of albums under artist
		String[] albums = artistRoot.list();
		for(String albumName : albums) {
			
			String absAlbumPath = artistRoot + "/" + albumName;
			File album = new File(absAlbumPath);

			// Skip any non-directories
			if(!album.isDirectory()) {
				continue;
			}

			File[] songs = album.listFiles();

			// Make sure there's stuff in the album
			if(songs.length == 0) {
				continue;
			}
			
			
			System.out.println("");
			System.out.println(album.getPath());
			System.out.println("");

			for(File song : songs) {
				// Only print out files
				if(!song.isFile()) {
					continue;
				}
				System.out.println(song.getName());
			}

		}


		return true;
	}
}
