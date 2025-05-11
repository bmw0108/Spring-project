package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserNotFoundException;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

/**
 * Controller that handles HTTP requests for user management.
 * Provides endpoints for creating, retrieving, updating, and deleting users,
 * as well as searching for users based on various parameters.
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    /**
     * Calculates the age of a user based on their birthdate and the current date.
     *
     * @param birthdate the user's birthdate
     * @param now the current date
     * @return the age of the user in years
     */
    private int getAge(LocalDate birthdate, LocalDate now) {
        return Period.between(birthdate, now).getYears();
    }

    /**
     * Retrieves all users and maps them to preview DTOs.
     *
     * @return a list of preview DTOs for all users
     */
    @GetMapping
    public List<UserPreviewDto> getAllUsers() {
        return userService.findAllUsers().stream()
                .map(userMapper::toPreviewDto)
                .toList();
    }

    /**
     * Retrieves a user by their ID and maps it to a DTO.
     *
     * @param id the ID of the user to retrieve
     * @return the user as a DTO
     * @throws UserNotFoundException if the user with the specified ID does not exist
     */
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userMapper.toDto(
                userService.getUser(id)
                        .orElseThrow(() -> new UserNotFoundException(id))
        );
    }

    /**
     * Searches for users based on various optional criteria.
     * If a parameter is not provided, it is ignored in the search.
     *
     * @param firstName the first name of the user to search for
     * @param lastName the last name of the user to search for
     * @param email the email of the user to search for
     * @param birthdate the birthdate of the user to search for
     * @param minAge the minimum age of the user to search for
     * @param maxAge the maximum age of the user to search for
     * @return a list of preview DTOs for users matching the search criteria
     */
    @GetMapping("/search")
    public List<UserPreviewDto> searchUsers(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthdate,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge
    ) {
        LocalDate now = LocalDate.now();

        return userService.findAllUsers().stream()
                .filter(user -> firstName == null || user.getFirstName().equalsIgnoreCase(firstName))
                .filter(user -> lastName == null || user.getLastName().equalsIgnoreCase(lastName))
                .filter(user -> email == null || user.getEmail().toLowerCase().contains(email.toLowerCase()))
                .filter(user -> birthdate == null || user.getBirthdate().isEqual(birthdate))
                .filter(user -> minAge == null || getAge(user.getBirthdate(), now) >= minAge)
                .filter(user -> maxAge == null || getAge(user.getBirthdate(), now) <= maxAge)
                .map(userMapper::toPreviewDto)
                .toList();
    }

    /**
     * Adds a new user based on the provided user DTO.
     *
     * @param userDto the user DTO containing the user data
     * @return the added user as a DTO
     */
    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User savedUser = userService.createUser(user);
        return userMapper.toDto(savedUser);
    }

    /**
     * Updates an existing user based on the provided user DTO and user ID.
     *
     * @param id the ID of the user to update
     * @param userDto the user DTO containing the updated data
     * @return the updated user as a DTO
     * @throws UserNotFoundException if no user with the specified ID is found
     */
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User userToUpdate = userMapper.toEntity(userDto);
        User updatedUser = userService.updateUser(id, userToUpdate);
        return userMapper.toDto(updatedUser);
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @throws UserNotFoundException if no user with the specified ID is found
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
