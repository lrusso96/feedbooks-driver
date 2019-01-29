package lrusso96.feedbooks.driver;

import lrusso96.feedbooks.driver.core.Book;
import lrusso96.feedbooks.driver.core.Category;
import lrusso96.feedbooks.driver.core.Feedbooks;
import lrusso96.feedbooks.driver.exceptions.FeedbooksException;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

public class FeedbooksTest
{
    @Test
    public void simpleSearch() throws FeedbooksException
    {
        Feedbooks feedbooks = new Feedbooks(null, null, null);
        Book[] ret = feedbooks.search("Carroll");
        assertNotEquals(0, ret.length);
        Book book = ret[0];
        assertNotNull(book.getAuthor());
        assertNotEquals(0, book.getId());
        assertNotNull(book.getTitle());
        assertNotNull(book.getDownload());
    }

    @Test
    public void customLanguages() throws FeedbooksException
    {
        Locale[] lang = new Locale[]{new Locale("it"), new Locale("en")};
        Feedbooks feedbooks = new Feedbooks(lang, null, null);
        Book[] ret = feedbooks.search("Carroll");
        assertNotEquals(0, ret.length);
        Book book = ret[0];
        assertNotNull(book.getAuthor());
        assertNotEquals(0, book.getId());
        assertNotNull(book.getTitle());
        assertNotNull(book.getDownload());
    }

    @Test
    public void maxResults() throws FeedbooksException
    {
        int limit = 2;
        Feedbooks feedbooks = new Feedbooks(null, limit, null);
        Book[] ret = feedbooks.search("Shakespeare");
        assertFalse(ret.length > limit);
        Book book = ret[0];
        assertNotNull(book.getAuthor());
        assertNotEquals(0, book.getId());
        assertNotNull(book.getTitle());
        assertNotNull(book.getDownload());
    }

    @Test
    public void getRecent() throws FeedbooksException
    {
        int limit = 20;
        Locale[] lang = new Locale[]{new Locale("it")};
        Feedbooks feedbooks = new Feedbooks(lang, limit, null);
        Book[] ret = feedbooks.getRecent();
        assertFalse(ret.length > limit);
        Book book = ret[0];
        assertNotNull(book.getAuthor());
        assertNotEquals(0, book.getId());
        assertNotNull(book.getTitle());
        assertNotNull(book.getDownload());
    }

    @Test
    public void getTop() throws FeedbooksException
    {
        int limit = 20;
        Locale[] lang = new Locale[]{new Locale("es")};
        Feedbooks feedbooks = new Feedbooks(lang, limit, null);
        Book[] ret = feedbooks.getTop();
        assertFalse(ret.length > limit);
        Book book = ret[0];
        assertNotNull(book.getAuthor());
        assertNotEquals(0, book.getId());
        assertNotNull(book.getTitle());
        assertNotNull(book.getDownload());
    }

    @Test
    public void getSpecificCategory() throws FeedbooksException
    {
        Category.Label[] categories = new Category.Label[]{Category.Label.DRAMA, Category.Label.SEA_STORIES};
        for (Category.Label label : categories)
        {
            Feedbooks feedbooks = new Feedbooks(null, null, label);
            Book[] ret = feedbooks.getTop();
            for (Book book : ret)
            {
                boolean found = false;
                for (Category category : book.getCategories())
                {
                    if (category.getTerm().equals(label.toString()))
                    {
                        found = true;
                        break;
                    }
                }
                assertTrue(found);
            }
        }
    }

    @Test
    public void getAllBooks() throws FeedbooksException
    {
        Locale[] lang = new Locale[]{new Locale("it")};
        Feedbooks feedbooks = new Feedbooks(lang, null, Category.Label.LITERARY);
        Book[] ret = feedbooks.getTop();
        assertTrue(ret.length > 70 );
    }
}
