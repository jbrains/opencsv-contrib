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
        final ParseHeaderAwareCSVIntoRecords<IHaveAStringRecord> csvParser =
                new ParseHeaderAwareCSVIntoRecords<>(
                        rowAsMap -> new IHaveAStringRecord(rowAsMap.get("text")));

        final List<IHaveAStringRecord> parsedRows =
                List.ofAll(
                        csvParser.parse(
                                new StringReader(unlines(List.of("text", "jbrains")))));

        Assertions.assertEquals(
                List.of(new IHaveAStringRecord("jbrains")),
                parsedRows);
    }

    private static String unlines(final Traversable<String> lines) {
        return lines.collect(Collectors.joining(System.lineSeparator()));
    }
}
