package models;

import java.util.*;

public abstract class Media {

    private String category;
    private String title;
    private String short_summary;

    protected Media(String title, String category,String short_summary) {
        this.category = category;
        this.title = title;
        this.short_summary=short_summary;
    }

    public String getTitle() {
        return title;
    }

    public String getShort_summary() {
        return short_summary;
    }


    @Override
    public String toString() {
        return this.title;
    }

      public static Map<MediaCategory, List<Media>> getMediaMap(MediaCategory mediaCategory, List<Media> mediaList) {
        Map<MediaCategory, List<Media>> groupedMap = new HashMap<>();
        for (Media media : mediaList) {
            addToMap(mediaCategory, media, groupedMap);
        }
        return groupedMap;
    }

    private static void addToMap(MediaCategory mediaCategory, Media media, Map<MediaCategory, List<Media>> map) {
        if (!map.containsKey(mediaCategory))
            map.put(mediaCategory, new ArrayList<>(Collections.singletonList(media)));
        else
            map.get(mediaCategory).add(media);
    }


}