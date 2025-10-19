package io.accelerate.solutions.HLO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class HelloSolutionTest {
    private HelloSolution helloSolution;

    @BeforeEach
    void setHelloSolution() {
        helloSolution = new HelloSolution();
    }

    @Test
    void testHelloSolution() {
        var name = "Mr. X";
        var result = helloSolution.hello(name);
        assertThat(result, equalTo("Hello, Mr. X!"));

    }
}
