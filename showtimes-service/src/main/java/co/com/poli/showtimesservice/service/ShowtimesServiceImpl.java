package co.com.poli.showtimesservice.service;


import co.com.poli.showtimesservice.clientFeign.MoviesClient;
import co.com.poli.showtimesservice.model.Movies;
import co.com.poli.showtimesservice.persistence.entity.Showtimes;
import co.com.poli.showtimesservice.persistence.entity.ShowtimesItem;
import co.com.poli.showtimesservice.persistence.repository.ShowtimesItemRepository;
import co.com.poli.showtimesservice.persistence.repository.ShowtimesRepository;
import co.com.poli.showtimesservice.service.dto.ShowtimesDetalleInDTO;
import co.com.poli.showtimesservice.service.dto.ShowtimesInDTO;
import co.com.poli.showtimesservice.service.dto.ShowtimesItemInDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowtimesServiceImpl implements ShowtimesService {

    private final ShowtimesRepository showtimesRepository;

    private final ShowtimesItemRepository showtimesItemRepository;
    private final MoviesClient moviesClient;

    @Override
    public Showtimes save(ShowtimesInDTO showtimesInDTO) {
        Showtimes showtimes = new Showtimes();
        showtimes.setDate(showtimesInDTO.getDate());
        List<ShowtimesItem> showtimesItems = new ArrayList<>();
        for(ShowtimesItemInDTO showtimesItem: showtimesInDTO.getItems()){
            ShowtimesItem nuevoItem = new ShowtimesItem();
            nuevoItem.setIdMovie(showtimesItem.getIdMovie());

            showtimesItems.add(nuevoItem);
        }

        showtimes.setItems(showtimesItems);

        return this.showtimesRepository.save(showtimes);
    }

    @Override
    public List<ShowtimesDetalleInDTO> findAll() {
        List<Showtimes> showtimes = this.showtimesRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();

        List<ShowtimesDetalleInDTO> detalleInDTOList = new ArrayList<>();

        for(int i = 0; i < showtimes.size(); i++){
            ShowtimesDetalleInDTO showtimesDetalleInDTO = new ShowtimesDetalleInDTO();
            showtimesDetalleInDTO.setDate(showtimes.get(i).getDate());
            showtimesDetalleInDTO.setId(showtimes.get(i).getId());

            List<Movies> items = showtimes.get(i).getItems().stream()
                    .map(showtimesItem -> {
                        Movies movies = modelMapper.map(moviesClient.findById(showtimesItem.getIdMovie()).getData(),Movies.class);
                        return movies;
                    }).collect(Collectors.toList());

            showtimesDetalleInDTO.setItems(items);
            detalleInDTOList.add(showtimesDetalleInDTO);
        }



        return detalleInDTOList;
    }

    @Override
    public ShowtimesDetalleInDTO findById(Long id) {
        Optional<Showtimes> showtimes = this.showtimesRepository.findById(id);

        if(!showtimes.isEmpty()){
            ShowtimesDetalleInDTO showtimesDetalleInDTO = new ShowtimesDetalleInDTO();
            showtimesDetalleInDTO.setId(showtimes.get().getId());
            showtimesDetalleInDTO.setDate(showtimes.get().getDate());

            ModelMapper modelMapper = new ModelMapper();
            List<Movies> movies = showtimes.get().getItems().stream()
                                .map(showtimesItem -> {
                                    Movies movie = modelMapper.map(moviesClient.findById(showtimesItem.getIdMovie()).getData(),Movies.class);
                                    return movie;
                                }).collect(Collectors.toList());
            showtimesDetalleInDTO.setItems(movies);

            return showtimesDetalleInDTO;
        }
        return null;
    }

    @Override
    public Showtimes save(Showtimes showtimes) {
        Optional<Showtimes> showtimesExistente = this.showtimesRepository.findById(showtimes.getId());
        if(!showtimesExistente.isEmpty()){
            return this.showtimesRepository.save(showtimes);
        }

        return null;
    }

    @Override
    public Boolean validarMovieRegistrada(Long id) {
        ShowtimesItem showtimesItem = this.showtimesItemRepository.findByIdMovie(id);

        if(showtimesItem != null){
            return true;
        }

        return false;
    }


}
