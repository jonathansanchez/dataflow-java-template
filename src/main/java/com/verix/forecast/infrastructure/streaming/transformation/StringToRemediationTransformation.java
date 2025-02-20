package com.verix.forecast.infrastructure.streaming.transformation;

import com.verix.forecast.domain.model.Action;
import com.verix.forecast.domain.model.Country;
import com.verix.forecast.domain.model.DeliveryDate;
import com.verix.forecast.domain.model.Remediation;
import org.apache.beam.sdk.transforms.DoFn;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StringToRemediationTransformation extends DoFn<String, Remediation> {

    private static final String COMMA = ",";

    @ProcessElement
    public void processElement(@Element String line, OutputReceiver<Remediation> out) {
        List<String> splitValue = Optional
                .ofNullable(line)
                .map(s -> Arrays.asList(s.trim().split(COMMA)))
                .orElseThrow(RuntimeException::new);

        out.output(new Remediation(
                splitValue.get(0),
                Country.create(splitValue.get(1)),
                splitValue.get(2),
                splitValue.get(3),
                splitValue.get(4),
                Action.create(splitValue.get(5)),
                splitValue.get(6),
                DeliveryDate.create(splitValue.get(7)))
        );
    }
}
