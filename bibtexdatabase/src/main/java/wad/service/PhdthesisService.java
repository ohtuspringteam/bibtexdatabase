package wad.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wad.domain.Mastersthesis;
import wad.domain.Phdthesis;
import wad.domain.Tag;
import wad.repository.PhdthesisRepository;

@Service
public class PhdthesisService implements ServiceInterface<Phdthesis> {

    @Autowired
    private PhdthesisRepository mastersthesisRepository;

    public void addTag(Long id, Tag tag) {
        Phdthesis phdthesis = getPhdthesis(id);
        phdthesis.getTags().add(tag);
        addPhdthesis(phdthesis);
    }
    
    public List<Phdthesis> list() {
        List<Phdthesis> phdthesises = mastersthesisRepository.findAll();
        return phdthesises;
    }

    public void addPhdthesis(Phdthesis mastersthesis) {
        mastersthesisRepository.save(mastersthesis);
    }

    public void deletePhdthesis(Long id) {
        mastersthesisRepository.delete(mastersthesisRepository.findOne(id));
    }

    public Phdthesis getPhdthesis(Long id) {
        return mastersthesisRepository.findOne(id);
    }

    private String toBibtex(Phdthesis thesis) throws IllegalArgumentException, IllegalAccessException {
        String result = "@Phdthesis {";
        String tabs;
        Class<? extends Object> obj = thesis.getClass();
        Field[] fields = obj.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals("tags")) {
                continue;
            }
            boolean ehto = (field.get(thesis) != null && !field.get(thesis).toString().isEmpty());
            if (ehto && field.getName().equals("citation")) {
                result += thesis.getCitation() + "\n";
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
                        field.get(thesis));
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
        Phdthesis mastersthesis = mastersthesisRepository.findOne(id);
        String result = "";
        try {
            result = toBibtex(mastersthesis);
        } catch (Exception ex) {
        }
        return result;
    }

    public String getBibtex(Phdthesis phdthesis) {
        String result = "";
        try {
            result = toBibtex(phdthesis);
        } catch (Exception ex) {
        }
        return result;
    }

    public List<Phdthesis> search(String name) {
        List<Phdthesis> result = new ArrayList<>();
        List<Phdthesis> byAuthor = mastersthesisRepository.findByAuthorContaining(name);
        List<Phdthesis> byTitle = mastersthesisRepository.findByTitleContaining(name);
        List<Phdthesis> byBooktitle = mastersthesisRepository.findBySchoolContaining(name);
        result.addAll(byAuthor);
        for (Phdthesis mastersthesis : byTitle) {
            if (!result.contains(mastersthesis)) {
                result.add(mastersthesis);
            }
        }
        for (Phdthesis mastersthesis : byBooktitle) {
            if (!result.contains(mastersthesis)) {
                result.add(mastersthesis);
            }
        }
        return result;
    }

}
