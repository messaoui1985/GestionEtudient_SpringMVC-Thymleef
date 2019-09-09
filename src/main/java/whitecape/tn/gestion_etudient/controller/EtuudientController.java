package whitecape.tn.gestion_etudient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import whitecape.tn.gestion_etudient.entites.Etudient;
import whitecape.tn.gestion_etudient.repository.EtudientRepository;

import java.util.List;

@Controller
@RequestMapping("etudient")
public class EtuudientController {
    @Autowired
    private EtudientRepository etudientRepository;



    @GetMapping("/index")

    public  String index(Model model,
                         @RequestParam(name="page", defaultValue="0")int p
                       )

    {
        Page <Etudient> pageEtudients=etudientRepository.findAll(new PageRequest(p,5));
        model.addAttribute("etudiants",pageEtudients.getContent());
        int pageCount=pageEtudients.getTotalPages();
        int [] pages=new int[pageCount];
        for (int i=0;i<pageCount;i++)
            pages[i]=i;

        model.addAttribute("page", pages);
        model.addAttribute("pageEtudients",pageEtudients);
        model.addAttribute("pageCaurante",p);



        return"etudiants";
    }


}
