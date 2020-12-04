package models;

import java.util.Date;
import java.util.Objects;

public class Book extends  Media {

    private Date publish_date;
    private String book_type;
    private String rank;

    public Book(String category, String title, String short_summary, Date publish_date, String book_type, String rank) {
        super(title,category, short_summary);
        this.publish_date = publish_date;
        this.book_type = book_type;
        this.rank = rank;
    }



    public String getBook_type() {
        return book_type;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Book betaBook = (Book) o;
        return Objects.equals(publish_date, betaBook.publish_date) &&
                Objects.equals(book_type, betaBook.book_type) &&
                Objects.equals(rank, betaBook.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), publish_date, book_type, rank);
    }
}
