package com.spring.batch.springbatch.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.*;
import org.springframework.batch.test.AssertFile;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@SpringBatchTest
public class AccountServiceTests {
    ObjectMapper objectMapper= new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    static HttpHeaders authHeaders = new HttpHeaders();

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private JobRepositoryTestUtils jobRepositoryTestUtils;

    @AfterEach
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
    @DisplayName(" end-to-end-Job-testing")
    public void givenAccountReferenceOutput_whenJobExecuted_thenSuccess() throws Exception {

		FileSystemResource expectedResult = new FileSystemResource("src/main/resources/dataSource.txt");
		FileSystemResource actualResult = new FileSystemResource("src/main/resources/dataSource.txt");

		JobExecution jobExecution = jobLauncherTestUtils.launchJob(defaultJobParameters());
		JobInstance actualJobInstance = jobExecution.getJobInstance();
		ExitStatus actualJobExitStatus = jobExecution.getExitStatus();

		Assert.assertEquals("COMPLETED", actualJobExitStatus.getExitCode());
		Assert.assertEquals("job",actualJobInstance.getJobName());
		AssertFile.assertFileEquals(expectedResult, actualResult);
    }

    private JobParameters defaultJobParameters() {
        JobParametersBuilder paramsBuilder = new JobParametersBuilder();
        paramsBuilder.addString("file.input", "99728382835|426.00|FUND TRANSFER TO MayBank|2023-07-26|15:19:11|228");
        paramsBuilder.addString("file.output", "99728382835|426.00|FUND TRANSFER TO MayBank|2023-07-26|15:19:11|228");
        return paramsBuilder.toJobParameters();
    }
}
