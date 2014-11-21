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
 * 	-Find how to return a boolean with songSearch() - if we have time
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
				
				songSearch(term, path);
				
			} else {
				System.out.println("Invalid search type.");
				System.out.println();
			}
			
		}
		in.close();

	}
	
	private static boolean artistSearch(String artist, String searchPath) {

		ArrayList<String> list = new ArrayList<String>();

		// Search through list of files & folders to look for artist
		for(String filename : new File(searchPath).list()) {
			
			File file = new File(searchPath + "/" + filename);

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
		
		for(String path : list) {
			
			// Get list of files under artist's directory
			String[] albums = new File(path).list();
			for(String albumName : albums) {
				
				File album = new File(path + "/" + albumName);
				
				// Skip any non-directories
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
	
	private static void songSearch(String song, String path) {
		
		File dir = new File(path);
		for(String file : dir.list()) {
			File f = new File(path + "/" + file);
			if(f.isDirectory()) {
				songSearch(song, f.getPath());
			}
			if(f.isFile()) {
				if(f.getName().toLowerCase().contains(song.toLowerCase())) {
					System.out.println(f.getPath());
					continue; // Skip to next iteration of loop, otherwise we'll get a duplicate result printed with next if statement
				}
				if(f.getName().toLowerCase().contains(song.toLowerCase().replaceAll(" ", "_"))) {
					System.out.println(f.getPath());
				}
			}
		}
		
	}
	
}
