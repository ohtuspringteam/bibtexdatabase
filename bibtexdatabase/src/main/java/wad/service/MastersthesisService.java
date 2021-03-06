package wad.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wad.domain.Mastersthesis;
import wad.domain.Tag;
import wad.repository.MastersthesisRepository;

@Service
public class MastersthesisService implements ServiceInterface<Mastersthesis> {

    @Autowired
    private MastersthesisRepository mastersthesisRepository;

    public void addTag(Long id, Tag tag) {
        Mastersthesis ms = getMastersthesis(id);
        ms.getTags().add(tag);
        addMastersthesis(ms);
    }
    
    public List<Mastersthesis> list() {
        List<Mastersthesis> mastersthesises = mastersthesisRepository.findAll();
        return mastersthesises;
    }

    public void addMastersthesis(Mastersthesis mastersthesis) {
        mastersthesisRepository.save(mastersthesis);
    }

    public void deleteMastersthesis(Long id) {
        mastersthesisRepository.delete(mastersthesisRepository.findOne(id));
    }

    public Mastersthesis getMastersthesis(Long id) {
        return mastersthesisRepository.findOne(id);
    }

    private String toBibtex(Mastersthesis mastersthesis) throws IllegalArgumentException, IllegalAccessException {
        String result = "@Mastersthesis {";
        String tabs;
        Class<? extends Object> obj = mastersthesis.getClass();
        Field[] fields = obj.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals("tags")) {
                continue;
            }
            boolean ehto = (field.get(mastersthesis) != null && !field.get(mastersthesis).toString().isEmpty());
            if (ehto && field.getName().equals("citation")) {
                result += mastersthesis.getCitation() + "\n";
                continue;
            }
            if (ehto) {
                if (field.getName().length() < 8) {
                    tabs = "\t\t\t";
                } else {
                    tabs = "\t\t";
                }
                result += String.format("%s%s=\t\t\"%s\",\n",
                        field.getName(),
                        tabs,
                        field.get(mastersthesis));
            }
        }
        int ind = result.lastIndexOf(",");
        result = new StringBuilder(result).replace(ind, ind + 1, "").toString();
        result += "}";
        result = aakkosetBibtexMuotoon(result);
        return result;
    }

    private String aakkosetBibtexMuotoon(String result) {
        result = result.replace("ä", "{\\\"a}");
        result = result.replace("ö", "{\\\"o}");
        result = result.replace("å", "{\\aa}");
        return result;
    }

    public String getBibtex(Long id) {
        Mastersthesis mastersthesis = mastersthesisRepository.findOne(id);
        String result = "";
        try {
            result = toBibtex(mastersthesis);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    public String getBibtex(Mastersthesis mastersthesis) {
        String result = "";
        try {
            result = toBibtex(mastersthesis);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return result;
    }

    public List<Mastersthesis> search(String name) {
        List<Mastersthesis> result = new ArrayList<>();
        List<Mastersthesis> byAuthor = mastersthesisRepository.findByAuthorContaining(name);
        List<Mastersthesis> byTitle = mastersthesisRepository.findByTitleContaining(name);
        List<Mastersthesis> bySchool = mastersthesisRepository.findBySchoolContaining(name);
        result.addAll(byAuthor);
        for (Mastersthesis mastersthesis : byTitle) {
            if (!result.contains(mastersthesis)) {
                result.add(mastersthesis);
            }
        }
        for (Mastersthesis mastersthesis : bySchool) {
            if (!result.contains(mastersthesis)) {
                result.add(mastersthesis);
            }
        }
        return result;
    }

}
