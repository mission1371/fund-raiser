package com.eestienergia.fundraiser.rest.inventory;

import com.eestienergia.fundraiser.domain.InventoryRecord;
import com.eestienergia.fundraiser.domain.exception.UnsupportedStockOperationException;
import com.eestienergia.fundraiser.domain.service.InventoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class InventoryRestControllerUnitTest {

    @Mock
    private InventoryService service;

    @Mock
    private InventoryRecordResourceConverter converter;

    @InjectMocks
    private InventoryRestController controller;

    private MockMvc mvc;
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        mapper = new ObjectMapper();
    }

    @Test
    void shouldGetInventory() throws Exception {
        // given
        given(converter.convert(any())).willReturn(aResponse());
        given(service.fetchInventory()).willReturn(Collections.singletonList(InventoryRecord.builder().build()));

        // when
        final ResultActions result = mvc.perform(get("/inventory"));

        // then
        result
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[0].productCode").value("EP1"))
                .andExpect(jsonPath("$[0].name").value("Muffin"))
                .andExpect(jsonPath("$[0].stock").value(10));
    }

    @Test
    void shouldIncreaseStock() throws Exception {
        // given
        final InventoryUpdateRequestResource request = new InventoryUpdateRequestResource();
        request.setProductCode("EP1");
        request.setAddedStock(10L);
        given(converter.convert(any())).willReturn(aResponse());

        // when
        final ResultActions result = mvc.perform(put("/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)));

        // then
        verify(service).increaseStock(request.getProductCode(), request.getAddedStock());
        result
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productCode").value("EP1"))
                .andExpect(jsonPath("$.name").value("Muffin"))
                .andExpect(jsonPath("$.stock").value(10));
    }

    @Test
    void shouldUpdateStock() throws Exception {
        // given
        final InventoryUpdateRequestResource request = new InventoryUpdateRequestResource();
        request.setProductCode("EP1");
        request.setStock(10L);
        given(converter.convert(any())).willReturn(aResponse());

        // when
        final ResultActions result = mvc.perform(put("/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)));

        // then
        verify(service).updateStock(request.getProductCode(), request.getStock());
        result
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productCode").value("EP1"))
                .andExpect(jsonPath("$.name").value("Muffin"))
                .andExpect(jsonPath("$.stock").value(10));
    }

    @Test
    void shouldThrowExceptionWhenStockInformationNotProvided() {
        // given
        final InventoryUpdateRequestResource request = new InventoryUpdateRequestResource();
        request.setProductCode("EP1");
        request.setStock(null);
        request.setAddedStock(null);

        // when
        final Throwable throwable = catchThrowable(() -> controller.updateStock(request));

        // then
        assertThat(throwable).isInstanceOf(UnsupportedStockOperationException.class);
        assertThat(throwable).hasMessage("Unsupported stock operation!");
    }

    @Test
    void shouldNotAcceptEmptyBody() throws Exception {

        // when
        final ResultActions result = mvc.perform(put("/inventory")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        result
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotAcceptWithoutProductCode() throws Exception {
        // given
        final InventoryUpdateRequestResource request = new InventoryUpdateRequestResource();
        request.setProductCode(null);
        request.setStock(null);
        request.setAddedStock(null);

        // when
        final ResultActions result = mvc.perform(put("/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)));

        // then
        result
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotAcceptWithBlankProductCode() throws Exception {
        // given
        final InventoryUpdateRequestResource request = new InventoryUpdateRequestResource();
        request.setProductCode(" ");
        request.setStock(null);
        request.setAddedStock(null);

        // when
        final ResultActions result = mvc.perform(put("/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)));

        // then
        result
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotAcceptWithEmptyProductCode() throws Exception {
        // given
        final InventoryUpdateRequestResource request = new InventoryUpdateRequestResource();
        request.setProductCode("");
        request.setStock(null);
        request.setAddedStock(null);

        // when
        final ResultActions result = mvc.perform(put("/inventory")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)));

        // then
        result
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    private InventoryRecordResource aResponse() {
        return InventoryRecordResource.builder()
                .name("Muffin")
                .productCode("EP1")
                .stock(10)
                .build();
    }
}