package com.verix.sam.infrastructure.streaming.transformation;

import com.verix.sam.domain.model.LifeDate;
import com.verix.sam.domain.model.Sam;
import org.apache.beam.sdk.transforms.DoFn;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StringToSamTransformation extends DoFn<String, Sam> {

    private static final String COMMA = ",";

    @ProcessElement
    public void processElement(@Element String line, OutputReceiver<Sam> out) {
        List<String> splitValue = Optional
                .ofNullable(line)
                .map(s -> Arrays.asList(s.trim().split(COMMA)))
                .orElseThrow(RuntimeException::new);

        out.output(new Sam(
                splitValue.get(0),
                splitValue.get(1),
                splitValue.get(2),
                splitValue.get(3),
                splitValue.get(4),
                splitValue.get(5),
                splitValue.get(6),
                LifeDate.create(splitValue.get(7)),
                LifeDate.create(splitValue.get(8)),
                LifeDate.create(splitValue.get(9)),
                LifeDate.create(splitValue.get(10)),
                LifeDate.create(splitValue.get(11)),
                LifeDate.create(splitValue.get(12)),
                splitValue.get(13))
        );
    }
}
