package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wsb.fitnesstracker.training.api.*;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserNotFoundException;
import pl.wsb.fitnesstracker.user.api.UserProvider;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository repo;
    private final UserProvider      users;
    private final TrainingMapper    mapper;

    @Override
    public List<TrainingDto> getAllTrainings() {
        return repo.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<TrainingDto> getTrainingsByUser(Long userId) {
        return repo.findAll().stream()
                .filter(t -> t.getUser().getId().equals(userId))
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<TrainingDto> getFinishedTrainingsAfter(LocalDate date) {
        Date cutoff = Date.from(date.atStartOfDay().toInstant(ZoneOffset.UTC));
        return repo.findAll().stream()
                .filter(t -> t.getEndTime().after(cutoff))
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<TrainingDto> getTrainingsByActivityType(String activityType) {
        return repo.findAll().stream()
                .filter(t -> t.getActivityType().name().equalsIgnoreCase(activityType))
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public TrainingDto createTraining(TrainingRequest r) {
        User u = users.getUser(r.getUserId())
                .orElseThrow(() -> new UserNotFoundException(r.getUserId()));
        return mapper.toDto(repo.save(mapper.toEntity(r, u)));
    }

    @Override
    public TrainingDto updateTraining(Long id, TrainingRequest r) {
        return repo.findById(id)
                .map(existing -> {
                    User u = users.getUser(r.getUserId())
                            .orElseThrow(() -> new UserNotFoundException(r.getUserId()));
                    var tmp = mapper.toEntity(r, u);
                    existing.updateFrom(tmp);
                    return repo.save(existing);
                })
                .map(mapper::toDto)
                .orElseThrow(() -> new TrainingNotFoundException(id));
    }
    @Override
    public void deleteTraining(Long id) {
        if (!repo.existsById(id)) {
            throw new TrainingNotFoundException(id);
            }
        repo.deleteById(id);
    }
}
