import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day17
{

	private static List<Line> linesList = new ArrayList<>();
	private static List<Source> sourcesList = new ArrayList<>();
	private static char[][] undergroundMap;

	private static int endDeepth = 0;
	private static int x;
	private static int y;

	public static void main(String[] args)
	{
		loadData();
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = 0;
		int maxY = Integer.MIN_VALUE;
		for (Line line : linesList)
		{
			if (line.getStartX() < minX)
				minX = line.getStartX();
			if (line.getEndX() > maxX)
				maxX = line.getEndX();
			if (line.getStartY() < minY)
				minY = line.getStartY();
			if (line.getEndY() > maxY)
				maxY = line.getEndY();
		}
		endDeepth = maxY;

		minX = minX - 2;
		maxX = maxX + 1;
		maxY = maxY + 1;

		undergroundMap = new char[maxY - minY + 1][maxX - minX + 1];

		for (int i = 0; i < undergroundMap.length; i++)
		{
			for (int j = 0; j < undergroundMap[0].length; j++)
			{
				undergroundMap[i][j] = '.';
			}
		}

		for (Line line : linesList)
		{
			line.putLine(minX, maxX, minY, maxY, undergroundMap);
		}

		undergroundMap[-minY][500 - minX] = '+';
		y = -minY + 1;
		x = 500 - minX;
		sourcesList.add(new Source(x, y));
		undergroundMap[y][x] = '|';

		displayMap();

		endDeepth = maxY - minY;

		List<Source> sourcesToDelete = new ArrayList<>();

		// for(int i = 0; i < 300; i++)
		while (checkIfReachedBottom() == false)
		{

			for (int j = sourcesToDelete.size() - 1; j >= 0; j--)
			{
				sourcesList.remove(sourcesToDelete.get(j));
				sourcesToDelete.remove(j);
			}

			int sourceLength = sourcesList.size();
			for (int j = 0; j < sourceLength; j++)
			{
				Source source = sourcesList.get(j);

				if (undergroundMap[source.getY() + 1][source.getX()] == '.')
				{
					source.setY(source.getY() + 1);
					undergroundMap[source.getY()][source.getX()] = '|';
				}
				else if (undergroundMap[source.getY() + 1][source.getX()] == '#'
						|| undergroundMap[source.getY() + 1][source.getX()] == '~')
				{

					Source tempL = new Source(source.getX(), source.getY());
					sourcesList.add(tempL);

					boolean flagDeadLeft = false;
					boolean flagDeadRight = false;

					while (true)
					{
						if (undergroundMap[tempL.getY()][tempL.getX()] == '#')
						{
							sourcesToDelete.add(tempL);
							flagDeadLeft = true;
							break;
						}

						if (undergroundMap[tempL.getY() + 1][tempL.getX()] == '.')
						{
							undergroundMap[tempL.getY()][tempL.getX()] = '|';

							int tempX = tempL.getX() + 1;
							int tempY = tempL.getY();
							while (undergroundMap[tempY][tempX] == '~')
							{
								undergroundMap[tempY][tempX] = '|';
								tempX++;
							}

							break;
						}
						undergroundMap[tempL.getY()][tempL.getX()] = '~';
						tempL.setX(tempL.getX() - 1);
					}

					Source tempR = new Source(source.getX(), source.getY());
					sourcesList.add(tempR);

					while (true)
					{
						if (undergroundMap[tempR.getY()][tempR.getX()] == '#')
						{
							sourcesToDelete.add(tempR);
							flagDeadRight = true;
							if (flagDeadLeft == false)
							{
								int tempX = tempR.getX() - 1;
								int tempY = tempR.getY();
								while (undergroundMap[tempY][tempX] == '~')
								{
									undergroundMap[tempY][tempX] = '|';
									tempX--;
								}
							}
							break;
						}

						if (undergroundMap[tempR.getY() + 1][tempR.getX()] == '.')
						{
							undergroundMap[tempR.getY()][tempR.getX()] = '|';
							int tempX = tempR.getX() - 1;
							int tempY = tempR.getY();
							while (undergroundMap[tempY][tempX] == '~')
							{
								undergroundMap[tempY][tempX] = '|';
								tempX--;
							}
							break;
						}
						undergroundMap[tempR.getY()][tempR.getX()] = '~';
						tempR.setX(tempR.getX() + 1);
					}

					if (flagDeadRight == true && flagDeadLeft == true)
					{
						source.setY(source.getY() - 1);
					}
					else
						sourcesToDelete.add(source);

				}
				else if (undergroundMap[source.getY() + 1][source.getX()] == '|')
				{
					sourcesToDelete.add(source);
				}
				else
				{
					System.out.println("doing nothing");
					sourcesToDelete.add(source);
				}

			}

			// displayMap();
			System.out.println("calculating");
		}
		displayMap();
		System.out.println("Sum :"+ countWater());
		System.out.println("Sum of standing water: "+ countStandingWater());
	}

	public static boolean checkIfReachedBottom()
	{
		for (Source source : sourcesList)
		{
			if (source.getY() == endDeepth)
				return true;
		}
		return false;
	}
	
	public static int countWater()
	{
		int sum=0;
		for (int i = 0; i < undergroundMap.length-1; i++)
		{
			for (int j = 0; j < undergroundMap[0].length; j++)
			{
				if(undergroundMap[i][j] == '~' || undergroundMap[i][j] == '|')
					sum++;
			}
		}
		return sum;
	}
	
	public static int countStandingWater()
	{
		int sum=0;
		for (int i = 0; i < undergroundMap.length-1; i++)
		{
			for (int j = 0; j < undergroundMap[0].length; j++)
			{
				if(undergroundMap[i][j] == '~')
					sum++;
			}
		}
		return sum;
	}

	public static void displayMap()
	{
		for (int i = 0; i < undergroundMap.length; i++)
		{
			for (int j = 0; j < undergroundMap[0].length; j++)
			{
				System.out.print(undergroundMap[i][j]);
			}
			System.out.println();
		}
		System.out.println("\n\n\n\n");
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
				String row = fileScanner.nextLine();
				if (row.charAt(0) == 'x')
				{
					row = row.replaceAll("y=|, |x=|\\.\\.", " ");
					row = row.trim();
					row = row.replaceAll("\\s+", " ");
					String[] data = row.split(" ");
					Line line = new Line(Integer.parseInt(data[0]), Integer.parseInt(data[0]),
							Integer.parseInt(data[1]), Integer.parseInt(data[2]));
					linesList.add(line);
				}
				else if (row.charAt(0) == 'y')
				{
					row = row.replaceAll("y=|, |x=|\\.\\.", " ");
					row = row.trim();
					row = row.replaceAll("\\s+", " ");
					String[] data = row.split(" ");
					Line line = new Line(Integer.parseInt(data[1]), Integer.parseInt(data[2]),
							Integer.parseInt(data[0]), Integer.parseInt(data[0]));
					linesList.add(line);
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

	static class Source
	{
		private int x;
		private int y;

		public Source(int x, int y)
		{
			this.x = x;
			this.y = y;
		}

		public void setX(int x)
		{
			this.x = x;
		}

		public void setY(int y)
		{
			this.y = y;
		}

		public int getX()
		{
			return x;
		}

		public int getY()
		{
			return y;
		}
	}

	static class Line
	{
		private int startX;
		private int endX;
		private int startY;
		private int endY;

		public Line(int startX, int endX, int startY, int endY)
		{
			this.startX = startX;
			this.endX = endX;
			this.startY = startY;
			this.endY = endY;
		}

		public void putLine(int minX, int maxX, int minY, int maxY, char[][] undergroundMap)
		{
			for (int i = startY; i <= endY; i++)
			{
				for (int j = startX; j <= endX; j++)
				{
					int y = i - minY;
					int x = j - minX;
					undergroundMap[y][x] = '#';
				}
			}
		}

		public int getStartX()
		{
			return startX;
		}

		public int getEndX()
		{
			return endX;
		}

		public int getStartY()
		{
			return startY;
		}

		public int getEndY()
		{
			return endY;
		}

	}

}
