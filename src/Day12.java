import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day12
{

	private static List<Pot> flowerPots = new ArrayList<>();
	private static List<Rule> rulesList = new ArrayList<>();

	public static void main(String[] args)
	{
		loadData();

		System.out.print("Gen 0 : ");
		for (Pot pot : flowerPots)
		{
			System.out.print(pot.getSymbol() + " ");
		}
		System.out.println();

		for (int i = 0; i < 5000; i++)
		{

			grow();
		}
		int sum = 0;
		for (Pot pot : flowerPots)
		{
			if (pot.getSymbol() == '#')
				sum += pot.getIndex();
		}
		System.out.println("Sum : " + sum);
	}

	private static void grow()
	{
		int firstPlant = 5;
		for (int i = 0; i < 5; i++)
		{
			if (flowerPots.get(i).getSymbol() == '#')
			{
				firstPlant = i;
				break;
			}
		}
		if (firstPlant < 5)
		{
			for (int i = firstPlant; i < 5; i++)
			{
				Pot pot = new Pot(flowerPots.get(0).getIndex() - 1, '.');
				flowerPots.add(0, pot);
			}
		}

		int lastPlant = flowerPots.size();
		for (int i = flowerPots.size() - 5; i < flowerPots.size(); i++)
		{
			if (flowerPots.get(i).getSymbol() == '#')
			{
				lastPlant = i;
			}
		}
		if (lastPlant < flowerPots.size())
		{
			for (int i = (flowerPots.size() - 1) - lastPlant; i < 5; i++)
			{
				Pot pot = new Pot(flowerPots.get(flowerPots.size() - 1).getIndex() + 1, '.');
				flowerPots.add(pot);
			}
		}

		// System.out.print("before: ");
		// for (Pot pot : flowerPots)
		// {
		// System.out.print(pot.getSymbol() + " ");
		// }
		// System.out.println();

		List<Pot> result = new ArrayList<>();
		for (int i = 0; i < flowerPots.size(); i++)
		{
			if (i <= 1 || i >= flowerPots.size() - 2)
			{
				result.add(flowerPots.get(i));
			}
			else
			{
				String plants = "";
				for (int j = i - 2; j < i + 3; j++)
				{
					plants += flowerPots.get(j).getSymbol();
				}

				boolean flagMatched = false;

				for (Rule rule : rulesList)
				{
					if (rule.isMatching(plants))
					{
						Pot pot = new Pot(flowerPots.get(i).getIndex(), rule.getResult());
						result.add(pot);
						flagMatched = true;
						break;
					}
				}
				if (flagMatched == false)
				{
					Pot pot = new Pot(flowerPots.get(i).getIndex(), '.');
					result.add(pot);
				}
			}
		}

		// System.out.print("Plants : ");
		// for (Pot pot : result)
		// {
		// System.out.print(pot.getSymbol() + " ");
		// }
		// System.out.println();
		flowerPots = result;

		// int sum = 0;
		// for (Pot pot : flowerPots)
		// {
		// if (pot.getSymbol() == '#')
		// sum += pot.getIndex();
		// }
		// System.out.println("Sum : " + sum);
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
			String firstLine = fileScanner.nextLine();
			firstLine = firstLine.replaceAll("[A-Z,a-z, ,:]+", "");
			for (int i = 0; i < firstLine.length(); i++)
			{
				flowerPots.add(new Pot(i, firstLine.charAt(i)));
			}
			while (fileScanner.hasNext())
			{
				String line = fileScanner.nextLine();
				line = line.replaceAll(" => ", " ");

				System.out.println("'" + line + "'");
				String[] data = line.split(" ");

				rulesList.add(new Rule(data[0], data[1].charAt(0)));

			}
			fileScanner.close();

		}
		catch (FileNotFoundException e)
		{
			System.out.println("Error in reading from a file");
			return;
		}
	}

	static class Rule
	{
		private String rule;
		private char result;

		public Rule(String rule, char result)
		{
			this.rule = rule;
			this.result = result;
		}

		public boolean isMatching(String toMatch)
		{
			if (toMatch.equals(rule))
			{
				return true;
			}
			else
				return false;
		}

		public char getResult()
		{
			return result;
		}
	}

	static class Pot
	{
		private int index;
		private char symbol;

		public Pot(int index, char symbol)
		{
			this.index = index;
			this.symbol = symbol;
		}

		public int getIndex()
		{
			return index;
		}

		public char getSymbol()
		{
			return symbol;
		}
	}
}
