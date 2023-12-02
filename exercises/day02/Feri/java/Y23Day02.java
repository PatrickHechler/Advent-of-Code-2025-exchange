import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * see: https://adventofcode.com/2023/day/02
 */
public class Y23Day02 {
 
	/*
	 * example input: 
	 *
	 * 
	 */

	private static final String INPUT_RX   = "^Game ([0-9]+): ([0-9a-z ,;]+)*+$";
	private static final String INPUT_RX_DICECOLOR   = "^([0-9]+) (red|green|blue)$";
	
	enum DCOLOR {red, green, blue}
	
	static record DiceCount(int cnt, DCOLOR color) {
		@Override public String toString() { return cnt + " " +color; }
	}
	
	public static class InputData {
		int gameId;
		List<DiceCount> shownDiceCounts = new ArrayList<>();
		@Override
		public String toString() {
			return "Game "+gameId+": "+shownDiceCounts;
		}
	}
	
	public static class InputProcessor implements Iterable<InputData>, Iterator<InputData> {
		private Scanner scanner;
		public InputProcessor(String inputFile) {
			try {
				scanner = new Scanner(new File(inputFile));
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		@Override public Iterator<InputData> iterator() { return this; }
		@Override public boolean hasNext() { return scanner.hasNext(); }
		@Override public InputData next() {
			String line = scanner.nextLine().trim();
			while (line.length() == 0) {
				line = scanner.nextLine();
			}
			InputData data = new InputData();
			if (line.matches(INPUT_RX)) {
				data.gameId = Integer.parseInt(line.replaceFirst(INPUT_RX, "$1"));
				for (String revealed:line.replaceFirst(INPUT_RX, "$2").split(";")) {
					for (String shownDiceColor:revealed.split(",")) {
						shownDiceColor = shownDiceColor.trim();
						if (!shownDiceColor.matches(INPUT_RX_DICECOLOR)) {
							throw new RuntimeException("invalid show dice color '"+shownDiceColor+"'");
						}
						int count = Integer.parseInt(shownDiceColor.replaceFirst(INPUT_RX_DICECOLOR, "$1"));
						DCOLOR color = DCOLOR.valueOf(shownDiceColor.replaceFirst(INPUT_RX_DICECOLOR, "$2"));
						data.shownDiceCounts.add(new DiceCount(count, color));
					}
				}
			}
			else {
				throw new RuntimeException("invalid line '"+line+"'");
			}
			return data;
		}
	}

	
	public static void mainPart1(String inputFile, int maxRed, int maxGreen, int maxBlue) {
		Map<DCOLOR, Integer> maxDicesPerColor = new HashMap<>();
		maxDicesPerColor.put(DCOLOR.red, maxRed);
		maxDicesPerColor.put(DCOLOR.green, maxGreen);
		maxDicesPerColor.put(DCOLOR.blue, maxBlue);
		System.out.println("MAX: " + maxDicesPerColor);
		int sumValidGameIDs = 0;
		for (InputData data:new InputProcessor(inputFile)) {
			System.out.println(data);
			boolean valid = true;
			for (DiceCount dc:data.shownDiceCounts) {
				if (dc.cnt > maxDicesPerColor.get(dc.color)) {
					System.out.println(data.gameId+" is invalid color "+dc.cnt+" x "+dc.color+" is more than max "+maxDicesPerColor.get(dc.color));
					valid = false;
					break;
				}
			}
			if (valid) {
				sumValidGameIDs += data.gameId;
			}
		}
		System.out.println("SUM VALID GAME IDs: "+sumValidGameIDs);
	}

	
	public static void mainPart2(String inputFile) {
		int sumSetValues = 0;
		for (InputData data:new InputProcessor(inputFile)) {
			System.out.println(data);
			Map<DCOLOR, Integer> maxDicesPerColor = new HashMap<>();
			maxDicesPerColor.put(DCOLOR.red, 0);
			maxDicesPerColor.put(DCOLOR.green, 0);
			maxDicesPerColor.put(DCOLOR.blue, 0);
			for (DiceCount dc:data.shownDiceCounts) {
				int max = Math.max(maxDicesPerColor.get(dc.color), dc.cnt);
				maxDicesPerColor.put(dc.color, max);
			}
			System.out.println(maxDicesPerColor);
			int setValue = maxDicesPerColor.get(DCOLOR.red)*maxDicesPerColor.get(DCOLOR.green)*maxDicesPerColor.get(DCOLOR.blue);
			sumSetValues += setValue;
		}
		System.out.println("SUM SET VALUES: "+sumSetValues);
	}

	

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("--- PART I ---");
//		mainPart1("exercises/day02/Feri/input-example.txt", 12, 13, 14);
		mainPart1("exercises/day02/Feri/input.txt", 12, 13, 14);
		System.out.println("---------------");
		System.out.println("--- PART II ---");
//		mainPart2("exercises/day02/Feri/input-example.txt");
		mainPart2("exercises/day02/Feri/input.txt");     
		System.out.println("---------------");    // 
	}
	
}
