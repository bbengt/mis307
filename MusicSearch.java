package musicsearch;
import java.io.File;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Searches through a user-provided music library for an artist, album, or song and prints out any matches it finds
 * @author Brian Bengtson, Dan Bodtke, Brandy Byrd, Jerry Chen, Carson Scovin
 *
 */
public class MusicSearch 
{
	public static void main(String[] args) 
	{
		
		Scanner in = new Scanner(System.in);
		
		while(true) 
		{
			System.out.print("What are you looking for (artist, album, song)? If you are finished, enter \"quit\": ");
			String searchBy = in.nextLine();

			if(searchBy.equalsIgnoreCase("quit"))
			{
				System.out.println("_________________________________________________________________________________________________________________________________________________________________________________________________________________");
				
				break;

			}
			
			if(!Arrays.asList("artist", "album", "song").contains(searchBy.toLowerCase())) 
			{
				System.out.println("_________________________________________________________________________________________________________________________________________________________________________________________________________________");
				System.out.println();
				System.out.println("Invalid search type.");
				System.out.println("_________________________________________________________________________________________________________________________________________________________________________________________________________________");
				System.out.println();

				continue;

			}
			
			System.out.print("What's the path to your music library? ");
			String path = in.nextLine();
			
			// Ensure specified music library path exists
				File f = new File(path);
				if(!f.exists()) 
				{
					System.out.println("_________________________________________________________________________________________________________________________________________________________________________________________________________________");
					System.out.println();
					System.out.println("Specified path doesn't exist.");
					System.out.println("_________________________________________________________________________________________________________________________________________________________________________________________________________________");
					System.out.println();
								
					continue;

				}
						
				// Ensure specified music library is a directory
				if(!f.isDirectory()) 
				{
					System.out.println("_________________________________________________________________________________________________________________________________________________________________________________________________________________");
					System.out.println();
					System.out.println("Specified path isn't a directory.");
					System.out.println("_________________________________________________________________________________________________________________________________________________________________________________________________________________");
					System.out.println();
								
					continue;

				}
				
			System.out.print("Enter search term: ");
			String term = in.nextLine();
			System.out.println("_________________________________________________________________________________________________________________________________________________________________________________________________________________");
			System.out.println();
				
			if(searchBy.equalsIgnoreCase("artist")) 
			{
				boolean success = artistSearch(term, path);
				if(!success)
				{
					System.out.println("Artist not found.");
					System.out.println("_________________________________________________________________________________________________________________________________________________________________________________________________________________");
					System.out.println();
				}		

				continue;

			} 
			
			else if(searchBy.equalsIgnoreCase("album")) 
			{
				boolean success = albumSearch(term, path);
				if(!success)
				{ 
					System.out.println("Album not found.");
					System.out.println("_________________________________________________________________________________________________________________________________________________________________________________________________________________");
					System.out.println();
				}	

				continue;

			} 
			
			else if(searchBy.equalsIgnoreCase("song")) 
			{
				boolean success = songSearch(term, path);
				if(!success)
				{
					System.out.println("Song not found.");
					System.out.println("_________________________________________________________________________________________________________________________________________________________________________________________________________________");
					System.out.println();
				}	

				continue;

			} 
			
			else 
			{
				System.out.println();
				System.out.println("Invalid input.");
				System.out.println("_________________________________________________________________________________________________________________________________________________________________________________________________________________");
				System.out.println();
			}
		}
		in.close();
	}
	
