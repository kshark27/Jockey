package com.marverenic.music.pages;


import android.app.Activity;
import android.database.Cursor;
import android.provider.MediaStore;

import com.marverenic.music.instances.Genre;
import com.marverenic.music.instances.Song;
import com.marverenic.music.utils.Debug;

import java.util.ArrayList;

public class GenrePage {

    public static void onCreate (Object parent, ArrayList<Song> songEntries, Activity activity) {
        if (activity.getActionBar() != null) activity.getActionBar().setTitle(((Genre) parent).genreName);
        else Debug.log(Debug.LogLevel.WTF, "LibraryPageActivity", "Couldn't find the action bar", activity);

        Cursor cur = activity.getContentResolver().query(
                MediaStore.Audio.Genres.Members.getContentUri("external", ((Genre) parent).genreId),
                new String[]{
                        MediaStore.Audio.Genres.Members.TITLE,
                        MediaStore.Audio.Genres.Members.ARTIST,
                        MediaStore.Audio.Genres.Members.ALBUM,
                        MediaStore.Audio.Genres.Members.DURATION,
                        MediaStore.Audio.Genres.Members.DATA,
                        MediaStore.Audio.Genres.Members.ALBUM_ID},
                MediaStore.Audio.Media.IS_MUSIC + " != 0 ", null, null);
        cur.moveToFirst();

        for (int i = 0; i < cur.getCount(); i++) {
            cur.moveToPosition(i);
            songEntries.add(new Song(
                    cur.getString(cur.getColumnIndex(MediaStore.Audio.Playlists.Members.TITLE)),
                    cur.getString(cur.getColumnIndex(MediaStore.Audio.Playlists.Members.ARTIST)),
                    cur.getString(cur.getColumnIndex(MediaStore.Audio.Playlists.Members.ALBUM)),
                    cur.getInt(cur.getColumnIndex(MediaStore.Audio.Playlists.Members.DURATION)),
                    cur.getString(cur.getColumnIndex(MediaStore.Audio.Playlists.Members.DATA)),
                    cur.getString(cur.getColumnIndex(MediaStore.Audio.Playlists.Members.ALBUM_ID))));
        }
        cur.close();
    }

}
