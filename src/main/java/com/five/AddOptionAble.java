package com.five;

import org.apache.commons.cli.Option;

import java.util.function.Consumer;

public interface AddOptionAble {
    void addOption(Option option, Consumer<Object[]> callback);
}
