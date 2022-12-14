package advent.day13;

import java.util.ArrayList;
import java.util.List;
import advent.common.util.InputReader;

public class DistressSignal {

    private static int getClosingIndex(String data, int openingIndex) {
        int cursor = openingIndex;
        int nestingCount = 0;
        while (cursor < data.length()) {
            switch (data.charAt(cursor)) {
                case '[':
                    nestingCount++;
                    break;
                case ']':
                    nestingCount--;
                    break;
                default:
                    break;
            }
            cursor++;
            if (nestingCount == 0) {
                return cursor;
            }
        }
        return -1;
    }

    private static boolean isNumeric(String data) {
        try {
            Integer.parseInt(data);
        } catch(Exception e) {
            return false;
        }
        return true;
    }

    private static class PacketValue {
        
        boolean isInteger = false;

        Integer value = null;

        List<PacketValue> subPackets = new ArrayList<>();
        
        private PacketValue(String data) {
            if (data.isEmpty()) {
                return;
            }
            
            if (isNumeric(data)) {
                isInteger = true;
                value = Integer.parseInt(data);
                return;
            }

            assert(data.charAt(0) == '[');
            assert(data.charAt(data.length() - 1) == ']');

            String innerData = data.substring(1, data.length() - 1);
            int cursor = 0;
            while (cursor < innerData.length()) {
                if (innerData.charAt(cursor) == '[') {
                    int listCloseIndex = getClosingIndex(innerData, cursor);
                    subPackets.add(new PacketValue(innerData.substring(cursor, listCloseIndex)));
                    cursor = listCloseIndex + 1;
                } else if (innerData.charAt(cursor) == ',') {
                    cursor++;
                } else {
                    int numberEnd = cursor;
                    while (numberEnd < innerData.length() && Character.isDigit(innerData.charAt(numberEnd))) {
                        numberEnd++;
                    }

                    subPackets.add(new PacketValue(innerData.substring(cursor, numberEnd)));
                    cursor = numberEnd;
                }
            }
        }

        @Override
        public String toString() {
            if (isInteger) {
                return value.toString();
            }

            String output = "[";
            for (PacketValue packetValue : subPackets) {
                output += packetValue.toString() + ",";
            }
            if (output.charAt(output.length() - 1) == ',') {
                output = output.substring(0, output.length() - 1);
            }
            output += "]";
            return output;
        }

        private static Boolean hasCorrectOrder(PacketValue left, PacketValue right) {
            System.out.println("Compare " + left + " vs " + right);
            if (left.isInteger && right.isInteger) {
                if (left.value < right.value) {
                    return true;
                } else if (left.value > right.value) {
                    return false;
                } else {
                    return null;
                }
            } else if (!left.isInteger && !right.isInteger) {
                int index = 0;
                while (true) {
                    if (index >= left.subPackets.size() && index >= right.subPackets.size()) {
                        return null;
                    } else if (index >= left.subPackets.size()) {
                        return true;
                    } else if (index >= right.subPackets.size()) {
                        return false;
                    } else {
                        Boolean result = hasCorrectOrder(left.subPackets.get(index), right.subPackets.get(index));
                        if (result != null) {
                            return result;
                        }
                    }
                    index++;
                }
            } else if (left.isInteger) {
                return hasCorrectOrder(new PacketValue("[" + left.value + "]"), right);
            } else if (right.isInteger) {
                return hasCorrectOrder(left, new PacketValue("[" + right.value + "]"));
            }
            return null;
        }
    }

    private static class Packet {
        private PacketValue left;
        private PacketValue right;

        public Packet(String left, String right) {
            this.left = new PacketValue(left);
            this.right = new PacketValue(right);
        }

        public boolean hasCorrectOrder() {
            return PacketValue.hasCorrectOrder(left, right);
        }
    }

    public static void main(String[] args) {
        List<Packet> packets = new ArrayList<>();
        List<String> inputs = InputReader.readLinesFromInput("advent/day13/input.txt");
        
        int index = 0;
        while (index < inputs.size()) {
            packets.add(new Packet(inputs.get(index), inputs.get(index + 1)));
            index += 3;
        }

        List<Integer> goodIndices = new ArrayList<>();
        for (index = 0; index < packets.size(); index++) {
            if (packets.get(index).hasCorrectOrder()) {
                goodIndices.add(index + 1);
            }
            //System.out.println(index + ": " + packets.get(index).hasCorrectOrder());
        }

        System.out.println(goodIndices.stream().mapToInt(Integer::valueOf).sum());
        System.out.println(goodIndices.toString());
    }
}