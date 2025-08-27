package co.com.pragma.api;

import co.com.pragma.api.dto.UserRequestDTO;
import co.com.pragma.model.user.User;
import co.com.pragma.usecase.user.UserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class Handler {
    private final UserUseCase userUseCase;

    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(User.class)
                .flatMap(userUseCase::createUser)
                .flatMap(userCreated -> ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(userCreated)
                );
    }

    private co.com.pragma.model.user.User mapDtoToDomain(UserRequestDTO dto) {
        co.com.pragma.model.user.User u = new co.com.pragma.model.user.User(
                null,
                dto.getName(),
                dto.getLastName(),
                dto.getBirthDate(),
                dto.getAddress(),
                dto.getPhoneNumber(),
                dto.getEmail(),
                dto.getBaseSalary(),
                dto.getIdentityDocument(),
                null
        );
        u.setName(dto.getName());
        u.setLastName(dto.getLastName());
        u.setEmail(dto.getEmail());
        u.setBaseSalary(dto.getBaseSalary());
        u.setBirthDate(dto.getBirthDate());
        return u;
    }

    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        // useCase.logic();
        return ServerResponse.ok().bodyValue("");
    }
}
