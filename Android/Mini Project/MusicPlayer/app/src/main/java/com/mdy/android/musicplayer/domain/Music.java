package com.mdy.android.musicplayer.domain;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by MDY on 2017-06-14.
 */

public class Music {
    private static Music instance = null;
    private Set<Item> items = null;

    private Music() {
        items = new HashSet<>();
    }

    public Music getInstance(){
        if(instance == null)
            instance = new Music();
        return instance;
    }

    public Set<Item> getItems(){
        return items;
    }

    // 음악 데이터를 폰에서 꺼낸 다음 List 저장소에 담아둔다.
    public void loader(Context context){ // 컨텐트 리졸버는 context에서 꺼내쓸 수 있다.

        // 데이터가 계속 쌓이는 것을 방지한다.
        items.clear();

        ContentResolver resolver = context.getContentResolver();

        // 1. 테이블명 정의
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        // 2. 가져올 컬럼명 정의
        String projection[] = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST
        };

        // 3. 쿼리
        Cursor cursor = resolver.query(uri, projection, null, null, null);
        if(cursor != null){
            while(cursor.moveToNext()){
                Item item = new Item();
                item.id = getValue(cursor, projection[0]);
                item.albumId = getValue(cursor, projection[1]);
                item.title = getValue(cursor, projection[2]);
                item.artist = getValue(cursor, projection[3]);

                item.musicUri = makeMusicUri(item.id);
                item.albumArt = makeAlbumUri(item.albumId);

                // 저장소에 담는다...
                items.add(item);
            }
        }
    }

    private String getValue(Cursor cursor, String name){
        int index = cursor.getColumnIndex(name);
        return cursor.getString(index);
    }


    // Set 이 중복값을 허용하지 않도록 equals 와 hashCode를 활용한다.
    public class Item {
        String id;
        String albumId;
        String artist;
        String title;

        Uri musicUri;
        Uri albumArt;


        @Override
        public boolean equals(Object item) {
            // null 체크
            if(item == null) return false;
            // 객체 타입 체크
            if (!(item instanceof Item)) return false;
            // 키값의 hashcode 비교
            return id.hashCode() == item.hashCode();
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }
    }

    private Uri makeMusicUri(String musicId){
        Uri contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        return Uri.withAppendedPath(contentUri, musicId);
    }

    private Uri makeAlbumUri(String albumId){
        String albumUri = "content://media/external/audio/albumart/";
        return Uri.parse(albumUri + albumId);
    }

}






/*
public static class DummyItem {
    public final String id;
    public final String content;
    public final String details;

    public DummyItem(String id, String content, String details) {
        this.id = id;
        this.content = content;
        this.details = details;
    }

    @Override
    public String toString() {
        return content;
    }
}*/
