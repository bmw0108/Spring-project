package pl.wsb.fitnesstracker.user.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.user.api.SimpleUserDto;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserNotFoundException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    /** GET all users (full DTO) */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers().stream()
                .map(userMapper::toDto)
                .toList();
    }

    /** GET simple list: only ID and email-prefix username */
    @GetMapping("/simple")
    public List<SimpleUserDto> getSimpleUsers() {
        return userService.findAllUsers().stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }

    /** GET one user by ID */
    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        var user = userService.getUser(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toDto(user);
    }

    /** GET users matching (partial, case-insensitive) email */
    @GetMapping("/email")
    public List<UserDto> findByEmail(@RequestParam String email) {
        return userService.findAllUsers().stream()
                .filter(u -> u.getEmail().toLowerCase()
                        .contains(email.toLowerCase()))
                .map(userMapper::toDto)
                .toList();
    }

    /** GET users born before the given date */
    @GetMapping("/older/{date}")
    public List<UserDto> getOlderThan(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date) {

        return userService.findAllUsers().stream()
                .filter(u -> u.getBirthdate().isBefore(date))
                .map(userMapper::toDto)
                .toList();
    }

    /** CREATE user — must return 201 */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody UserDto dto) {
        userService.createUser(userMapper.toEntity(dto));
    }

    /** UPDATE user */
    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody UserDto dto) {
        userService.updateUser(id, userMapper.toEntity(dto));
    }

    /** DELETE user — must return 204 */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
