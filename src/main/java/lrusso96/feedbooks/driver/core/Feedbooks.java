package lrusso96.feedbooks.driver.core;

import lrusso96.feedbooks.driver.exceptions.FeedbooksException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
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

    private Category parseCategory(Element element){
        Category category = new Category();
        category.setLabel(element.attr("label"));
        category.setTerm(element.attr("term"));
        return category;
    }

    private int parseID(String string){
        String[] ids = string.split("/");
        try {
            return Integer.parseInt(ids[ids.length - 1]);
        }
        catch (NumberFormatException e){
            return 0;
        }
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
                String id = entry.getElementsByTag("id").text();
                book.setId(parseID(id));
                book.setTitle(entry.getElementsByTag("title").text());
                book.setSummary(entry.getElementsByTag("summary").text());
                book.setPublished(parseUTC(entry.getElementsByTag("published").text()));
                book.setUpdated(parseUTC(entry.getElementsByTag("updated").text()));
                book.setIssued(Integer.parseInt(entry.getElementsByTag("dcterms:issued").text()));
                book.setLanguage(new Locale(entry.getElementsByTag("dcterms:language").text()));
                String coverKey = "http://opds-spec.org/image";
                String downloadKey = "http://opds-spec.org/acquisition";
                Elements links = entry.getElementsByTag("link");
                for(Element link : links)
                {
                    String rel = link.attr("rel");
                    if(rel.equals(coverKey))
                        book.setCover(URI.create(link.attr("href")));
                    else if(rel.equals(downloadKey))
                        book.setDownload(URI.create(link.attr("href")));
                }

                book.setSource(URI.create(entry.getElementsByTag("dcterms:source").text()));

                Elements categories = entry.getElementsByTag("category");
                book.setCategories(categories.stream().map(this::parseCategory).toArray(Category[]::new));

                // can focus on Element author only!
                Author author = new Author();
                id = entry.getElementsByTag("uri").text();
                author.setId(parseID(id));
                author.setFullName(entry.getElementsByTag("name").text());
                author.setBirthDate(Integer.parseInt(entry.getElementsByTag("schema:birthDate").text()));
                author.setDeathDate(Integer.parseInt(entry.getElementsByTag("schema:deathDate").text()));
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