package com.spring.batch.springbatch.security.stepsdefinations;

import com.spring.batch.springbatch.security.dto.AccountDTO;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.Base64;

public class MyAccountStepdefs {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    String body;

    AccountDTO accountDTO;
    static HttpHeaders authHeaders = new HttpHeaders();
    ResultActions resultActions;
    private static final String server="/api/batch";

    @BeforeAll
    public static void init() {

        String token = new String(Base64.getEncoder().encodeToString(("admin" + ":" + "admin").getBytes()));
        System.out.println(token + " Token");
        authHeaders.set("Authorization", "Basic " + token);
    }

    /*@Given("Send Account details with updated value")
    public void sendAccountDetailsWithUpdatedValue() throws JsonProcessingException {
        accountDTO=AccountDTO.builder()
                .accountNumber("99728382836")
                .id(1)
                .customerId("224")
                .trxDate("2023-07-26")
                .trxTime("15:19:11")
                .trxAmount(427.00f)
                .description("FUND TRANSFER TO MyBank")
                .build();
        body = objectMapper.writeValueAsString(accountDTO);
    }

    @When("I send a put request to the URL {string} to update Account details")
    public void iSendAPutRequestToTheURLToUpdateAccountDetails(String endPoint) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                put(URI.create(server + endPoint))
                        .content(body)
                        .headers(authHeaders)
                        .contentType("application/json");
        resultActions = mockMvc.perform(mockHttpServletRequestBuilder);

    }

    @Then("update given account with updated value and receive status code as {int}")
    public void updateGivenAccountWithUpdatedValue(int status) throws Exception {
        resultActions.andExpect(status().is(status));

    }*/

    @Given("Send Account details with updated value")
    public void send_account_details_with_updated_value() throws JsonProcessingException {
        accountDTO=AccountDTO.builder()
                .accountNumber("99728382836")
                .id(1)
                .customerId("224")
                .trxDate("2023-07-26")
                .trxTime("15:19:11")
                .trxAmount(427.00f)
                .description("FUND TRANSFER TO MyBank")
                .build();
        body = objectMapper.writeValueAsString(accountDTO);
        throw new io.cucumber.java.PendingException();
    }
    @When("I send a put request to the URL {string} to update Account details")
    public void i_send_a_put_request_to_the_url_to_update_account_details(String endPoint) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                put(URI.create(server + endPoint))
                        .content(body)
                        .headers(authHeaders)
                        .contentType("application/json");
        resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
        throw new io.cucumber.java.PendingException();
    }
    @Then("update given account with updated value and receive status code as {int}")
    public void update_given_account_with_updated_value_and_receive_status_code_as(Integer status) throws Exception {
        resultActions.andExpect(status().is(status));
        throw new io.cucumber.java.PendingException();
    }

}
