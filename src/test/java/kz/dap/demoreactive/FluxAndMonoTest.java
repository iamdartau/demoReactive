package kz.dap.demoreactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

    @Test
    public void fluxTest() {
        Flux<String> flux = Flux.just("spring", "spring boot", "web reactive")
//                .concatWith(Flux.error(new RuntimeException("Exception occurred")))
                .concatWith(Flux.just("After error"))
                .log();
        flux
                .subscribe(System.out::println,
                        e -> System.err.println("exception is :" + e),
                        () -> System.out.println("completed"));
    }

    @Test
    public void fluxTestElements() {
        Flux<String> stringFlux = Flux.just("spring", "spring boot", "web reactive")
                .log();
        StepVerifier
                .create(stringFlux)
                .expectNext("spring")
                .expectNext("spring boot")
                .expectNext("web reactive")
                .verifyComplete();
    }

    @Test
    public void monoTest() {
        Mono<String> spring = Mono.just("spring");
        StepVerifier
                .create(spring.log())
                .expectNext("spring")
                .verifyComplete();
    }
}
