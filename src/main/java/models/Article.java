package models;

import java.util.Date;
import java.util.Objects;

public class Article extends Media{

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Article that = (Article) o;
        return wordCount == that.wordCount &&
                Objects.equals(source, that.source) &&
                Objects.equals(section_name, that.section_name) &&
                Objects.equals(publish_date, that.publish_date) &&
                Objects.equals(documentType, that.documentType) &&
                Objects.equals(materialType, that.materialType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), source, section_name, publish_date, documentType, materialType, wordCount);
    }

    private final String source;
    private final String section_name;
    private final Date publish_date;
    private final String documentType;
    private final String materialType;
    private final int wordCount;

    public Article(String title, String category, String short_summary, String source, String section_name, Date publish_date, String documentType, String materialType, int wordCount) {
        super(title, category, short_summary);
        this.source = source;
        this.section_name = section_name;
        this.publish_date = publish_date;
        this.documentType = documentType;
        this.materialType = materialType;
        this.wordCount = wordCount;
    }



    public String getSection_name() {
        return section_name;
    }

    public Date getPublish_date() {
        return publish_date;
    }
}
