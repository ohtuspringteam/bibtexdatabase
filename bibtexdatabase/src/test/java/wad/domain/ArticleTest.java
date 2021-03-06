package wad.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ArticleTest {

    @Test
    public void createArticle() {
        Article article = new Article();

        //Setterit
        article.setCitation("artsu");
        article.setAuthor("Author");
        article.setJournal("jossain");
        article.setNumber(3);
        article.setPages(666);
        article.setMonth(1);
        article.setNote("joooo");
        article.setTitle("Title");
        article.setVolume(1);
        article.setYear(2015);

        //Getterit
        assertEquals("Author", article.getAuthor());
        assertEquals("jossain", article.getJournal());
        assertTrue(3 == article.getNumber());
        assertTrue(666 == article.getPages());
        assertTrue(1 == article.getMonth());
        assertEquals("joooo", article.getNote());
        assertEquals("Title", article.getTitle());
        assertTrue(1 == article.getVolume());
        assertTrue(2015 == article.getYear());
        assertEquals("artsu", article.getCitation());

    }
    
    @Test
    public void testTagsEmpty() {
        Article article = new Article();
        assertTrue(article.getTags().isEmpty());
    }

}
