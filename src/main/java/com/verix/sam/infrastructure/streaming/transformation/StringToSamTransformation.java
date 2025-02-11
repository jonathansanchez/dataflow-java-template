package com.verix.sam.infrastructure.streaming.transformation;

import com.verix.sam.domain.model.LifeDate;
import com.verix.sam.domain.model.Sam;
import org.apache.beam.sdk.transforms.DoFn;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringToSamTransformation extends DoFn<String, Sam> {

    private static final String COMMA = ",";
    public static final String REGEX_SPLIT = "([^\",]+|\"[^\"]*\")*,?";
    public static final String QUOTES = "\"";

    @ProcessElement
    public void processElement(@Element String line, OutputReceiver<Sam> out) {
        List<String> splitValue = Optional
                .ofNullable(line)
                .map(this::splitByCommaIgnoringQuotes)
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

    private List<String> splitByCommaIgnoringQuotes(String element) {
        Pattern pattern = Pattern.compile(REGEX_SPLIT);
        Matcher matcher = pattern.matcher(element);

        return Stream.generate(() -> {
                    if (matcher.find()) {
                        return matcher.group();
                    }
                    return null;
                })
                .takeWhile(Objects::nonNull)
                .filter(match -> !match.isEmpty())
                .map(match -> {
                    if (match.endsWith(COMMA)) {
                        match = match.substring(0, match.length() - 1);
                    }
                    if (match.startsWith(QUOTES) && match.endsWith(QUOTES)) {
                        match = match.substring(1, match.length() - 1);
                    }
                    return match;
                })
                .collect(Collectors.toList());
    }
}
