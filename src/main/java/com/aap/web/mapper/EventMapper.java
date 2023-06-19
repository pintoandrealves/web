package com.aap.web.mapper;

import com.aap.web.dto.EventDto;
import com.aap.web.models.Event;

public class EventMapper {
    public static Event mapToEvent(EventDto eventDto){
        return Event.builder()
                .id(eventDto.getId())
                .name(eventDto.getName())
                .startTime(eventDto.getStartTime())
                .endTime(eventDto.getEndTime())
                .type(eventDto.getType())
                .photoUrl(eventDto.getPhotoUrl())
                .createOn(eventDto.getCreateOn())
                .updateOn(eventDto.getUpdateOn())
                .club(eventDto.getClub())
                .build();
    }

    public static EventDto mapToEventDto(Event event){
        return EventDto.builder()
                .id(event.getId())
                .name(event.getName())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .type(event.getType())
                .photoUrl(event.getPhotoUrl())
                .createOn(event.getCreateOn())
                .updateOn(event.getUpdateOn())
                .club(event.getClub())
                .build();
    }
}
