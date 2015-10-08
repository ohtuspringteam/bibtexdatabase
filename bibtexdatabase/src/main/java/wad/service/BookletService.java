
package wad.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wad.domain.Booklet;
import wad.repository.BookletRepository;

@Service
public class BookletService {

    @Autowired
    private BookletRepository repo;

    public List<Booklet> list() {
        List<Booklet> books = repo.findAll();
        return books;
    }

    public void addBooklet(Booklet book) {
        repo.save(book);
    }

    public void deleteBooklet(Long id) {
        repo.delete(repo.findOne(id));
    }

    public Booklet getBooklet(Long id) {
        return repo.findOne(id);
    }
    
    /*
    private String toBibtex(Book book) throws IllegalArgumentException, IllegalAccessException{
        String result = "@Book {";
        String tabs;
        Class<? extends Object> obj = book.getClass();
        Field[] fields = obj.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            boolean ehto = (field.get(book) != null && !field.get(book).toString().isEmpty());
            if (ehto && field.getName().equals("citation")) {
                result += book.getCitation() + "\n";
                continue;
            }
            if(ehto) {
                if (field.getName().length()<8)
                    tabs="\t\t\t";
                else
                    tabs="\t\t";
                result += String.format("%s%s=\t\t\"%s\",\n",
                field.getName(),
                tabs,
                field.get(book));
            }
        }
        int ind = result.lastIndexOf(",");
        result = new StringBuilder(result).replace(ind, ind+1,"").toString();
        result += "}";
        return result;
    }
    */
    
    /*
    public String getBibtex(Long id) {
        Booklet book = repo.findOne(id);
        String result = "";
        try {
        result = toBibtex(book);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
            return result;
    }
    */
    
    public List<Booklet> search(String name) {
        List<Booklet> result = new ArrayList<>();
        List<Booklet> byAuthor = repo.findByAuthorContaining(name);
        List<Booklet> byTitle = repo.findByTitleContaining(name);
        result.addAll(byAuthor);
        result.addAll(byTitle);
        return result;
    }
    
}
