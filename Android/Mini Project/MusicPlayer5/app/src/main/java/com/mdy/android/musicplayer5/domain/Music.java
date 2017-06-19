package com.mdy.android.musicplayer5.domain;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by MDY on 2017-06-14.
 */

public class Music {
    private static Music instance = null;
    // 중복을 방지하기 위해 데이터 저장소의 형태를 Set 으로 설정
    private Set<Item> items = null;

    // HashSet은 Value로만 된다.
    private Music() {
        items = new HashSet<>();
    }

    public static Music getInstance(){
        if(instance == null)
            instance = new Music();

        return instance;
    }

    // HashSet은 Value로만 되어서 ArrayList에 담아주면 인덱스랑 값이 저장된다.
    public List<Item> getItems(){
        return new ArrayList<>(items);
    }

    // 음악 데이터를 폰에서 꺼낸다음 List 저장소에 담아둔다.
    public void loader(Context context) {   // 컨텐트 리졸버는 context에서 꺼내쓸 수 있다.

        // 0. 먼저 Album Art 가 있는 테이블에서 전체 이미지를 조회해서 저장해 둔다
        setAlbumArt(context);
        // (음악ID, 앨범아트 이미지경로) = hashMap
        // (     1, /sdcard/exteranl/0/media..... image.jpg)
        // (     2, /sdcard/exteranl/0/media..... image3.jpg)



        // items.clear(); Set을 사용함으로 중복을 방지할 수 있다
        ContentResolver resolver = context.getContentResolver();

        // 1. 테이블 명 정의 ?
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        // 2. 가져올 컬럼명 정의
        String proj[] = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST
        };
        // 3. 쿼링~
        Cursor cursor = resolver.query(uri, proj, null, null, null);
        if(cursor != null){
            while(cursor.moveToNext()){
                Item item = new Item();
                item.id = getValue(cursor, proj[0]);
                item.albumId = getValue(cursor, proj[1]);
                item.title = getValue(cursor, proj[2]);
                item.artist = getValue(cursor, proj[3]);

                // 음원 Uri
                item.musicUri = makeMusicUri(item.id);
                // 앨범아트 가져오기
                item.albumArt = albumMap.get(Integer.parseInt(item.albumId));
                // 저장소에 담는다...
                items.add(item);
            }
        }
        // 메모리 누수가 발생하지 않도록 커서 꼭 닫을 것
        cursor.close();
    }

    private String getValue(Cursor cursor, String name){
        int index = cursor.getColumnIndex(name);
        return cursor.getString(index);
    }

    // Set 이 중복값을 허용하지 않도록 equals 와 hashCode를 활용한다
    public class Item {
        public String id;
        public String albumId;
        public String artist;
        public String title;

        public Uri musicUri;
        public String albumArt;

        public boolean itemClicked = false;

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
                        // contentUri를 Log로 찍어보면 content://media/external/audio/media
        return Uri.withAppendedPath(contentUri, musicId);
    }

    // 앨범아트 데이터만 따로 저장
    private static HashMap<Integer, String> albumMap = new HashMap<>();

    private static void setAlbumArt(Context context) {
        String[] Album_cursorColumns = new String[]{
                MediaStore.Audio.Albums.ALBUM_ART, //앨범아트
                MediaStore.Audio.Albums._ID
        };
        Cursor Album_cursor = context.getContentResolver().query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                Album_cursorColumns, null, null, null);
        if (Album_cursor != null) { //커서가 널값이 아니면
            if (Album_cursor.moveToFirst()) { //처음참조
                int albumArt = Album_cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
                int albumId = Album_cursor.getColumnIndex(MediaStore.Audio.Albums._ID);
                do {
                    if (!albumMap.containsKey(Integer.parseInt(Album_cursor.getString(albumId)))) { //맵에 앨범아이디가 없으면
                        albumMap.put(Integer.parseInt(Album_cursor.getString(albumId)), Album_cursor.getString(albumArt)); //집어넣는다
                    }
                } while (Album_cursor.moveToNext());
            }
        }
        Album_cursor.close();
    }

    /*private Uri makeAlbumUri(String albumId){
        String albumUri = "content://media/external/audio/albumart/";
        return Uri.parse(albumUri + albumId);
    }*/

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
