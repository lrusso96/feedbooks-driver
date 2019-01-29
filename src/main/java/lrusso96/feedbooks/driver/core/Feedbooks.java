package lrusso96.feedbooks.driver.core;

import lrusso96.feedbooks.driver.exceptions.FeedbooksException;
import org.apache.commons.lang3.math.NumberUtils;
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
    //supported lang: en, it, es, de and fr.
    private final Locale[] languages;
    private final Integer maxResults;
    private final Category.Label label;

    public Feedbooks(Locale[] languages, Integer maxResults, Category.Label label)
    {
        this.languages = languages == null?  new Locale[]{ new Locale("en") } : languages;
        this.maxResults = maxResults;
        this.label = label;
    }

    private Category parseCategory(Element element){
        Category category = new Category();
        String term = element.attr("term");
        category.setTerm(term);
        String label = element.attr("label");
        category.setLabel(label);
        return category;
    }

    private int parseID(String string){
        String[] ids = string.split("/");
        if(ids.length == 0)
            return 0;
        return NumberUtils.toInt(ids[ids.length - 1], 0);
    }

    private Author parseAuthor(Element entry){
        Author author = new Author();
        String id = entry.getElementsByTag("uri").text();
        author.setId(parseID(id));
        author.setFullName(entry.getElementsByTag("name").text());
        author.setBirthDate(NumberUtils.toInt(entry.getElementsByTag("schema:birthDate").text(), 0));
        author.setDeathDate(NumberUtils.toInt(entry.getElementsByTag("schema:deathDate").text(), 0));
        return author;
    }

    private Book parseBook(Element entry){
        Book book = new Book();
        String id = entry.getElementsByTag("id").text();
        book.setId(parseID(id));
        book.setTitle(entry.getElementsByTag("title").text());
        book.setSummary(entry.getElementsByTag("summary").text());
        book.setPublished(parseUTC(entry.getElementsByTag("published").text()));
        book.setUpdated(parseUTC(entry.getElementsByTag("updated").text()));
        book.setIssued(NumberUtils.toInt(entry.getElementsByTag("dcterms:issued").text(), 0));
        book.setLanguage(new Locale(entry.getElementsByTag("dcterms:language").text()));
        String coverKey = "http://opds-spec.org/image";
        String downloadKey = "http://opds-spec.org/acquisition";
        Elements links = entry.getElementsByTag("link");
        for (Element link : links)
        {
            String rel = link.attr("rel");
            if (rel.equals(coverKey))
                book.setCover(URI.create(link.attr("href")));
            else if (rel.equals(downloadKey))
                book.setDownload(URI.create(link.attr("href")));
        }

        book.setSource(entry.getElementsByTag("dcterms:source").text());

        Elements categories = entry.getElementsByTag("category");
        book.setCategories(categories.stream().map(this::parseCategory).toArray(Category[]::new));

        // can focus on Element author only!
        book.setAuthor(parseAuthor(entry));
        return book;
    }

    public Book[] search(String query) throws FeedbooksException
    {
        URI endpoint = URI.create("https://feedbooks.com/books/search.atom");
        return _search(endpoint, query);
    }

    public Book[] getRecent() throws FeedbooksException
    {
        URI endpoint = URI.create("https://feedbooks.com/books/recent.atom");
        return _search(endpoint, null);
    }

    public Book[] getTop() throws FeedbooksException
    {
        URI endpoint = URI.create("https://feedbooks.com/books/top.atom");
        return _search(endpoint, null);
    }

    private Book[] _search(URI endpoint, String query) throws FeedbooksException
    {
        try
        {
            List<Book> ret = new ArrayList<>();
            Connection connection = Jsoup.connect(endpoint.toString());
            if(query!= null)
                connection = connection.data("query", query);
            if(label!= null)
                connection = connection.data("category", label+"");
            Integer cnt = maxResults;
            int totalResults = Integer.MAX_VALUE;
            for (Locale language : languages)
            {
                for (int page = 1; ret.size() < totalResults; page++)
                {
                    Document doc = connection.data("lang", language.getLanguage()).data("page", page + "").get();
                    totalResults = NumberUtils.toInt(doc.getElementsByTag("opensearch:totalResults").text());

                    Elements entries = doc.getElementsByTag("entry");
                    for (Element entry : entries)
                    {
                        if ((cnt != null) && (cnt-- == 0))
                        {
                            return ret.toArray(new Book[0]);
                        }
                        ret.add(parseBook(entry));
                    }
                }
            }
            return ret.toArray(new Book[0]);
        }
        catch (IOException e)
        {
            throw new FeedbooksException(e.getMessage());
        }
    }

}