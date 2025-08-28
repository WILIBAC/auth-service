package co.com.pragma.r2dbc;

import co.com.pragma.model.user.User;
import co.com.pragma.model.user.gateways.UserRepository;
import co.com.pragma.r2dbc.entity.UserEntity;
import co.com.pragma.r2dbc.helper.ReactiveAdapterOperations;
import co.com.pragma.r2dbc.mapper.UserEntityMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Repository
public class MyReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        User,
        UserEntity,
        String,
        MyReactiveRepository
> implements UserRepository {

    private final MyReactiveRepository repository;
    private final UserEntityMapper mapper;

    public MyReactiveRepositoryAdapter(MyReactiveRepository repository, UserEntityMapper mapper) {
        super(repository, null, null);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<User> findByIdentityDocument(String identityDocument) {
        return repository.findByIdentityDocument(identityDocument)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<User> findById(String id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<User> findAll() {
        return repository.findAll().map(mapper::toDomain);
    }

    @Override
    public Mono<User> save(User user) {
        UserEntity data = mapper.toEntity(user);
        // Ensure the entity is treated as new so R2DBC performs INSERT, not UPDATE
        if (data != null) {
            data.setNew(true);
        }
        return repository.save(data).map(mapper::toDomain);
    }
}
