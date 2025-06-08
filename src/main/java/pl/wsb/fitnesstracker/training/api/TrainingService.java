package pl.wsb.fitnesstracker.training.api;

import java.time.LocalDate;
import java.util.List;

public interface TrainingService {
    List<TrainingDto> getAllTrainings();
    List<TrainingDto> getTrainingsByUser(Long userId);
    List<TrainingDto> getFinishedTrainingsAfter(LocalDate date);
    List<TrainingDto> getTrainingsByActivityType(String activityType);
    TrainingDto createTraining(TrainingRequest request);
    TrainingDto updateTraining(Long id, TrainingRequest request);
    void deleteTraining(Long id);
}
