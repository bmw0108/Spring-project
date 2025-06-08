package pl.wsb.fitnesstracker.user.api;

/**
 * Minimal user info for the “simple” list: ID + username (email prefix).
 */
public record SimpleUserDto(
        Long id,
        String username
) {}