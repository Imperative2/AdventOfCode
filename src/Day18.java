import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;

public class Day18
{
	private static char[][] fields;
	private static int maxX;
	private static int maxY;

	public static void main(String[] args)
	{
		loadData();

		display();

		for (long i = 0; i < 50000; i++)
		{
			char[][] fieldsNew = new char[fields.length][fields[0].length];
			for (int j = 0; j < fields.length; j++)
			{
				for (int k = 0; k < fields[0].length; k++)
				{
					if (fields[j][k] == '.')
					{
						if (getSurroundingTrees(j, k) > 2)
							fieldsNew[j][k] = '|';
						else
							fieldsNew[j][k] = '.';
					}
					else if (fields[j][k] == '|')
					{
						if (getSurroundingCamps(j, k) > 2)
							fieldsNew[j][k] = '#';
						else
							fieldsNew[j][k] = '|';
					}
					else if (fields[j][k] == '#')
					{
						if (getSurroundingCamps(j, k) > 0 && getSurroundingTrees(j, k) > 0)
							fieldsNew[j][k] = '#';
						else
							fieldsNew[j][k] = '.';
					}
				}
			}
			fields = fieldsNew;
			if(i%7000 == 0)
			{
				System.out.println("i: "+i);
				display();
			}


		}
		
		System.out.println("Fields are worth: "+ getProduct());

	}

	private static void display()
	{
		for (int i = 0; i < maxY; i++)
		{
			for (int j = 0; j < maxX; j++)
			{
				System.out.print(fields[i][j]);
			}
			System.out.println();
		}
		System.out.println("\n\n\n");
	}

	private static int getSurroundingTrees(int y, int x)
	{
		return getSurroundingFields('|', y, x);
	}

	private static int getSurroundingCamps(int y, int x)
	{
		return getSurroundingFields('#', y, x);
	}

	private static int getSurroundingOpen(int y, int x)
	{
		return getSurroundingFields('.', y, x);
	}

	private static int getSurroundingFields(char type, int y, int x)
	{
		int sum = 0;
		for (int i = -1; i < 2; i++)
		{
			if (y + i >= 0 && y + i < maxY)
			{
				for (int j = -1; j < 2; j++)
				{
					if (x + j >= 0 && x + j < maxX)
					{
						if (fields[y + i][x + j] == type && (i != 0 || j != 0))
							sum++;
					}
				}
			}
		}
		return sum;
	}
	
	private static int getProduct()
	{
		int sumTrees = 0;
		int sumCamps = 0;
		for (int i = 0; i < maxY; i++)
		{
			
			for (int j = 0; j < maxX; j++)
			{
				if(fields[i][j] == '|')
					sumTrees++;
				if(fields[i][j] == '#')
					sumCamps++;
			}
		}
		
		return sumTrees*sumCamps;
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

			List<String> linesList = new ArrayList<>();

			while (fileScanner.hasNext())
			{
				linesList.add(fileScanner.nextLine());
			}
			maxY = linesList.size();
			maxX = linesList.get(0).length();

			fields = new char[maxY][maxX];

			for (int i = 0; i < maxY; i++)
			{
				String line = linesList.get(i);
				for (int j = 0; j < maxX; j++)
				{
					fields[i][j] = line.charAt(j);
				}
			}

			fileScanner.close();

		}
		catch (FileNotFoundException e)
		{
			System.out.println("Error in reading from a file");
			return;
		}
	}

}
