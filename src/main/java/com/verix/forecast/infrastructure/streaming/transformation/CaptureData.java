package com.verix.forecast.infrastructure.streaming.transformation;

import org.apache.beam.sdk.transforms.DoFn;

import java.util.List;

public class CaptureData<T> extends DoFn<T, T> {
    public static List data;

    public CaptureData(List<T> pdata) {
        data = pdata;
    }

    @ProcessElement
    public void processElement(@Element T in, OutputReceiver<T> out) {
        data.add(in);
        out.output(in);
    }
}
