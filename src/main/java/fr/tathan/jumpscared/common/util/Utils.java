package fr.tathan.jumpscared.common.util;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;

import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

    public static <K, V> Codec<Map<K, V>> pairListMap(Codec<K> keyCodec, Codec<V> valueCodec) {
        return Codec.pair(keyCodec, valueCodec)
                .listOf()
                .xmap(
                        list -> list.stream().collect(Collectors.toMap(Pair::getFirst, Pair::getSecond)),
                        map -> map.entrySet().stream().map(entry -> Pair.of(entry.getKey(), entry.getValue())).toList()
                );
    }


}
