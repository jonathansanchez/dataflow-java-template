package com.verix.landing.infrastructure.streaming.transformations;

import com.verix.landing.domain.model.Landing;
import org.apache.beam.sdk.transforms.DoFn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvertStringToLanding extends DoFn<String, Landing> {
    private static final String REGEX_QUOTES = "\"([^\"]*)\"|([^,]+)|(?<=,)(?=,)";

    @ProcessElement
    public void processElement(@Element String line, OutputReceiver<Landing> out) {
        List<String> splitValue = Optional
                .ofNullable(line)
                .map(ConvertStringToLanding::parseLineInQuotes)
                .orElseThrow(RuntimeException::new);
        out.output(
                new Landing(
                        splitValue.get(0),
                        splitValue.get(1),
                        splitValue.get(2),
                        splitValue.get(3),
                        splitValue.get(4),
                        splitValue.get(5),
                        splitValue.get(6),
                        splitValue.get(7),
                        splitValue.get(8),
                        splitValue.get(11),
                        splitValue.get(12),
                        splitValue.get(21),
                        splitValue.get(22),
                        splitValue.get(23),
                        splitValue.get(24),
                        splitValue.get(25),
                        splitValue.get(26),
                        splitValue.get(27),
                        splitValue.get(28),
                        splitValue.get(29),
                        splitValue.get(30),
                        splitValue.get(31),
                        splitValue.get(32),
                        splitValue.get(33),
                        splitValue.get(34),
                        splitValue.get(38),
                        splitValue.get(39),
                        splitValue.get(40),
                        splitValue.get(41),
                        splitValue.get(47),
                        splitValue.get(48),
                        splitValue.get(49),
                        splitValue.get(50),
                        splitValue.get(51))
        );
    }

    private static List<String> parseLineInQuotes(String line) {
        List<String> col = new ArrayList<>();
        Matcher matcher = Pattern.compile(REGEX_QUOTES).matcher(line);
        while(matcher.find()){
            String value = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
            col.add(value);
        }
        return col;
    }
}