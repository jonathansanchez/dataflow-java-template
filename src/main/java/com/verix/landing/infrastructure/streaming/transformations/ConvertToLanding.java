package com.verix.landing.infrastructure.streaming.transformations;

import com.verix.landing.domain.model.Landing;
import org.apache.beam.sdk.transforms.DoFn;

public class ConvertToLanding extends DoFn<String, Landing> {

    private static final String COMMA = ",";

    @ProcessElement
    public void processElement(@Element String line, OutputReceiver<Landing> out) {
        String[] splitValue = line.toString().split(COMMA);
        out.output(new Landing(splitValue[0], splitValue[1], splitValue[2], splitValue[3], splitValue[4], splitValue[5]));
    }
}