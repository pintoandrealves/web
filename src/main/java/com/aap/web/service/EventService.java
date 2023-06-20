package com.aap.web.service;

import com.aap.web.dto.EventDto;
import javassist.NotFoundException;

import java.util.List;

public interface EventService {

    void createEvent(Long clubId, EventDto eventDto) throws NotFoundException;

    List<EventDto> findAllEvents();

    EventDto findByEventId(Long eventId) throws NotFoundException;

    void updateEvent(EventDto eventDto);

    void deleteEvent(Long eventId);
}
