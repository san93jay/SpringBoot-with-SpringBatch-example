package com.spring.batch.springbatch.security.bddTestRunner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "C://Learning-tech/maybank/src/test/java/com/spring/batch/maybank/Account.feature",
        glue = "stepsdefinations",
        plugin = "pretty")
public class AccountRunnerTest {
}
