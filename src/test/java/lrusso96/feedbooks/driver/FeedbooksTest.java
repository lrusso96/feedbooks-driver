package lrusso96.feedbooks.driver;

import lrusso96.feedbooks.driver.core.*;
import lrusso96.feedbooks.driver.models.Book;
import lrusso96.feedbooks.driver.models.Category;
import lrusso96.feedbooks.driver.models.Language;
import lrusso96.feedbooks.driver.exceptions.FeedbooksException;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class FeedbooksTest
{

    private final int SLEEP = 2000;

    @Test
    public void simpleSearch() throws FeedbooksException, InterruptedException
    {
        Thread.sleep(SLEEP);
        Feedbooks feedbooks = new FeedbooksBuilder().build();
        Book[] ret = feedbooks.search("Carroll");
        assertNotEquals(0, ret.length);
        Book book = ret[0];
        assertNotNull(book.getAuthor());
        assertNotEquals(0, book.getId());
        assertNotNull(book.getTitle());
        assertNotNull(book.getDownload());
    }

    @Test
    public void customLanguages() throws FeedbooksException, InterruptedException
    {
        Thread.sleep(SLEEP);
        Feedbooks feedbooks = new FeedbooksBuilder()
                .addLanguage(Language.ITALIAN)
                .addLanguage(Language.ENGLISH)
                .build();
        Book[] ret = feedbooks.search("Carroll");
        assertNotEquals(0, ret.length);
        Book book = ret[0];
        assertNotNull(book.getAuthor());
        assertNotEquals(0, book.getId());
        assertNotNull(book.getTitle());
        assertNotNull(book.getDownload());
    }

    @Test
    public void maxResults() throws FeedbooksException, InterruptedException
    {
        Thread.sleep(SLEEP);
        int limit = 2;
        Feedbooks feedbooks = new FeedbooksBuilder().setMaxResults(limit).build();
        Book[] ret = feedbooks.search("Shakespeare");
        assertFalse(ret.length > limit);
        Book book = ret[0];
        assertNotNull(book.getAuthor());
        assertNotEquals(0, book.getId());
        assertNotNull(book.getTitle());
        assertNotNull(book.getDownload());
    }

    @Test
    public void getRecent() throws FeedbooksException, InterruptedException
    {
        Thread.sleep(SLEEP);
        int limit = 20;
        Feedbooks feedbooks = new FeedbooksBuilder().setMaxResults(limit).addLanguage(Language.ITALIAN).build();
        Book[] ret = feedbooks.getRecent();
        assertFalse(ret.length > limit);
        Book book = ret[0];
        assertNotNull(book.getAuthor());
        assertNotEquals(0, book.getId());
        assertNotNull(book.getTitle());
        assertNotNull(book.getDownload());
    }

    @Test
    public void getTop() throws FeedbooksException, InterruptedException
    {
        Thread.sleep(SLEEP);
        int limit = 20;
        Feedbooks feedbooks = new FeedbooksBuilder().setMaxResults(limit).addLanguage(Language.SPANISH).build();
        Book[] ret = feedbooks.getTop();
        assertFalse(ret.length > limit);
        Book book = ret[0];
        assertNotNull(book.getAuthor());
        assertNotEquals(0, book.getId());
        assertNotNull(book.getTitle());
        assertNotNull(book.getDownload());
    }

    @Test
    public void loadMoreTopBooks() throws FeedbooksException, InterruptedException
    {
        Thread.sleep(SLEEP);
        Feedbooks feedbooks = new FeedbooksBuilder().addLanguage(Language.ITALIAN).build();
        Result result = feedbooks.getTopWithResult();
        Set<Book> books = result.getBooks();
        Set<Book> backup = new HashSet<>(books);
        feedbooks.loadMore(result);
        assertTrue(books.containsAll(backup));
        assertEquals(books.size(), 2 * backup.size());
    }

    @Test
    public void getSpecificCategory() throws FeedbooksException, InterruptedException
    {
        Thread.sleep(SLEEP);
        Category.Label[] categories = new Category.Label[]{Category.Label.DRAMA, Category.Label.SEA_STORIES};
        for (Category.Label label : categories)
        {
            Feedbooks feedbooks = new FeedbooksBuilder().setLabel(label).build();
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
    public void getAllBooks() throws FeedbooksException, InterruptedException
    {
        Thread.sleep(SLEEP);
        Feedbooks feedbooks = new FeedbooksBuilder()
                .setUnlimitedSearch()
                .addLanguage(Language.ITALIAN)
                .setLabel(Category.Label.LITERARY)
                .build();
        Book[] ret = feedbooks.getTop();
        assertTrue(ret.length > 70 );
    }
}
