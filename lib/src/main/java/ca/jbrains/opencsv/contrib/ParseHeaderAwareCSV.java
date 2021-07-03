/*
 * Copyright 2021 J. B. Rainsberger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.jbrains.opencsv.contrib;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ParseHeaderAwareCSV<RowType> {
    private final Function<Map<String, String>, RowType> parseRow;

    // REFACTOR Handle parse errors with a row parser that returns Either<ParseError, RowType>?
    public ParseHeaderAwareCSV(final Function<Map<String, String>, RowType> parseRow) {
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
