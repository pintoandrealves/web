package com.aap.web.service.impl;

import com.aap.web.dto.EventDto;
import com.aap.web.mapper.EventMapper;
import com.aap.web.models.Club;
import com.aap.web.models.Event;
import com.aap.web.repository.ClubRepository;
import com.aap.web.repository.EventRepository;
import com.aap.web.service.EventService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;
    private ClubRepository clubRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, ClubRepository clubRepository) {
        this.eventRepository = eventRepository;
        this.clubRepository = clubRepository;
    }

    @Override
    public void createEvent(Long clubId, EventDto eventDto) throws NotFoundException {
        Optional<Club> club = clubRepository.findById(clubId);
        Event event = EventMapper.mapToEvent(eventDto);
        if(!club.isPresent()){
            throw new NotFoundException("There is no club!");
        }
        event.setClub(club.get());
        eventRepository.save(event);
    }

    @Override
    public List<EventDto> findAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(event -> EventMapper.mapToEventDto(event)).toList();
    }

    @Override
    public EventDto findByEventId(Long eventId) {
        Event event = eventRepository.findById(eventId).get();
        return EventMapper.mapToEventDto(event);
    }

    @Override
    public void updateEvent(EventDto eventDto) {
        Event event = EventMapper.mapToEvent(eventDto);
        eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }
}
