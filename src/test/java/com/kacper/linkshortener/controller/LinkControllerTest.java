package com.kacper.linkshortener.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kacper.linkshortener.model.request.LinkRequestModel;
import com.kacper.linkshortener.model.response.LinkCreationResponse;
import com.kacper.linkshortener.model.response.LinkRedirectResponse;
import com.kacper.linkshortener.service.LinkService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDateTime;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(controllers = LinkController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class LinkControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LinkService linkService;

    @Test
    void createShortenedLink_CreateValidLink_Success() throws Exception {
        LinkRequestModel linkRequestModel = new LinkRequestModel();
        LinkCreationResponse linkCreationResponse = new LinkCreationResponse("localhost:8080/liLINK123k", LocalDateTime.now());
        when(linkService.createShortenedLink(any(), any())).thenReturn(linkCreationResponse);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(linkRequestModel);

        ResultActions response = mockMvc.perform(post("/link")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(jsonString));
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.link", CoreMatchers.is(linkCreationResponse.getLink())));
    }

    @Test
    void createShortenedLink_CreateInvalidLink_Fail() throws Exception {
        LinkRequestModel linkRequestModel = new LinkRequestModel();
        when(linkService.createShortenedLink(any(), any())).thenThrow(new RuntimeException());
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(linkRequestModel);
        ResultActions response = mockMvc.perform(post("/link")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(jsonString));
        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
    @Test
    void RetrieveRedirectLink_RetrieveValidLink_SuccessAndRedirect() throws Exception {
        LinkRedirectResponse linkRedirectResponse = new LinkRedirectResponse();
        linkRedirectResponse.setRedirectLink("www.youtube.com");
        when(linkService.retrieveLink(anyString())).thenReturn(linkRedirectResponse);

        ResultActions response = mockMvc.perform(get("/liLINK123k")
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
    @Test
    void RetrieveRedirectLink_RetrieveInvalidLink_NotFound() throws Exception{
        when(linkService.retrieveLink(anyString())).thenThrow(new RuntimeException());

        ResultActions response = mockMvc.perform(get("/liLINK123k")
                .contentType(MediaType.APPLICATION_JSON));
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}