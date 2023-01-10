package kz.dap.demoreactive.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.time.Duration;

@RestController
public class Controller {

    @GetMapping("/flux")
    public Flux<Integer> getFlux() {

        return Flux.just(1, 2, 3, 4, 5)
//                .delayElements(Duration.ofSeconds(1))
                .log();
    }

    @GetMapping(value = "/flux-stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Integer> getFluxStream() {

        return Flux.just(1, 2, 3, 4, 5)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }

    @GetMapping(value = "/flux-stream-two", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Long> getFluxStream2() {

        return Flux.interval(Duration.ofSeconds(2))
                .log();
    }

    @GetMapping(value = "/mono", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<Integer> getMono() {
        return Mono
                .just(1)
                .log();
    }

}
