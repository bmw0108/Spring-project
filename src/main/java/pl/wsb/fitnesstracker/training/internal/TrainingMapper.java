package pl.wsb.fitnesstracker.training.internal;

import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.training.api.TrainingRequest;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;

import java.time.ZoneOffset;
import java.util.Date;

@Component
class TrainingMapper {

    /** Entity → DTO */
    TrainingDto toDto(pl.wsb.fitnesstracker.training.api.Training e) {
        return new TrainingDto(
                e.getId(),
                new UserDto(    // nested user
                        e.getUser().getId(),
                        e.getUser().getFirstName(),
                        e.getUser().getLastName(),
                        e.getUser().getBirthdate(),
                        e.getUser().getEmail()
                ),
                e.getStartTime(),  // direct pass-through
                e.getEndTime(),
                e.getActivityType(),
                e.getDistance(),
                e.getAverageSpeed()
        );
    }

    /** Request + User → Entity */
    pl.wsb.fitnesstracker.training.api.Training toEntity(
            TrainingRequest r,
            User u
    ) {
        Date st = Date.from(r.getStartTime().atOffset(ZoneOffset.UTC).toInstant());
        Date et = Date.from(r.getEndTime().  atOffset(ZoneOffset.UTC).toInstant());
        return new pl.wsb.fitnesstracker.training.api.Training(
                u, st, et, r.getActivityType(), r.getDistance(), r.getAverageSpeed()
        );
    }
}
