package one.digitalinnovation.beerstock.service;

import one.digitalinnovation.beerstock.builder.BeerDTOBuilder;
import one.digitalinnovation.beerstock.dto.BeerDTO;
import one.digitalinnovation.beerstock.entity.Beer;
import one.digitalinnovation.beerstock.exception.BeerAlreadyRegisteredException;
import one.digitalinnovation.beerstock.exception.BeerNotFoundException;
import one.digitalinnovation.beerstock.exception.BeerStockExceededException;
import one.digitalinnovation.beerstock.mapper.BeerMapper;
import one.digitalinnovation.beerstock.repository.BeerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class BeerServiceTest {

    @Mock
    private BeerRepository beerRepository;

    private BeerMapper beerMapper = BeerMapper.INSTANCE;

    @InjectMocks
    private BeerService beerService;

    @Test
    void createValidBeer() throws BeerAlreadyRegisteredException {
        BeerDTO DTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer beer = beerMapper.toModel(DTO);

        // Prepare mock for function calls
        Mockito.when(beerRepository.findByName(DTO.getName())).thenReturn(Optional.empty());
        Mockito.when(beerRepository.save(beer)).thenReturn(beer);

        BeerDTO createBeer = beerService.createBeer(DTO);

        assertEquals(DTO.getId(), createBeer.getId());
        assertEquals(DTO.getName(), createBeer.getName());
        assertEquals(DTO.getQuantity(), createBeer.getQuantity());
        assertEquals(DTO.getMax(), createBeer.getMax());
        assertEquals(DTO.getBrand(), createBeer.getBrand());
        assertEquals(DTO.getType(), createBeer.getType());
    }

    @Test
    void createDuplicatedBeer() {
        BeerDTO DTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer beer = beerMapper.toModel(DTO);

        Mockito.when(beerRepository.findByName(DTO.getName())).thenReturn(Optional.of(beer));

        assertThrows(BeerAlreadyRegisteredException.class, () -> beerService.createBeer(DTO));
    }
    
    @Test
    void deleteExistentBeer() {
        BeerDTO DTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer beer = beerMapper.toModel(DTO);

        Mockito.when(beerRepository.findById(DTO.getId())).thenReturn(Optional.of(beer));

        Assertions.assertDoesNotThrow(() -> beerService.deleteById(DTO.getId()));
    }
    
    @Test
    void deleteNonexistentBeer() {
        BeerDTO DTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer beer = beerMapper.toModel(DTO);

        Mockito.when(beerRepository.findById(DTO.getId())).thenReturn(Optional.empty());

        assertThrows(BeerNotFoundException.class, () -> beerService.deleteById(DTO.getId()));
    }

    @Test
    void incrementExistentBeer() throws BeerNotFoundException, BeerStockExceededException {
        BeerDTO DTO = BeerDTOBuilder.builder().build().toBeerDTO();
        Beer beer = beerMapper.toModel(DTO);
        int newQuantity = DTO.getQuantity() + 3;

        Mockito.when(beerRepository.findById(DTO.getId())).thenReturn(Optional.of(beer));
        Mockito.when(beerRepository.save(beer)).thenReturn(beer);

        BeerDTO incrementedDTO = beerService.increment(DTO.getId(), 3);

        assertEquals(newQuantity, incrementedDTO.getQuantity());
        assertEquals(DTO.getId(), incrementedDTO.getId());
        assertEquals(DTO.getName(), incrementedDTO.getName());
        assertEquals(DTO.getMax(), incrementedDTO.getMax());
        assertEquals(DTO.getBrand(), incrementedDTO.getBrand());
        assertEquals(DTO.getType(), incrementedDTO.getType());
    }

    @Test
    void incrementNonexistentBeer() {
        BeerDTO DTO = BeerDTOBuilder.builder().build().toBeerDTO();

        Mockito.when(beerRepository.findById(DTO.getId())).thenReturn(Optional.empty());

        assertThrows(BeerNotFoundException.class, () -> beerService.increment(DTO.getId(), 3));
    }
}
