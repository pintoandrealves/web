package com.aap.web.mapper;

import com.aap.web.dto.ClubDto;
import com.aap.web.models.Club;

public class ClubMapper {

    private ClubMapper() {

    }

    public static Club mapToClub(ClubDto clubDto) {
        return Club.builder()
                .id(clubDto.getId())
                .title(clubDto.getTitle())
                .photoUrl(clubDto.getPhotoUrl())
                .content(clubDto.getContent())
                .createdOn(clubDto.getCreatedOn())
                .createdBy(clubDto.getCreatedBy())
                .updatedOn(clubDto.getUpdatedOn())
                .build();
    }

    public static ClubDto mapToClubDto(Club club){
        return ClubDto.builder()
                .id(club.getId())
                .title(club.getTitle())
                .photoUrl(club.getPhotoUrl())
                .content(club.getContent())
                .createdOn(club.getCreatedOn())
                .createdBy(club.getCreatedBy())
                .updatedOn(club.getUpdatedOn())
                .events(club.getEvents().stream().map(event -> EventMapper.mapToEventDto(event)).toList())
                .build();
    }

}
