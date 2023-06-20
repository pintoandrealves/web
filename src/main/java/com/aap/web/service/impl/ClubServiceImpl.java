package com.aap.web.service.impl;

import com.aap.web.dto.ClubDto;
import com.aap.web.mapper.ClubMapper;
import com.aap.web.models.Club;
import com.aap.web.models.UserEntity;
import com.aap.web.repository.ClubRepository;
import com.aap.web.repository.UserRepository;
import com.aap.web.security.SecurityUtil;
import com.aap.web.service.ClubService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClubServiceImpl implements ClubService {

    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<ClubDto> findAllClubs() {
        List<Club> clubs = clubRepository.findAll();
        return clubs.stream().map(club -> ClubMapper.mapToClubDto(club)).toList();
    }

    @Override
    public Club saveClub(ClubDto clubDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        Club club = ClubMapper.mapToClub(clubDto);
        club.setCreatedBy(user);
        return clubRepository.save(club);
    }

    @Override
    public ClubDto findClubById(Long clubId) throws NotFoundException {
        Optional<Club> club = clubRepository.findById(clubId);
        if(!club.isPresent()){
            throw new NotFoundException("There is no club!");
        }
        return ClubMapper.mapToClubDto(club.get());
    }

    @Override
    public void updateClub(ClubDto clubDto) {
        String username = SecurityUtil.getSessionUser();
        UserEntity user = userRepository.findByUsername(username);
        Club club = ClubMapper.mapToClub(clubDto);
        club.setCreatedBy(user);
        clubRepository.save(club);
    }

    @Override
    public void deleteClub(Long clubId) {
        clubRepository.deleteById(clubId);
    }

    @Override
    public List<ClubDto> searchClub(String query) {
        List<Club> clubs = clubRepository.searchClub(query);
        return clubs.stream().map(club -> ClubMapper.mapToClubDto(club)).toList();
    }

}
