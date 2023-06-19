package com.aap.web.controller;

import com.aap.web.dto.ClubDto;
import com.aap.web.models.Club;
import com.aap.web.service.ClubService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ClubController {

    @Autowired
    private ClubService clubService;

    public static final String REDIRECT_URL = "redirect:/clubs";

    @GetMapping("/clubs")
    public String listClubs(Model model){
        List<ClubDto> clubs = clubService.findAllClubs();
        model.addAttribute("clubs", clubs);
        return "clubs-list";
    }

    @GetMapping("clubs/new")
    public String createClubFrom(Model model){
        Club club = new Club();
        model.addAttribute("club", club);
        return "clubs-create";
    }

    @PostMapping("clubs/new")
    public String saveClub(@Valid @ModelAttribute("club") ClubDto clubDto,
                           BindingResult bindingResult,
                           Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("club", clubDto);
            return "clubs-create";
        }
        clubService.saveClub(clubDto);
        return REDIRECT_URL;
    }

    @GetMapping("/clubs/{clubId}/edit")
    public String editClubForm(@PathVariable("clubId") Long clubId, Model model) throws NotFoundException {
        ClubDto clubDto = clubService.findClubById(clubId);
        model.addAttribute("club", clubDto);
        return "clubs-edit";
    }

    @PostMapping("/clubs/{clubId}/edit")
    public String updateClub(@PathVariable("clubId") Long clubId,
                             @Valid @ModelAttribute("club") ClubDto clubDto,
                             BindingResult bindingResult,
                             Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("club", clubDto);

            return "clubs-edit";
        }
        clubDto.setId(clubId);
        clubService.updateClub(clubDto);
        return REDIRECT_URL;
    }

    @GetMapping("/clubs/{clubId}")
    public String clubDetail(@PathVariable("clubId") Long clubId, Model model) throws NotFoundException {
        ClubDto clubDto = clubService.findClubById(clubId);
        model.addAttribute("club", clubDto);
        return "clubs-detail";
    }

    /*
    * HTML doesn't support DELETE or PUT methods, I'm using thymeleaf and html in this project
    */
    @GetMapping("/clubs/{club}/delete")
    public String deleteClub(@PathVariable("clubId") Long clubId){
        clubService.deleteClub(clubId);
        return REDIRECT_URL;
    }

    @GetMapping("/clubs/search")
    public String searchClub(@RequestParam(value = "query") String query, Model model){
        List<ClubDto> clubs = clubService.searchClub(query);
        model.addAttribute("clubs", clubs);
        return "clubs-list";
    }

}
