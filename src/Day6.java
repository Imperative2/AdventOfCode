import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day6 
{
	
	private static ArrayList<Point> pointsList = new ArrayList<>();
	private static int maxX = Integer.MIN_VALUE;
	private static int maxY = Integer.MIN_VALUE;
	private static Point[][] points;
	private static char[][] symbolMap;
	
	public static void main(String[] args)
	{
		loadData();
		
		points = new Point[maxX][maxY];
		symbolMap = new char[maxX][maxY];
		
		int goodAreaSize = 0;
		
		
		for(int i=0; i<maxX; i++)
		{
			for(int j = 0; j<maxY; j++)
			{
				long sum = 0;
				for(Point point : pointsList)
				{
					sum += Math.abs(point.getX() - i);
					sum += Math.abs(point.getY() - j);
				}
				if(sum < 10000)
				{
					symbolMap[i][j] = '#';
					goodAreaSize++;
				}
				else
				{
					symbolMap[i][j] = '.';
				}
				System.out.print(symbolMap[i][j]);
			}
			System.out.println();
		}
		
		for(int i= 0; i<maxX; i++)
		{
			for(int j = 0; j<maxY; j++)
			{
				points[i][j] = closestPoint(i, j);
			}
		}
		
		disqualify();
		
		long largestAreaSize = Long.MIN_VALUE;
		
		for(Point point: pointsList)
		{
			for(int i=0; i<maxX; i++)
			{
				for(int j=0; j<maxY; j++)
				{
					if(point.equals(points[i][j]))
					{
						point.incArea();
					}
				}
			}
			
			System.out.println("Area size: "+point.getArea());
			if(point.getArea()> largestAreaSize)
			{
				largestAreaSize = point.getArea();
			}
		}
		
		System.out.println("Largest area size: "+largestAreaSize);
		System.out.println("Area not further from all points by 10000 : "+goodAreaSize);
		
	}
	
	public static Point closestPoint(int x, int y)
	{
		Point closestPoint = null;
		int minDistance = Integer.MAX_VALUE;
		int secondMinDistance = Integer.MAX_VALUE;
		for(Point point : pointsList)
		{
			int distance = 0;
			distance += Math.abs(point.getX() - x);
			distance += Math.abs(point.getY() - y);
			if(distance < minDistance)
			{
				secondMinDistance = minDistance;
				minDistance = distance;
				closestPoint = point;
			}
		}
		
		if(minDistance != secondMinDistance)
		{
			return closestPoint;
		}
		else
			return null;
	}
	
	public static void disqualify()
	{
		for(int i=0; i<maxX; i++)
		{
			for(int j=0; j<maxY; j++)
			{
				if(i == 0 || i == maxX-1 || j == 0 || j == maxY-1)
				{
					pointsList.remove(points[i][j]);
				}
			}
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
		fileName= System.getProperty("user.dir") +"\\"+fileName + ".txt";
		File file = new File(fileName);
		System.out.println(file.getPath());
		System.out.println("file exist: "+file.exists());
		
		
		Scanner fileScanner;
		try 
		{
			fileScanner = new Scanner(file);
			while(fileScanner.hasNext())
			{
				String input = fileScanner.nextLine();
				input = input.replaceAll(",", "");
				input = input.trim();
//				System.out.println("input :"+input+":");
				String[] cordinates = input.split(" ");
				int x = Integer.parseInt(cordinates[0]);
				int y = Integer.parseInt(cordinates[1]);
				
				pointsList.add(new Point(x,y));
				
				if(x > maxX)
					maxX = x;
				if(y > maxY ) 
					maxY = y;
				
				
			}
			fileScanner.close();
				
		} catch (FileNotFoundException e) 
		{
			System.out.println("Error in reading from a file");
			return;
		}

	}
	
	static class Point
	{
		private int x;
		private int y;
		private static int idSeed = 0;
		private int id;
		private long area = 0;
		
		public Point(int x, int y)
		{
			this.x = x;
			this.y = y;
			this.id = idSeed++;
		}
		
		public int getId()
		{
			return id;
		}
		
		public int getX()
		{
			return x;
		}
		
		public int getY()
		{
			return y;
		}
		
		public void incArea()
		{
			area++;
		}
		
		public boolean equals(Point point)
		{
			if(point == null)
				return false;
			if(this.id == point.getId())
				return true;
			else
				return false;
		}
		
		public long getArea()
		{
			return area;
		}
	}
}
