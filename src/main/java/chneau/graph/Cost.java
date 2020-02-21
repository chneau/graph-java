package chneau.graph;

import java.time.Duration;
import java.time.LocalDateTime;

public interface Cost {
    public Duration getCost(LocalDateTime c);
}
