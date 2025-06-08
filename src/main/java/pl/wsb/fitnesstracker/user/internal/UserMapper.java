package pl.wsb.fitnesstracker.user.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.user.api.SimpleUserDto;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;

@Component
class UserMapper {

    UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail()
        );
    }

    User toEntity(UserDto userDto) {
        return new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email()
        );
    }

    /** New: map to SimpleUserDto using email prefix */
    SimpleUserDto toSimpleDto(User user) {
        String email = user.getEmail();
        String username = email.contains("@")
                ? email.substring(0, email.indexOf("@"))
                : email;
        return new SimpleUserDto(user.getId(), username);
    }
}
