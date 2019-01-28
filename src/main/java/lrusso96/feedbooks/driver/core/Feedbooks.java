package lrusso96.feedbooks.driver.core;

import lrusso96.feedbooks.driver.exceptions.FeedbooksException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static lrusso96.feedbooks.driver.core.Utils.parseUTC;

public class Feedbooks
{
    //defaults
    private static final int DEFAULT_MAX_RESULTS = 10;

    //supported lang: en, it, es, de and fr.
    private final Locale[] languages;
    private final int maxResults;

    public Feedbooks(Locale[] languages, Integer maxResults)
    {
        this.languages = languages == null?  new Locale[]{ new Locale("en") } : languages;
        this.maxResults = maxResults == null? DEFAULT_MAX_RESULTS : maxResults;

    }

    public Book[] search(String query) throws FeedbooksException
    {
        String endpoint = "https://feedbooks.com/books/search.atom";
        try
        {
            Connection connection = Jsoup.connect(endpoint).data("query", query);
            for(Locale locale : languages)
                connection = connection.data("lang", locale.getLanguage());
            Document doc = connection.get();
            Elements entries = doc.getElementsByTag("entry");
            int cnt = maxResults;
            List<Book> ret = new ArrayList<>();
            for (Element entry : entries)
            {
                if (cnt-- == 0)
                    break;
                Book book = new Book();
                book.setTitle(entry.getElementsByTag("title").text());
                book.setSummary(entry.getElementsByTag("summary").text());
                book.setPublished(parseUTC(entry.getElementsByTag("published").text()));
                book.setUpdated(parseUTC(entry.getElementsByTag("updated").text()));
                book.setLanguage(new Locale(entry.getElementsByTag("dcterms:language").text()));

                Author author = new Author();
                author.setFullName(entry.getElementsByTag("name").text());
                book.setAuthor(author);

                ret.add(book);
            }
            return ret.toArray(new Book[0]);
        }
        catch (IOException e)
        {
            throw new FeedbooksException(e.getMessage());
        }
    }
}