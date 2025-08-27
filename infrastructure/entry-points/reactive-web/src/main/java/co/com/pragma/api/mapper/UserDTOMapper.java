package co.com.pragma.api.mapper;

import co.com.pragma.api.dto.UserDTO;
import co.com.pragma.model.user.User;
/*import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")*/
public interface UserDTOMapper {
    User toDomain(UserDTO dto);
    UserDTO toDTO(User domain);
}
