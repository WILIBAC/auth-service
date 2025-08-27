package co.com.pragma.usecase.user;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class UserUseCase {
    private final UserRepository userRepository;

    public Mono<User> createUser(User user) {
        return userRepository.findByEmail(user.getEmail())
                .flatMap(existing -> Mono.<User>error(new IllegalArgumentException("Email already registered")))
                .switchIfEmpty(Mono.defer(() -> userRepository.save(user)));
    }

    private Mono<User> validateUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()
                || user.getLastName() == null || user.getLastName().isEmpty()
                || user.getEmail() == null || user.getEmail().isEmpty()
                || user.getBaseSalary() == null) {
            return Mono.error(new IllegalArgumentException("Required fields empty"));
        }

        if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return Mono.error(new IllegalArgumentException("Invalid email format"));
        }

        if (user.getBaseSalary().compareTo(BigDecimal.ZERO) < 0 || user.getBaseSalary().compareTo(new BigDecimal("15000000")) > 0) {
            return Mono.error(new IllegalArgumentException("Salary out of range"));
        }

        return Mono.just(user);
    }
}
