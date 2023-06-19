package com.aap.web.service;

import com.aap.web.dto.ClubDto;
import com.aap.web.models.Club;
import javassist.NotFoundException;

import java.util.List;

public interface ClubService {
    List<ClubDto> findAllClubs();
    Club saveClub(ClubDto clubDto);

    ClubDto findClubById(Long clubId) throws NotFoundException;

    void updateClub(ClubDto clubDto);

    void deleteClub(Long clubId);

    List<ClubDto> searchClub(String query);
}
