package puddle;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Andy
 *
 */
public class PuddleVolume {

	private List<Entry> convertRaw(List<Integer> raw) {

		List<Entry> working = new LinkedList<Entry>();

		for (int i = 0; i < raw.size(); i++) {
			working.add(new Entry(raw.get(i), i));
		}

		return working;
	}


	/**
	 * @param puddle		List of walls that form a puddle
	 * @param waterLevel	water level of the puddle
	 * @return	volume of the puddle
	 */
	private int puddleVolum(List<Integer> puddle,int waterLevel) {

		int volume = 0;
		
		List<Integer> concave = puddle.subList(1, puddle.size() - 1);
		for (Integer height: concave) {
			volume += (waterLevel - height);
		}
		
		System.out.println("Input for this round: " + puddle.toString());
		System.out.println("Calculated volume for this round: " + volume);

		return volume;
	}

	public int calculateVolume(List<Integer> input) {

		int totalVolume = 0;
		while (input.size() > 2) {

			// sort the walls according to its height
			List<Entry> sortedWalls = convertRaw(input);
			Collections.sort(sortedWalls); // not so efficient here

			int pos1 = sortedWalls.get(0).position;
			int pos2 = sortedWalls.get(1).position;

			int start = Math.min(pos1, pos2);
			int end = Math.max(pos1, pos2);
			int waterLevel = sortedWalls.get(1).height;

			// we've found the puddle, now calculate its volume
			// subList does not include element at its end index
			List<Integer> puddle = input.subList(start, end + 1);
			int volume = puddleVolum(puddle, waterLevel);
			totalVolume += volume;

			// remove the walls that have processed
			// subList is just a view into the original list
			// we need to leave one of the walls
			input.subList(start, end).clear();

		}

		return totalVolume;
	}

	public static void main(String[] args) {

		int[] input = { 4, 1, 1, 6, 2, 5, 1, 4 };

		PuddleVolume pv = new PuddleVolume();

		System.out.println("Calculate puddle volume for input: "
				+ Arrays.toString((input)));

		List<Integer> flexibleWall = new LinkedList<>();
		for (int i = 0; i < input.length; i++)
			flexibleWall.add(input[i]);
		int volume = pv.calculateVolume(flexibleWall);

		System.out.println("The total volume is " + volume);

	}

	private class Entry implements Comparable<Entry> {
		public int height;
		public int position;

		public Entry(int value, int position) {
			this.height = value;
			this.position = position;
		}

		@Override
		public int compareTo(Entry o) {

			return -Integer.valueOf(height)
					.compareTo(Integer.valueOf(o.height));
		}

		@Override
		public int hashCode() {
			return Integer.valueOf(height).hashCode()
					^ Integer.valueOf(position).hashCode();
		}

		@Override
		public boolean equals(Object o) {
			if ((o instanceof Entry) == false)
				return false;

			Entry other = (Entry) o;

			return height == other.height && position == other.position;
		}

	}

}
