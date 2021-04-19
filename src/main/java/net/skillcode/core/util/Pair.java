package net.skillcode.core.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Pair<K, V> {

    private final K key;
    private final V value;

}
