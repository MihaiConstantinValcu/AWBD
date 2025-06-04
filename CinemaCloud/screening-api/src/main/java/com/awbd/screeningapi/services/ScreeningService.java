package com.awbd.screeningapi.services;

import com.awbd.screeningapi.domain.Hall;
import com.awbd.screeningapi.domain.Movie;
import com.awbd.screeningapi.domain.Screening;
import com.awbd.screeningapi.dtos.HallDto;
import com.awbd.screeningapi.dtos.ScreeningDto;
import com.awbd.screeningapi.dtos.SeatDto;
import com.awbd.screeningapi.repositories.HallRepository;
import com.awbd.screeningapi.repositories.ScreeningRepository;
import com.awbd.screeningapi.repositories.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreeningService {
    private final HallRepository hallRepository;
    private final ModelMapper modelMapper;
    private final ScreeningRepository screeningRepository;
    private final SeatRepository seatRepository;

    public List<HallDto> findAllHalls() {
        return hallRepository.findAll().stream()
                .map(hall -> modelMapper.map(hall, HallDto.class))
                .toList();
    }

    public void save(ScreeningDto screening) {
        screeningRepository.save(modelMapper.map(screening, Screening.class));
    }

    public List<ScreeningDto> findAllForMovie(Long id) {
        return screeningRepository.findAllByMovieId(id).stream()
                .map(screening -> modelMapper.map(screening, ScreeningDto.class))
                .toList();
    }

    public ScreeningDto findScreeningById(Long id){
        return screeningRepository.findById(id)
                .map(screening -> modelMapper.map(screening, ScreeningDto.class))
                .orElseThrow();
    }

    public HallDto findHallById(Long id){
        return hallRepository.findById(id)
                .map(hall -> modelMapper.map(hall, HallDto.class))
                .orElseThrow();
    }

    public List<SeatDto> findAllByHallId(Long id){
        return seatRepository.findAllByHallId(id).stream()
                .map(seat -> modelMapper.map(seat, SeatDto.class))
                .toList();
    }

    public List<SeatDto> findAllByIdIn(List<Long> ids){
        return seatRepository.findAllByIdIn(ids).stream()
                .map(seat -> modelMapper.map(seat, SeatDto.class))
                .toList();
    }

    public SeatDto findSeatById(Long id) {
        return seatRepository.findById(id)
                .map(seat -> modelMapper.map(seat, SeatDto.class))
                .orElseThrow();
    }
}
