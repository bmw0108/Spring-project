package pl.wsb.fitnesstracker.training.api;

import java.time.LocalDateTime;
import pl.wsb.fitnesstracker.training.internal.ActivityType;

/**
 * DTO for creating/updating trainings.
 */
public class TrainingRequest {

    private Long userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ActivityType activityType;
    private double distance;
    private double averageSpeed;

    public TrainingRequest() {}

    // ─── Getters & Setters ────────────────────────────────────────────────────

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public ActivityType getActivityType() { return activityType; }
    public void setActivityType(ActivityType activityType) { this.activityType = activityType; }

    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }

    public double getAverageSpeed() { return averageSpeed; }
    public void setAverageSpeed(double averageSpeed) { this.averageSpeed = averageSpeed; }
}
