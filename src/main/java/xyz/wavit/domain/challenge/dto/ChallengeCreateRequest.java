package xyz.wavit.domain.challenge.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import java.util.List;

public record ChallengeCreateRequest(@NotEmpty List<@Positive Long> nominatedUserIds) {}
