package org.example.raspberry.services;

import java.util.List;

public interface ProducerService {
    List<ConsecutiveWins> findShortestConsecutiveWins();
    List<ConsecutiveWins> findLongestConsecutiveWins();
}
