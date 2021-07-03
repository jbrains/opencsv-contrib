package ca.jbrains.learn.opencsv;

import ca.jbrains.opencsv.contrib.ParseHeaderAwareCSVIntoRecords;
import io.vavr.collection.List;
import io.vavr.collection.Traversable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.Map;
import java.util.stream.Collectors;

public class ParseCsvWithOpenCsvToRecords {
    public static record IHaveAStringRecord(String text) {
    }

    @Test
    void singleRowAndSingleColumn() throws Exception {
        final ParseHeaderAwareCSVIntoRecords<IHaveAStringRecord> csvParser = new ParseHeaderAwareCSVIntoRecords<>(ParseCsvWithOpenCsvToRecords::parseRow);
        final java.util.List<IHaveAStringRecord> rowsCollector = csvParser.parse(new StringReader(unlines(List.of("text", "jbrains"))));

        Assertions.assertEquals(
                List.of(new IHaveAStringRecord("jbrains")),
                List.ofAll(rowsCollector));
    }

    private static String unlines(final Traversable<String> lines) {
        return lines.collect(Collectors.joining(System.lineSeparator()));
    }

    private static IHaveAStringRecord parseRow(final Map<String, String> rowAsMap) {
        return new IHaveAStringRecord(rowAsMap.get("text"));
    }
}
