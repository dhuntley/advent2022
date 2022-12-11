package advent.day11;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

public class MonkeyInTheMiddle {

    private static class WorryNumber {
        // Store only as the modulo relative to each of the divisors?

        //private static final long[] divisors = {23L, 19L, 13L, 17L};
        private static final long[] divisors = {3L, 13L, 19L, 17L, 5L, 7L, 11L, 2L};

        private Map<Long, Long> modMap = new HashMap<>();

        public WorryNumber(long value) {
            for (long divisor : divisors) {
                modMap.put(divisor, value % divisor);
            }
        }

        public WorryNumber square() {
            modMap.replaceAll((Long divisor, Long mod) -> (mod * mod) % divisor);
            return this;
        }

        public WorryNumber add(long value) {
            modMap.replaceAll((Long divisor, Long mod) -> (mod + value) % divisor);
            return this;
        }

        public WorryNumber multiply(long value) {
            modMap.replaceAll((Long divisor, Long mod) -> (mod * value) % divisor);
            return this;
        }

        public long mod(long value) {
            return modMap.get(value);
        }
    }

    private static class Monkey {
        private Deque<WorryNumber> items = new ArrayDeque<>(); 

        private long inspectionCount = 0;

        private long divisor;

        private int trueTarget;

        private int falseTarget;

        private UnaryOperator<WorryNumber> operation;

        public Monkey(List<Long> items, UnaryOperator<WorryNumber> operation, long divisor, int trueTarget, int falseTarget) {
            items.forEach(item -> {
                this.items.add(new WorryNumber(item));
            });
            this.operation = operation;
            this.divisor = divisor;
            this.trueTarget = trueTarget;
            this.falseTarget = falseTarget;
        }
        
        public void toss(List<Monkey> monkeys) {
            while (!items.isEmpty()) {
                inspectionCount++;
                WorryNumber item = operation.apply(items.poll());
                monkeys.get(item.mod(divisor) == 0L ? trueTarget : falseTarget).receive(item);
            }
        }

        public void receive(WorryNumber item) {
            items.add(item);
        }

        public long getInspectionCount() {
            return inspectionCount;
        }

    }

    public static void main(String[] args) {
       
        List<Monkey> monkeys = new ArrayList<>();

        // monkeys.add(new Monkey(List.of(79l, 98l), old -> old.multiply(19l), 23l, 2, 3));
        // monkeys.add(new Monkey(List.of(54l, 65l, 75l, 74l), old -> old.add(6l), 19l, 2, 0));
        // monkeys.add(new Monkey(List.of(79l, 60l, 97l), old -> old.square(), 13l, 1, 3));
        // monkeys.add(new Monkey(List.of(74l), old -> old.add(3l), 17l, 0, 1));

        monkeys.add(new Monkey(List.of(54l, 98l, 50l, 94l, 69l, 62l, 53l, 85l), old -> old.multiply(13L), 3L, 2, 1));
        monkeys.add(new Monkey(List.of(71l, 55l, 82l), old -> old.add(2L), 13L, 7, 2));
        monkeys.add(new Monkey(List.of(77l, 73l, 86l, 72l, 87l), old -> old.add(8L), 19L, 4, 7));
        monkeys.add(new Monkey(List.of(97l, 91l), old -> old.add(1L), 17L, 6, 5));
        monkeys.add(new Monkey(List.of(78l, 97l, 51l, 85l, 66l, 63l, 62l), old -> old.multiply(17L), 5L, 6, 3));
        monkeys.add(new Monkey(List.of(88l), old -> old.add(3L), 7L, 1, 0));
        monkeys.add(new Monkey(List.of(87l, 57l, 63l, 86l, 87l, 53l), old -> old.square(), 11L, 5, 0));
        monkeys.add(new Monkey(List.of(73l, 59l, 82l, 65l), old -> old.add(6L), 2L, 4, 3));


        for (int i=0; i<10000 ; i++) {
            monkeys.forEach(monkey -> {
                monkey.toss(monkeys);
            });
        }

        monkeys.sort((Monkey a, Monkey b) -> (int)(b.getInspectionCount() - a.getInspectionCount()));
        monkeys.forEach(monkey -> {
            System.out.println(monkey.getInspectionCount());
        });
        System.out.println(monkeys.get(0).getInspectionCount() * monkeys.get(1).getInspectionCount());
    }
}