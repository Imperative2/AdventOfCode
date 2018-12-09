import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class Day7
{
	private static HashSet<Character> symbolsSet = new HashSet<>();
	private static HashMap<Character, MyCharacter> symbolsMap = new HashMap<>();
	private static ArrayList<String> messesages = new ArrayList<>();
	private static Elf[] elfs = new Elf[5];

	public static void main(String[] args)
	{
		loadData();

		for (Character symbol : symbolsSet)
		{
			MyCharacter myChar = new MyCharacter(symbol);
			symbolsMap.put(symbol, myChar);
		}
		for (String line : messesages)
		{
			line = line.replaceAll("\\w{2,}|\\s|\\.", " ");
			line = line.replaceAll("\\s{1,}", " ");
			line = line.trim();
			// System.out.println(line);
			String[] letters = line.split(" ");
			symbolsMap.get(letters[1].charAt(0)).addPredecesor(letters[0].charAt(0));
		}

		System.out.println(symbolsSet.toString());

		orderSteps();

		makeElfWork();

	}

	private static void makeElfWork()
	{
		for (int i = 0; i < elfs.length; i++)
		{
			elfs[i] = new Elf();
		}

		HashSet<Character> copySymbolsSet = (HashSet<Character>) symbolsSet.clone();
		HashMap<Character, MyCharacter> copySymbolsMap = (HashMap<Character, MyCharacter>) symbolsMap.clone();

		long time = 0;
		ArrayList<Character> charsWorkedAt = new ArrayList<>();

		while (copySymbolsSet.size() > 0)
		{
			ArrayList<Character> possibleNext = new ArrayList<>();
			for (Character symbol : copySymbolsSet)
			{
				MyCharacter myChar = copySymbolsMap.get(symbol);
				if (myChar.isViable(copySymbolsSet))
				{
					possibleNext.add(symbol);
				}
			}

			if (possibleNext.size() <= 0)
			{
				System.out.println("ERROR");
			}

			Character[] sortedChars = possibleNext.stream().sorted().toArray(Character[]::new);

			for (int i = 0; i < elfs.length; i++)
			{

				Elf elf = elfs[i];
				if (elf.isWorking() == false)
				{
					Iterator<Character> iterator = new ArrayList<Character>(Arrays.asList(sortedChars)).iterator();
					while (iterator.hasNext())
					{
						Character charToWorkOn = iterator.next();
						if (charsWorkedAt.contains(charToWorkOn) == false)
						{
							elf.newWork(copySymbolsMap.get(charToWorkOn));
							charsWorkedAt.add(charToWorkOn);
							break;
						}
					}

				}
			}

			System.out.print("\n" + time + ": ");
			for (Elf elf : elfs)
			{
				if (elf.isWorking())
				{
					System.out.print(elf.getWork().getSymbol());
				} else
					System.out.print(".");
			}

			for (Elf elf : elfs)
			{
				elf.work();
			}
			
			
			time++;

			for (Elf elf : elfs)
			{
				if (elf.isFinished() == true)
				{
					MyCharacter work = elf.getWork();
					// elf.clearWork();
					copySymbolsSet.remove(work.getSymbol());
					copySymbolsMap.remove(work.getSymbol());
				}
			}

		}

		System.out.println("\nWork time for 5 elfs: " + time);
	}

	private static void orderSteps()
	{
		HashSet<Character> copySymbolsSet = (HashSet<Character>) symbolsSet.clone();
		HashMap<Character, MyCharacter> copySymbolsMap = (HashMap<Character, MyCharacter>) symbolsMap.clone();

		String result = "";
		while (copySymbolsSet.size() > 0)
		{
			ArrayList<Character> possibleNext = new ArrayList<>();
			for (Character symbol : copySymbolsSet)
			{
				MyCharacter myChar = copySymbolsMap.get(symbol);
				if (myChar.isViable(copySymbolsSet))
				{
					possibleNext.add(symbol);
				}
			}

			if (possibleNext.size() <= 0)
			{
				System.out.println("ERROR");
			}

			Character[] sortedChars = possibleNext.stream().sorted().toArray(Character[]::new);

			result += sortedChars[0];
			copySymbolsSet.remove(sortedChars[0]);
			copySymbolsMap.remove(sortedChars[0]);
		}

		System.out.println(result);
	}

	private static void loadData()
	{
		Scanner sc = new Scanner(System.in);
		System.out.println(System.getProperty("user.dir"));
		System.out.print("podaj nazwê pliku: ");
		String fileName;
		System.out.println();
		fileName = sc.next();
		fileName = System.getProperty("user.dir") + "\\" + fileName + ".txt";
		File file = new File(fileName);
		System.out.println(file.getPath());
		System.out.println("file exist: " + file.exists());

		Scanner fileScanner;
		try
		{
			fileScanner = new Scanner(file);
			while (fileScanner.hasNext())
			{
				String line = fileScanner.nextLine();
				messesages.add(line);
				line = line.replaceAll("\\w{2,}|\\s|\\.", " ");
				line = line.replaceAll("\\s{1,}", " ");
				line = line.trim();
				// System.out.println(line);
				String[] letters = line.split(" ");
				symbolsSet.add(letters[0].charAt(0));
				symbolsSet.add(letters[1].charAt(0));
			}
			fileScanner.close();

		} catch (FileNotFoundException e)
		{
			System.out.println("Error in reading from a file");
			return;
		}
	}

	static class Elf
	{
		private boolean isWorkingFlag = false;
		private MyCharacter work;
		private int workTime;
		private boolean wasWorkingFlag = false;
		private boolean isFinishedFlag = false;

		public Elf()
		{

		}

		public void newWork(MyCharacter myChar)
		{
			isWorkingFlag = true;
			wasWorkingFlag = true;
			isFinishedFlag = false;
			workTime = myChar.getSymbolWeight();
			work = myChar;
		}

		public void work()
		{
			workTime--;
			if (workTime == 0 && wasWorkingFlag == true)
			{
				wasWorkingFlag = false;
				isFinishedFlag = true;
				isWorkingFlag = false;
			}
		}

		public boolean isWorking()
		{
			return isWorkingFlag;
		}

		public boolean isFinished()
		{
			return isFinishedFlag;
		}

		public MyCharacter getWork()
		{
			return work;
		}

	}

	static class MyCharacter
	{
		private Character symbol;
		private ArrayList<Character> predecesors = new ArrayList<>();
		private int symbolWeight;

		public MyCharacter(char symbol)
		{
			this.symbol = symbol;
			symbolWeight = symbol - 4;
			System.out.println(symbol + " Symbol weight: " + symbolWeight);
		}

		public void addPredecesor(Character otherSymbol)
		{
			predecesors.add(otherSymbol);
		}

		public boolean isViable(HashSet<Character> symbols)
		{
			for (Character predecesor : predecesors)
			{
				if (symbols.contains(predecesor))
					return false;
			}
			return true;
		}

		public int getSymbolWeight()
		{
			return symbolWeight;
		}

		public Character getSymbol()
		{
			return symbol;
		}

	}
}
