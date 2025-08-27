package co.com.pragma.r2dbc.mapper;

import co.com.pragma.r2dbc.entity.UserEntity;
import co.com.pragma.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    UserEntity toEntity(User user);
    User toDomain(UserEntity entity);
}

