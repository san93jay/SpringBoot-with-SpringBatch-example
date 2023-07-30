package com.spring.batch.springbatch.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.batch.springbatch.security.dto.AccountSearchDTO;
import org.junit.After;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@SpringBatchTest
class MaybankApplicationTests {

	ObjectMapper objectMapper= new ObjectMapper();
	@Autowired
	private MockMvc mockMvc;
	static HttpHeaders authHeaders = new HttpHeaders();

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private JobRepositoryTestUtils jobRepositoryTestUtils;

	private static final String END_POINT="/api/batch";

	@After
	public void cleanUp() {
		jobRepositoryTestUtils.removeJobExecutions();
	}


	@BeforeAll
	public static void init() {

		String token = new String(Base64.getEncoder().encodeToString(("admin" + ":" + "admin").getBytes()));
		System.out.println(token + " Token");
		authHeaders.set("Authorization", "Basic " + token);
	}

	@Test
	@Order(1)
	@DisplayName("status200WhenValidRequest")
	public void status200WhenValidRequest_whenJobExecuted_thenSuccess() throws Exception {


		String PostApiwithExistentRequest=END_POINT+"/save";
		mockMvc
				.perform(post(PostApiwithExistentRequest))
				.andDo(print())
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
	}

	@Test
	@Order(2)
	@DisplayName("statusCode404WhenNonExistentRequested")
	void statusCode404WhenNonExistentRequested() throws Exception {
		String getApiwithNonExistentRequest=END_POINT+"/-1";
		mockMvc
				.perform(get(getApiwithNonExistentRequest).headers(authHeaders))
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	@Order(3)
	@DisplayName("statusCode200WhenExistentRequested")
	void statusCode200whenExistentRequested() throws Exception {
		String getApiwithExistentRequest=END_POINT+"/getById?page=0&size=10";
		AccountSearchDTO accountSearchDTO=new AccountSearchDTO("8872838283","222","ATM WITHDRWAL");
		mockMvc
				.perform(get(getApiwithExistentRequest).headers(authHeaders)
						.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(accountSearchDTO)))
				        .andDo(print())
				        .andExpect(status().isOk());
	}

	@Test
	@Order(4)
	@DisplayName("statusCode400WhenInvalidIdRequested")
	void statusCode400WhenInvalidIdRequested() throws Exception {
		String getApiwithInvalidRequest=END_POINT+"/getById";
		AccountSearchDTO accountSearchDTO=new AccountSearchDTO("8872838283","222","ATM WITHDRWAL");
		mockMvc
				.perform(get(getApiwithInvalidRequest).headers(authHeaders)
						.contentType(MediaType.APPLICATION_JSON))
				        .andDo(print())
				        .andExpect(status().isBadRequest());
	}
}
