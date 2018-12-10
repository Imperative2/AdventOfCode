import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day10
{

	private static boolean[][] messageArray;
	private static int maxX = Integer.MIN_VALUE;
	private static int maxY = Integer.MIN_VALUE;
	private static int minX = Integer.MAX_VALUE;
	private static int minY = Integer.MAX_VALUE;
	private static List<Point> points = new ArrayList<>();
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args)
	{
		loadData();

		for (int i = 0; i < 10035; i++)
		{
			for (Point point : points)
			{
				point.moveWithoutArray();
			}
		}

		for (Point point : points)
		{
			int x = point.getX();
			int y = point.getY();

			if (x < minX)
				minX = x;
			if (x > maxX)
				maxX = x;
			if (y < minY)
				minY = y;
			if (y > maxY)
				maxY = y;
		}

		System.out.println("Min x" + minX + " Max x " + maxX + " Min y " + minY + " Max y" + maxY);

		// minX = minX-1000;
		// maxX = maxX+1000;
		// minY = minY-1000;
		// maxY = maxY+1000;

		messageArray = new boolean[Math.abs(minX) + Math.abs(maxX) + 100][Math.abs(minY) + Math.abs(maxY) + 100];
		System.out.println("Min x" + minX + " Max x " + maxX + " Min y " + minY + " Max y" + maxY);

		for (int i = 0; i < 100; i++)
		{

			for (Point point : points)
			{
				point.move(messageArray, minX, minY);
			}

			for (int k = Math.abs(minY); k < Math.abs(minY) + maxY; k++)
			{
				for (int j = Math.abs(minX); j < Math.abs(minX) + maxX; j++)
				{
					if (messageArray[j][k] == false)
						System.out.print(" ");
					else
						System.out.print("#");
				}
				System.out.println();
			}
			String s = sc.next();

			System.out.println("\n\n\n\n\n\n");
			// System.out.flush();
		}

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
				line = line.replaceAll("[a-z,A-Z]{1,}|=|<|>", "");
				line = line.replaceAll("\\s{1,}", " ");
				line = line.trim();
				System.out.println("'" + line + "'");
				String[] data = line.split(" ");
				int x = Integer.parseInt(data[0]);
				int y = Integer.parseInt(data[1]);

				Point point = new Point(x, y, Integer.parseInt(data[2]), Integer.parseInt(data[3]));
				points.add(point);

			}
			fileScanner.close();

		}
		catch (FileNotFoundException e)
		{
			System.out.println("Error in reading from a file");
			return;
		}
	}

	static class Point
	{
		private int x;
		private int y;
		private int velocityX;
		private int velocityY;

		public Point(int x, int y, int vX, int vY)
		{
			this.x = x;
			this.y = y;
			this.velocityX = vX;
			this.velocityY = vY;
		}

		public void moveWithoutArray()
		{
			x = x + velocityX;
			y = y + velocityY;
		}

		public void move(boolean[][] messageArray, int minX, int minY)
		{
			// System.out.println("X: "+(x+Math.abs(minX))+" Y: "+(y+Math.abs(minY)));
			messageArray[x + Math.abs(minX)][y + Math.abs(minY)] = false;
			x = x + velocityX;
			y = y + velocityY;

			messageArray[x + Math.abs(minX)][y + Math.abs(minY)] = true;
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
}
