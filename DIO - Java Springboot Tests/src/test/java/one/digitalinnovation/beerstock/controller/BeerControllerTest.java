package one.digitalinnovation.beerstock.controller;

import one.digitalinnovation.beerstock.builder.BeerDTOBuilder;
import one.digitalinnovation.beerstock.dto.BeerDTO;
import one.digitalinnovation.beerstock.exception.BeerNotFoundException;
import one.digitalinnovation.beerstock.mapper.BeerMapper;
import one.digitalinnovation.beerstock.repository.BeerRepository;
import one.digitalinnovation.beerstock.service.BeerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import static one.digitalinnovation.beerstock.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BeerControllerTest {

    private MockMvc mockMvc;
    private BeerMapper beerMapper = BeerMapper.INSTANCE;

    @Mock
    private BeerService beerService;

    @Mock
    private BeerRepository beerRepository;

    @InjectMocks
    private BeerController beerController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(beerController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void createBeer() throws Exception {
        BeerDTO DTO = BeerDTOBuilder.builder().build().toBeerDTO();

        Mockito.when(beerService.createBeer(DTO)).thenReturn(DTO);

        mockMvc.perform(
                post("/api/v1/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(DTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(DTO.getName())))
                .andExpect(jsonPath("$.brand", is(DTO.getBrand())))
                .andExpect(jsonPath("$.quantity", is(DTO.getQuantity())))
                .andExpect(jsonPath("$.type", is(DTO.getType().toString())));
    }

    @Test
    void createInvalidBeer() throws Exception {
        BeerDTO DTO = BeerDTOBuilder.builder().build().toBeerDTO();
        DTO.setBrand(null);

        mockMvc.perform(
                post("/api/v1/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(DTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getBeer() throws Exception {
        BeerDTO DTO = BeerDTOBuilder.builder().build().toBeerDTO();

        Mockito.when(beerService.findByName(DTO.getName())).thenReturn(DTO);

        mockMvc.perform(get("/api/v1/beers/" + DTO.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(DTO.getName())))
                .andExpect(jsonPath("$.brand", is(DTO.getBrand())))
                .andExpect(jsonPath("$.quantity", is(DTO.getQuantity())))
                .andExpect(jsonPath("$.type", is(DTO.getType().toString())));
    }

    @Test
    void getInvalidBeer() throws Exception {
        BeerDTO DTO = BeerDTOBuilder.builder().build().toBeerDTO();

        Mockito.when(beerService.findByName(DTO.getName())).thenThrow(BeerNotFoundException.class);

        mockMvc.perform(get("/api/v1/beers/" + DTO.getName()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBeer() throws Exception {
        BeerDTO DTO = BeerDTOBuilder.builder().build().toBeerDTO();

        Mockito.doNothing().when(beerService).deleteById(DTO.getId());

        mockMvc.perform(delete("/api/v1/beers/" + DTO.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteInvalidBeer() throws Exception {
        BeerDTO DTO = BeerDTOBuilder.builder().build().toBeerDTO();

        Mockito.doThrow(BeerNotFoundException.class).when(beerService).deleteById(DTO.getId());

        mockMvc.perform(delete("/api/v1/beers/" + DTO.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void incrementBeer() throws Exception {
        BeerDTO DTO = BeerDTOBuilder.builder().build().toBeerDTO();

        Mockito.when(beerService.increment(DTO.getId(), 10)).thenReturn(DTO);

        mockMvc.perform(
                patch("/api/v1/beers/" + DTO.getId() + "/increment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(DTO)))
                .andExpect(status().isOk());
    }

    @Test
    void incrementInvalidBeer() throws Exception {
        BeerDTO DTO = BeerDTOBuilder.builder().build().toBeerDTO();

        Mockito.when(beerService.increment(DTO.getId(), 10)).thenThrow(BeerNotFoundException.class);

        mockMvc.perform(
                patch("/api/v1/beers/" + DTO.getId() + "/increment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(DTO)))
                .andExpect(status().isNotFound());
    }
}
