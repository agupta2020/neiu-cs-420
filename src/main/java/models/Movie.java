package models;

import java.util.Date;
import java.util.Objects;

public class Movie extends Media{
    private Date publish_date;
    private String reviewer;

    public Movie(String category, String title, String short_summary, Date publish_date, String reviewer) {
        super(title, category, short_summary);
        this.publish_date = publish_date;
        this.reviewer = reviewer;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Movie betaMovie = (Movie) o;
        return Objects.equals(publish_date, betaMovie.publish_date) &&
                Objects.equals(reviewer, betaMovie.reviewer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), publish_date, reviewer);
    }
}
