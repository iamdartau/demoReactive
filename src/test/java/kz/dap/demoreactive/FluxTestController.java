package kz.dap.demoreactive;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebFluxTest
public class FluxTestController {

    @Autowired
    WebTestClient webClient;

    @Test
    public void flux_approach_one() {
        Flux<Integer> responseBody = webClient.get()
                .uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Integer.class)
                .getResponseBody();

        StepVerifier
                .create(responseBody)
                .expectSubscription()
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .expectNext(5)
                .verifyComplete();
    }

    @Test
    public void flux_approach_two() {
        webClient.get()
                .uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Integer.class)
                .hasSize(5);
    }

    @Test
    public void flux_approach_three() {

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);

        EntityExchangeResult<List<Integer>> listEntityExchangeResult = webClient.get()
                .uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class).returnResult();

        assertEquals(integers,
                listEntityExchangeResult.getResponseBody());

    }

    @Test
    public void flux_approach_four() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);

        webClient.get()
                .uri("/flux")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Integer.class)
                .consumeWith(response -> {
                   assertEquals(integers, response.getResponseBody());
                });
    }

}
