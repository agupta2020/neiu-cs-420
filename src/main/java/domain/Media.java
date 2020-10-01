package domain;

import java.util.*;

public abstract class Media {


    private String category;
    private String title;

    protected Media(String title, String category) {
        this.category = category;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return this.title;
    }

    public static Map<String, List<Media>> getMediaMap(String Category, List<Media> mediaList) {
        Map<String, List<Media>> groupedMap = new HashMap<>();
        for (Media media : mediaList) {
            addToMap(Category, media, groupedMap);
        }
        return groupedMap;
    }

    private static void addToMap(String Category, Media media, Map<String, List<Media>> map) {
        if (!map.containsKey(Category))
            map.put(Category, new ArrayList<>(Arrays.asList(media)));
        else
            map.get(Category).add(media);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Media media = (Media) o;
        return Objects.equals(category, media.category) &&
                Objects.equals(title, media.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, title);
    }
}