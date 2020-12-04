package models;

public enum MediaCategory {
    ARTICLE("SearchedArticle", 1),
    BOOK("BestBooks", 2),
    MOVIE("MovieReview", 3);


    private  final  String mediaDetails;
    private  final  int position;

    MediaCategory(String mediaDetails, int position){
     this.mediaDetails=mediaDetails;
     this.position=position;
    }

    public int getPosition() {
        return position;
    }

    public String getMediaDetails() {
        return mediaDetails;
    }
}
