package se.edstrompartners.cards;

import java.util.*;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Util {

    public static IntStream iota(int i){
        return IntStream.range(0, i);
    }
}
