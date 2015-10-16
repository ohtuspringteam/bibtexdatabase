package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wad.domain.Booklet;
import wad.service.BookletService;

@Controller
@RequestMapping(value = "/booklets")
public class BookletController {

    @Autowired
    private BookletService bookletService;

    @RequestMapping(method = RequestMethod.POST)
    public String createBooklet(@ModelAttribute Booklet booklet, RedirectAttributes redirectAttributes) {
        bookletService.addBooklet(booklet);
        redirectAttributes.addFlashAttribute("id", booklet.getId());
        redirectAttributes.addFlashAttribute("message", "New booklet created");
        return "redirect:/booklets/new";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        if (!bookletService.list().isEmpty()) {
            model.addAttribute("booklets", bookletService.list());
        }
        return "booklets";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getAdded(@PathVariable Long id, Model model) {
        model.addAttribute("booklet", bookletService.getBooklet(id));
        return "booklet";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newBooklet() {
        return "newbooklet";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.DELETE)
    public String deleteBooklet(RedirectAttributes redirectAttributes, @PathVariable Long id) {
        bookletService.deleteBooklet(id);
        redirectAttributes.addFlashAttribute("message", "Booklet deleted");
        return "redirect:/booklets";
    }
    
    @RequestMapping(value="/{id}/bibtex", method = RequestMethod.GET)
    public String getBibtex(@PathVariable Long id, Model model){
        Booklet booklet = bookletService.getBooklet(id);
        model.addAttribute("bibtex", bookletService.getBibtex(booklet.getId()));
        return "bibtex";
    }
}