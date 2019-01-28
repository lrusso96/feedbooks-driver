package lrusso96.feedbooks.driver.core;

import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

public class Book
{
    private int id;
    private String title;
    private Author author;
    private String summary;
    private LocalDate published;
    private LocalDate updated;
    private int issued;
    private Locale language;
    private Category[] categories;
    private URI download;
    private URI cover;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Author getAuthor()
    {
        return author;
    }

    public void setAuthor(Author author)
    {
        this.author = author;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public LocalDate getPublished()
    {
        return published;
    }

    public void setPublished(LocalDate published)
    {
        this.published = published;
    }

    public LocalDate getUpdated()
    {
        return updated;
    }

    public void setUpdated(LocalDate updated)
    {
        this.updated = updated;
    }

    public Locale getLanguage()
    {
        return language;
    }

    public void setLanguage(Locale language)
    {
        this.language = language;
    }

    public Category[] getCategories()
    {
        return categories;
    }

    public void setCategories(Category[] categories)
    {
        this.categories = categories;
    }

    public URI getDownload()
    {
        return download;
    }

    public void setDownload(URI download)
    {
        this.download = download;
    }

    public URI getCover()
    {
        return cover;
    }

    public void setCover(URI cover)
    {
        this.cover = cover;
    }

    public int getIssued()
    {
        return issued;
    }

    public void setIssued(int issued)
    {
        this.issued = issued;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Book book = (Book) o;
        return id == book.id &&
                Objects.equals(title, book.title) &&
                Objects.equals(author, book.author) &&
                Objects.equals(summary, book.summary) &&
                Objects.equals(published, book.published) &&
                Objects.equals(updated, book.updated) &&
                Objects.equals(issued, book.issued) &&
                Objects.equals(language, book.language) &&
                Arrays.equals(categories, book.categories) &&
                Objects.equals(download, book.download) &&
                Objects.equals(cover, book.cover);
    }

    @Override
    public int hashCode()
    {
        int result = Objects.hash(id, title, author, summary, published, updated, issued, language, download, cover);
        result = 31 * result + Arrays.hashCode(categories);
        return result;
    }

    @Override
    public String toString()
    {
        return "Book{" + "id=" + id + ", title='" + title + '\'' + ", author=" + author + ", summary='" + summary + '\'' + ", published=" + published + ", updated=" + updated + ", issued=" + issued + ", language=" + language + ", categories=" + Arrays.toString(categories) + ", download=" + download + ", cover=" + cover + '}';
    }


}
