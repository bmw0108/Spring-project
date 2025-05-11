package pl.wsb.fitnesstracker.user.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.user.api.User;

/**
 * A component responsible for mapping between User, UserDto, and UserPreviewDto.
 * This class is used to convert between different representations of user data.
 */
@Component
class UserMapper {

    /**
     * Converts a User entity to a UserDto.
     *
     * @param user the User entity to convert
     * @return the corresponding UserDto
     */
    UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getEmail());
    }

    /**
     * Converts a UserDto to a User entity.
     *
     * @param userDto the UserDto to convert
     * @return the corresponding User entity
     */
    User toEntity(UserDto userDto) {
        return new User(
                userDto.firstName(),
                userDto.lastName(),
                userDto.birthdate(),
                userDto.email());
    }

    /**
     * Converts a User entity to a UserPreviewDto, which includes only basic information
     * like the user's ID and a preview username (derived from the user's email).
     *
     * @param user the User entity to convert
     * @return the corresponding UserPreviewDto
     */
    UserPreviewDto toPreviewDto(User user) {
        String email = user.getEmail();
        String username = email.contains("@")
                ? email.substring(0, email.indexOf("@")).toLowerCase()
                : email.toLowerCase();

        return new UserPreviewDto(user.getId(), username);
    }
}
