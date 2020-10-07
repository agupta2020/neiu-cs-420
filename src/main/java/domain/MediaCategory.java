package domain;

public enum MediaCategory {
    MOVIE("Top Movies Critics Choice",3),
    ARTICLE("Most Shared Articles", 1),
    BOOK("Top Rated Books", 2);


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
