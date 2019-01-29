package lrusso96.feedbooks.driver;

import lrusso96.feedbooks.driver.core.*;
import lrusso96.feedbooks.driver.exceptions.FeedbooksException;
import org.junit.Test;

import static org.junit.Assert.*;

public class FeedbooksTest
{
    @Test
    public void simpleSearch() throws FeedbooksException
    {
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
    public void customLanguages() throws FeedbooksException
    {
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
    public void maxResults() throws FeedbooksException
    {
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
    public void getRecent() throws FeedbooksException
    {
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
    public void getTop() throws FeedbooksException
    {
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
    public void getSpecificCategory() throws FeedbooksException
    {
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
    public void getAllBooks() throws FeedbooksException
    {
        Feedbooks feedbooks = new FeedbooksBuilder()
                .setUnlimitedSearch()
                .addLanguage(Language.ITALIAN)
                .setLabel(Category.Label.LITERARY)
                .build();
        Book[] ret = feedbooks.getTop();
        assertTrue(ret.length > 70 );
    }
}
