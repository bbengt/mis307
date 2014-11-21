MIS 307 Music Searcher Project
======

Our music searching program will search through a music library for either an artist, an album, or a song.  The user specifies what type of search to do (artist, album, or song) and the program prints out any matches it finds.  The searches will also print out any partial matches it finds (see below for example input/output).  It also doesn't matter if the music library has spaces or underscores in names (or both), it will still catch matches.

NOTE: This program assumes the path you provide it is the root of the library (e.g. there's a folder for each artist in the folder at the end of the path provided).  It also expects every song to be on an album (not loose within an artist folder) and ignores any folders past the album level (<path>/Artist/Album/<this directory will be ignored>/)

Sample Input/Output:

What are you looking for (artist, album, song)? artist
Enter search term: The Beatles
What's the path to your music library? C:\Music

C:\Music\The Beatles\Sgt. Pepper's Lonely Hearts Club Band

13 A Day In the Life.m4a
04 Getting Better.m4a
09 When I'm Sixty-Four.m4a
05 Fixing a Hole.m4a
01 Sgt. Pepper's Lonely Hearts Club.m4a
12 Sgt. Pepper's Lonely Hearts Club.m4a
08 Within You Without You.m4a
11 Good Morning Good Morning.m4a
07 Being For the Benefit of Mr. Kite.m4a
06 She's Leaving Home.m4a
03 Lucy In the Sky With Diamonds.m4a
02 With a Little Help From My Friend.m4a
10 Lovely Rita.m4a

C:\Music\The Beatles\Help!

5-13 Yesterday.m4a

What are you looking for (artist, album, song)? song
Enter search term: Yest
What's the path to your music library? C:\Music

C:\Music\Guns_N'_Roses\Greatest Hits\11 Yesterdays.m4a
C:\Music\The Beatles\Help!\5-13 Yesterday.m4a