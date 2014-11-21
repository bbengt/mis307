package musicsearch;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * What's left to do: 
 * 	-Implement album search
 * 	-Javadoc
 * 	-Code clean-up
 * 	-Testing/bugfixes
 * 
 */

public class MusicSearch {

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		
		while(true) {
			
			System.out.print("What are you looking for (artist, album, song)? ");
			String searchBy = in.nextLine();
			System.out.print("Enter search term: ");
			String term = in.nextLine();
			System.out.print("What's the path to your music library? ");
			String path = in.nextLine();
			
			// Ensure specified music library path exists
			File f = new File(path);
			if(!f.exists()) {
				System.out.println("Specified path doesn't exist.");
				continue;
			}
			
			// Ensure specified music library is a directory
			if(!f.isDirectory()) {
				System.out.println("Specified path isn't a directory.");
				continue;
			}
			
			if(searchBy.equalsIgnoreCase("artist")) {
				
				boolean success = artistSearch(term, path);
				if(!success) {
					
					System.out.println("Artist not found.");
					
				}
				break;
				
			} else if(searchBy.equalsIgnoreCase("album")) {
				
				boolean success = albumSearch(term, path);
				if(!success) {
					System.out.println("Album not found.");
				}
			
				break;
			} else if(searchBy.equalsIgnoreCase("song")) {
				
				boolean success = songSearch(term, path);
				if(!success) {
					System.out.println("Song not found.");
				}
				
				break;
				
			} else {
				System.out.println("Invalid search type.");
				System.out.println();
			}
			
		}
		in.close();

	}
	
	private static boolean artistSearch(String artist, String searchPath) {
		
		if(artist == null) {
			System.out.println("Invalid artist.");
			return false;
		}
		if(!new File(searchPath).exists()) {
			System.out.println("Invalid search path.");
			return false;
		}

		ArrayList<String> list = new ArrayList<String>();

		for(File file : new File(searchPath).listFiles()) {

			// Artist should be a directory
			if(file.isDirectory()) {
				
				// Filename contains (ignoring case) artist name
				if(file.getName().toLowerCase().contains(artist.toLowerCase())) {
					list.add(file.getPath());
				}
				
				// Filename has underscores instead of spaces
				if(file.getName().toLowerCase().contains(artist.toLowerCase().replaceAll(" ", "_"))) {
					list.add(file.getPath());
				}
			}
		}

		// If we've gotten through all files/folders in the specified path and haven't found the artist, it doesn't exist
		if(list.isEmpty()) {
			return false;
		}
		
		// Search through each artist that matched the search pattern
		for(String path : list) {
			
			// Search through each album by the artist
			for(File album : new File(path).listFiles()) {
				
				// Skip any non-directories (e.g. file in artist folder but not in an album folder)
				if(!album.isDirectory()) {
					continue;
				}
				
				System.out.println();
				System.out.println(album.getPath());
				System.out.println();
				
				for(File song : album.listFiles()) {
					
					// Skip directories
					if(!song.isFile()) {
						continue;
					}
					System.out.println(song.getName());
				}
			}
		}

		return true;
	}
	
	private static boolean albumSearch(String album, String path) {
		return false;
	}
	
	private static boolean songSearch(String song, String path) {
		
		if(song == null) {
			System.out.println("Invalid song.");
			return false;
		}
		if(!new File(path).exists()) {
			System.out.println("Invalid path.");
			return false;
		}
		
		boolean success = false;
		
		File dir = new File(path);
		for(File file : dir.listFiles()) {

			if(file.isDirectory()) {
				if(success) {
					songSearch(song, file.getPath());
				} else {
					success = songSearch(song, file.getPath());
				}
			}
			if(file.isFile()) {
				if(file.getName().toLowerCase().contains(song.toLowerCase())) {
					System.out.println(file.getPath());
					success = true;	
					continue;
				}
				if(file.getName().toLowerCase().contains(song.toLowerCase().replaceAll(" ", "_"))) {
					System.out.println(file.getPath());
					success =  true;
				}
			}
		}
		return success;
	}
	
}
