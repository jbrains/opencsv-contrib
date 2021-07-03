package ca.jbrains.opencsv.contrib;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ParseHeaderAwareCSVIntoRecords<RowType> {
    private final Function<Map<String, String>, RowType> parseRow;

    // REFACTOR Handle parse errors with a row parser that returns Either<ParseError, RowType>?
    public ParseHeaderAwareCSVIntoRecords(final Function<Map<String, String>, RowType> parseRow) {
        this.parseRow = parseRow;
    }

    public List<RowType> parse(final Reader rawInputSource) throws IOException, CsvValidationException {
        final CSVReaderHeaderAware csvReader = new CSVReaderHeaderAware(rawInputSource);
        final List<RowType> rowsCollector = new ArrayList<>();
        while (true) {
            final Map<String, String> rowAsMap = csvReader.readMap();
            if (rowAsMap == null) break;
            rowsCollector.add(parseRow.apply(rowAsMap));
        }
        return rowsCollector;
    }
}
