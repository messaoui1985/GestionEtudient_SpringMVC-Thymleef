package whitecape.tn.gestion_etudient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import whitecape.tn.gestion_etudient.entites.Etudient;
import whitecape.tn.gestion_etudient.repository.EtudientRepository;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("etudient")
public class EtuudientController {
    @Autowired
    private EtudientRepository etudientRepository;
    @Value("${dir.images}")
    private String imageDir;

    @GetMapping("/index")

    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int p,
                        @RequestParam(name = "size",defaultValue = "5")int s ,
                        @RequestParam(name = "motCle", defaultValue = "") String mc) {
        Page <Etudient> pageEtudients = etudientRepository.chercherEtudient("%" +mc+ "%", new PageRequest(p,s));
        model.addAttribute("etudiants", pageEtudients.getContent());

        int[] etudiants= new int[pageEtudients.getTotalPages()];
        model.addAttribute("page", etudiants);
        model.addAttribute("size",s);
        model.addAttribute("pageEtudients", pageEtudients);
        model.addAttribute("pageCaurante", p);
        model.addAttribute("motCle", mc);


        return "etudiants";
    }

    @GetMapping("/delete")
    private String delete(Long id,String mc,int page,String motCle){
        etudientRepository.deleteById(id);

        return "redirect:index?page="+page+ "&motcle=" +motCle ;
    }


@GetMapping("/formEtudiants")
    private String formEtudiants(Model model){
        model.addAttribute("etudiants", new Etudient());
        return"formEtudiants";
}
    @PostMapping("/saveEtudiant")

    private String save(@Valid Etudient et,BindingResult bindingResult,
                @RequestParam(name="picture") MultipartFile file) throws IOException {
        if (bindingResult.hasErrors()) {
            return "formEtudiants";
        }

        if (!(file.isEmpty())) {
            System.out.println("---------------");
            System.out.println(file.getOriginalFilename());
            et.setPhoto(file.getOriginalFilename()); // getoriginalfilname retourne le nom originale de la photo
            file.transferTo(new File(System.getProperty("user.home")+"/scop/"+file.getOriginalFilename()));
        }

        etudientRepository.save(et);

            return "redirect:index";


    }
}
