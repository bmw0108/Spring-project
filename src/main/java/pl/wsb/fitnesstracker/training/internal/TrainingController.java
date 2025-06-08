package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.training.api.TrainingRequest;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
class TrainingController {

    private final TrainingServiceImpl service;
    private final TrainingMapper       mapper;

    @GetMapping
    public List<TrainingDto> all() {
        return service.getAllTrainings();
    }

    @GetMapping("/{userId}")
    public List<TrainingDto> byUser(@PathVariable Long userId) {
        return service.getTrainingsByUser(userId);
    }

    @GetMapping("/finished/{afterDate}")
    public List<TrainingDto> finishedAfter(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate afterDate
    ) {
        return service.getFinishedTrainingsAfter(afterDate);
    }

    @GetMapping("/activityType")
    public List<TrainingDto> byActivity(@RequestParam String activityType) {
        return service.getTrainingsByActivityType(activityType);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrainingDto create(@RequestBody TrainingRequest req) {
        return service.createTraining(req);
    }

    @PutMapping("/{trainingId}")
    public TrainingDto update(
            @PathVariable Long trainingId,
            @RequestBody TrainingRequest req
    ) {
        return service.updateTraining(trainingId, req);
    }
    @DeleteMapping("/{trainingId}")
    public void delete(@PathVariable Long trainingId) {
        service.deleteTraining(trainingId);
    }
}