	/**
	 * Searches through the music library specified in 'path' for any artists that contain the string in 'artist'
	 * @param artist
	 * 	Term to look for
	 * @param path
	 * 	Path to music library to search
	 * @return
	 * 	true if any matches are found
	 * 	false if no matches are found
	 */
	private static boolean artistSearch(String artist, String path) 
	{
		if(artist == null) 
		{
			System.out.println("Invalid artist.");
			return false;
		}
		if(!new File(path).exists()) 
		{
			System.out.println("Invalid search path.");
			return false;
		}
		
		ArrayList<String> list = new ArrayList<String>();

		for(File file : new File(path).listFiles()) 
		{

			// Artist should be a directory
			if(file.isDirectory()) 
			{
				// Filename contains (ignoring case) artist name or if Filename has underscores instead of spaces
				if(file.getName().toLowerCase().contains(artist.toLowerCase())||file.getName().toLowerCase().contains(artist.toLowerCase().replaceAll(" ", "_"))) 
				{
					
					//Replace all underscores in the artist name with spaces before printing out
					String name = file.getName().replaceAll("_", " ");
					
					// Only print artist name if we haven't already found this artist (ex. library had the artist with underscores and spaces both)
					if(!(list.contains(file.getPath().replaceAll("_", " ")) || list.contains(file.getPath().replaceAll(" ", "_")))) {
						System.out.println("The artist, " + name + ", was found.");
					}
					
					list.add(file.getPath());
				}
			}
		}

		// If we've gotten through all files/folders in the specified path and haven't found the artist, it doesn't exist
		if(list.isEmpty()) 
			return false;
		
		for(String artistName : list) 
		{
			for(File album : new File(artistName).listFiles()) 
			{
				
				// Ignore loose files (songs not in a album/directory)
				if(!album.isDirectory()) 
					continue;				
				
				System.out.println();
				System.out.println("Album: " + album.getName().replaceAll("_", " "));
				System.out.println();
				System.out.println("The path to this album is: " + album.getPath());
				System.out.println();
				System.out.println("The content found in " + album.getName().replaceAll("_", " ") + " includes: ");
				for(File song : album.listFiles()) {
					
					// Skip directories
					if(!song.isFile()) {
						continue;
					}
					System.out.println(song.getName());
				}
			}
		}
		System.out.println("_________________________________________________________________________________________________________________________________________________________________________________________________________________");
		System.out.println();
		
		return true;
	}
	
	/**
	 * Searches through the music library specified in 'searchPath' for the string in 'album'
	 * @param album
	 * 	Term to search for
	 * @param searchPath
	 * 	Path to search in
	 * @return
	 * 	true if a match is found
	 * 	false if no match is found
	 */
	private static boolean albumSearch(String album, String searchPath) 
	{
		boolean success = false;

		for(File artists : new File(searchPath).listFiles()) 
		{	
			// Ignore any loose files in the search path
			if(!artists.isDirectory()) {
				continue;
			}
			
			for(File albums : artists.listFiles())
			{
				if(albums.isDirectory())
				{
					// Album name matches (ignore case) with spaces or underscores
					if(albums.getName().toLowerCase().contains(album.toLowerCase())||albums.getName().toLowerCase().contains(album.toLowerCase().replaceAll(" ", "_")))
					{
						System.out.println("The album, " + albums.getName().replaceAll("_", " ") + ", was found.");
						System.out.println();
						System.out.println("The path to this album is: " + albums.getPath());
						System.out.println();
						System.out.println("The content found in " + albums.getName().replaceAll("_", " ") + " includes: ");
							
						for(File song : albums.listFiles()) 
						{
							if(!song.isFile())
								continue;
				
							System.out.println(song.getName());
							
							success = true;
						}
					}	
				}
			}
		}
		if(success == true)
		{
			System.out.println("_________________________________________________________________________________________________________________________________________________________________________________________________________________");
			System.out.println();	
		}

		
		return success;
	}
	
	/**
	 * Recursively searches for the string in 'song' in the music library in 'path'
	 * @param song
	 * 	Term to search for
	 * @param path
	 * 	Path to search in
	 * @return
	 * 	true if a match is found
	 * 	false if no match is found
	 */
	private static boolean songSearch(String song, String path) 
	{
		if(song == null) 
		{
			System.out.println("Invalid song.");
			return false;
		}
		if(!new File(path).exists()) 
		{
			System.out.println("Invalid path.");
			return false;
		}
		
		boolean success = false;
		
		File dir = new File(path);
		
		for(File file : dir.listFiles()) 
		{
			if(file.isDirectory()) 
			{
				if(success) 
					songSearch(song, file.getPath());
				
				else 
					success = songSearch(song, file.getPath());
			}
			if(file.isFile()) 
			{
				// Filename includes (ignore case) the search term (matches either spaces or underscores)
				if(file.getName().toLowerCase().contains(song.toLowerCase())||file.getName().toLowerCase().contains(song.toLowerCase().replaceAll(" ", "_"))) 
				{
					System.out.println("The song, " + file.getName() + ", was found.");
					System.out.println();
					System.out.println("The path to this song is " + file.getPath());
					System.out.println("_________________________________________________________________________________________________________________________________________________________________________________________________________________");
					System.out.println();
					success = true;	
				}
			}
		}
		return success;
	}
}