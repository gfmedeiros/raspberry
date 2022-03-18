package org.example.raspberry.jobs;


import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.List;

public class CsvItemToNomineeProcessor implements ItemProcessor<CsvItem, List<Nominee>> {

    @Override
    public List<Nominee> process(CsvItem item) {
        List<Nominee> nominees = new ArrayList<>();
        boolean isWinner = isWinner(item);
        for (String producer: splitProducers(item.getProducers())) {
            Nominee nominee = new Nominee();
            nominee.setYear(item.getYear());
            nominee.setTitle(item.getTitle());
            nominee.setStudios(item.getStudios());
            nominee.setProducer(producer);
            nominee.setWinner(isWinner);
            nominees.add(nominee);
        }
        return nominees;
    }

    private String[] splitProducers(String producers) {
        return producers.split("\\s+and\\s+|\\s*,(\\s+and)?\\s+");
    }

    private static boolean isWinner(CsvItem item) {
        return item.getWinner() != null & !item.getWinner().isEmpty();
    }
}
