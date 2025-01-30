package com.verix.apm.infrastructure.streaming.transformation;

import com.verix.apm.domain.model.BooleanCleaner;
import com.verix.apm.domain.model.LifeDate;
import com.verix.apm.domain.model.Apm;
import org.apache.beam.sdk.transforms.DoFn;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/*
- Toma un String, lo divide por commas y lo convierte a objeto Apm
 */
public class StringToApmTransformation extends DoFn<String, Apm> {

    private static final String COMMA = ",";

    @ProcessElement
    public void processElement(@Element String line, OutputReceiver<Apm> out) {
        List<String> splitValue = Optional
                .ofNullable(line)
                .map(s -> Arrays.asList(s.trim().split(COMMA)))
                .orElseThrow(RuntimeException::new);

        out.output(new Apm(
                splitValue.get(0),
                splitValue.get(1),
                BooleanCleaner.parseBoolean(splitValue.get(2)),  // isCompliant
                BooleanCleaner.parseBoolean(splitValue.get(3)),  // cia
                splitValue.get(4),
                LifeDate.create(splitValue.get(5)),
                LifeDate.create(splitValue.get(6)),
                splitValue.get(7),
                BooleanCleaner.parseBoolean(splitValue.get(8)),  // applicationTested
                splitValue.get(9),
                splitValue.get(10),
                splitValue.get(11),
                splitValue.get(12),
                splitValue.get(13),
                splitValue.get(14))
        );
    }
}