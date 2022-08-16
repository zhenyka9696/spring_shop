package org.itstep.springshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Crud {
    @Autowired
    private MockMvc mvc;

    @Test
    @Order(1)
    public void getJewelries() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/jewelries")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].name",
                        Matchers.equalTo("French Pave Diamond Engagement Ring in Platinum (1/4 ct. tw.)")));

    }

    @Test
    @Order(2)
    public void getJewelriesById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/jewelries/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.name", Matchers.equalTo("French Pave Diamond Engagement Ring in Platinum (1/4 ct. tw.)")));

    }

    @ParameterizedTest // Создаем параметризованный тест
    @Order(3)
    @ValueSource(ints = {-1, 0, 1, 2, Integer.MAX_VALUE})
    public void getJewelriesById(int id) throws Exception {
        ResultActions actions = mvc.perform(MockMvcRequestBuilders.get("/jewelries/{id}", id).
                accept(MediaType.APPLICATION_JSON));
        actions
                .andExpect(status().isOk());
        switch (id) {
            case 1:
                actions
                        .andExpect(jsonPath("$.name",
                                Matchers.equalTo("French Pave Diamond Engagement Ring in Platinum (1/4 ct. tw.)")));
                break;
            case 2:
                actions
                        .andExpect(jsonPath("$.name",
                                Matchers.equalTo("Petite Solitaire Engagement Ring in Platinum")));
                break;
            default:
                actions
                        .andExpect(content().string("null"));
        }
    }

    @Test
    @Order(4)
    public void postJewelry() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.post
                        ("/jewelries")
                        .content(asJsonString(new Jewelry( 3L, "name", "color", 1., 2., new Material(1L,"m"))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", Matchers.equalTo("name")))
                .andExpect(jsonPath("$.price", Matchers.equalTo(1.)));

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(5)
    public void putJewelry() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.put
                        ("/jewelries/2")
                        .content(asJsonString(new Jewelry( 4L, "name4", "color", 1., 2., new Material(1L,"m"))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", Matchers.equalTo(1.)));

    }

    @Test
    @Order(6)
    public void deleteJewelry() throws Exception
    {
        mvc.perform( MockMvcRequestBuilders.delete("/jewelries/2", 1) )
                .andExpect(status().isOk());
    }
}
