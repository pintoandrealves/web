package com.aap.web.controller;

import com.aap.web.dto.EventDto;
import com.aap.web.models.Event;
import com.aap.web.models.UserEntity;
import com.aap.web.security.SecurityUtil;
import com.aap.web.service.EventService;
import com.aap.web.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    private static final String EVENT_ATTRIBUTE = "event";

    @GetMapping("/events/{clubId}/new")
    public String createEventForm(@PathVariable("clubId") Long clubId, Model model){
        Event event = new Event();
        model.addAttribute("clubId", clubId);
        model.addAttribute(EVENT_ATTRIBUTE, event);
        return "events-create";
    }

    @PostMapping("/events/{clubId}")
    public String createEvent(@PathVariable("clubId") Long clubId,
                              @ModelAttribute("event") EventDto eventDto,
                              Model model,
                              BindingResult bindingResult) throws NotFoundException {
        if(bindingResult.hasErrors()){
            model.addAttribute(EVENT_ATTRIBUTE, eventDto);
            return "events-create";
        }
        eventService.createEvent(clubId, eventDto);
        return "redirect:/clubs/" + clubId;
    }

    @GetMapping("/events")
    public String eventList(Model model){
        UserEntity user = new UserEntity();
        List<EventDto> events = eventService.findAllEvents();
        String username = SecurityUtil.getSessionUser();
        if(username != null) {
            user = userService.findByUserName(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("events", events);
        return "events-list";
    }

    @GetMapping("/events/{eventId}")
    public String eventDetail(@PathVariable("eventId") Long eventId,
                              Model model){
        EventDto eventDto = eventService.findByEventId(eventId);
        model.addAttribute(EVENT_ATTRIBUTE, eventDto);
        return "events-detail";
    }

    @GetMapping("/events/{eventId}/edit")
    public String editEventForm(@PathVariable("eventId") Long eventId, Model model) {
        EventDto eventDto = eventService.findByEventId(eventId);
        model.addAttribute(EVENT_ATTRIBUTE, eventDto);
        return "events-edit";
    }

    @PostMapping("/events/{eventId}/edit")
    public String updateEvent(@PathVariable("eventId") Long eventId,
                              @Valid @ModelAttribute("event") EventDto eventDto,
                              BindingResult bindingResult,
                              Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute(EVENT_ATTRIBUTE, eventDto);
            return "events-edit";
        }
        EventDto eventDb = eventService.findByEventId(eventId);
        eventDto.setId(eventId);
        eventDto.setClub(eventDb.getClub());
        eventService.updateEvent(eventDto);
        return "redirect:/events";
    }

    /*
     * HTML doesn't support DELETE or PUT methods, I'm using thymeleaf and html in this project
     */
    @GetMapping("/events/{eventId}/delete")
    public String deleteEvent(@PathVariable("eventId") Long eventId){
        eventService.deleteEvent(eventId);
        return "redirect:/events";
    }

}
