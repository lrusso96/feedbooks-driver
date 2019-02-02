package lrusso96.feedbooks.driver.core;

import lrusso96.feedbooks.driver.models.Book;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Result
{
    private final URI endpoint;
    private final  String query;
    private LocalDate updated;
    private int page;
    private Set<Book> books;

    Result(URI endpoint, String query)
    {
        books = new HashSet<>();
        this.endpoint = endpoint;
        this.query = query;
    }

    URI getEndpoint()
    {
        return endpoint;
    }

    String getQuery()
    {
        return query;
    }

    public LocalDate getUpdated()
    {
        return updated;
    }

    public void setUpdated(LocalDate updated)
    {
        this.updated = updated;
    }

    int getPage()
    {
        return page;
    }

    void setPage(int page)
    {
        this.page = page;
    }

    public Set<Book> getBooks()
    {
        return books;
    }

    void addBooks(Set<Book> more){
        books.addAll(more);
    }

    @Override
    public String toString()
    {
        return "Result{" + "endpoint=" + endpoint + ", query='" + query + '\'' + ", updated=" + updated + ", page=" + page + ", books=" + books + '}';
    }
}
