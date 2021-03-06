
package wad.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class BookletTest {
    
    @Test
    public void createBooklet() {
        Booklet book = new Booklet();

        //Setterit
        book.setCitation("citation");
        book.setAuthor("Author");
        book.setTitle("Title");
        book.setHowpublished("kopioitu");
        book.setAddress("Osoite");
        book.setMonth(1);
        book.setNote("Note");
        book.setYear(2015);
        book.setKey("avain");
        //Getterit
        
        assertEquals("Osoite", book.getAddress());
        assertEquals("Author", book.getAuthor());
        assertEquals("Note", book.getNote());
        assertEquals("avain", book.getKey());
        assertEquals("Title", book.getTitle());
        assertEquals(1, (int) book.getMonth());
        assertEquals(2015, (int) book.getYear());
        assertEquals("kopioitu", book.getHowpublished());
        
    }
    
    @Test
    public void testTagsEmpty() {
        Booklet book = new Booklet();
        assertTrue(book.getTags().isEmpty());
    }
    
}
