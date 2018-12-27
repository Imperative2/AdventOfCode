import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Day13
{

	private static char[][] tracks;
	private static List<Cart> cartsList = new ArrayList<>();

	private static boolean collision = false;

	public static void main(String[] args)
	{
		loadData();

		Collections.sort(cartsList);

		dispTracks();

		dispCarts();

		int collisionX = 0;
		int collisionY = 0;

		while (collision == false)
		{
			for (Cart cart : cartsList)
			{
				if (cart.move(tracks) == true)
				{
					collision = true;
					collisionX = cart.getX();
					collisionY = cart.getY();
					System.out.println(cart.toString());
					break;
				}
			}

			// dispTracks();

			// dispCarts();

			Collections.sort(cartsList);

		}

		System.out.println("Collision x: " + collisionX + " Collision y: " + collisionY);

		cartsList = new ArrayList<>();
		loadData();
		dispTracks();

		Collections.sort(cartsList);

		dispCarts();

		long in = 0;
		// for(int i = 0; i<4566; i++)
		while (cartsList.size() > 1)
		{
			in++;
			for (Cart cart : cartsList)
			{
				if (cart.move(tracks) == true)
				{
					collision = true;
					collisionX = cart.getX();
					collisionY = cart.getY();
					
					System.out.println("Collision x: " + collisionX + " Collision y: " + collisionY);

					for (Cart anotherCart : cartsList)
					{
						if (anotherCart != cart && anotherCart.getX() == collisionX && anotherCart.getY() == collisionY)
						{
							cart.setAccident(true);
							anotherCart.setAccident(true);
							cart.setTrackShape(anotherCart.getTrackShape());
						}
					}

				}
			}

			Iterator<Cart> iterator = cartsList.iterator();
			while (iterator.hasNext())
			{
				Cart cart = iterator.next();
				if (cart.hadAccident() == true)
				{
					tracks[cart.getY()][cart.getX()] = cart.getTrackShape();
					iterator.remove();
				}
			}

			// if(i>4550)
			// {
			// dispTracks();
			// dispCarts();
			// }

			Collections.sort(cartsList);

		}

		dispTracks();
		System.out.println("Finished " + in);
		dispCarts();

	}

	private static void dispTracks()
	{
		for (int i = 0; i < tracks.length; i++)
		{
			for (int j = 0; j < tracks[0].length; j++)
			{
				System.out.print(tracks[i][j]);
			}
			System.out.println();
		}
	}

	private static void dispCarts()
	{
		for (Cart cart : cartsList)
		{
			System.out.println(cart.toString());
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
			List<String> rows = new ArrayList<>();

			while (fileScanner.hasNext())
			{
				String row = fileScanner.nextLine();
				rows.add(row);

			}
			tracks = new char[rows.size()][rows.get(0).length()];

			for (int i = 0; i < tracks.length; i++)
			{
				for (int j = 0; j < tracks[0].length; j++)
				{
					char c = rows.get(i).charAt(j);
					tracks[i][j] = c;
					if (c == '^' || c == 'v')
					{
						Cart cart = new Cart(j, i, c, '|');
						cartsList.add(cart);
					}
					else if (c == '<' || c == '>')
					{
						Cart cart = new Cart(j, i, c, '-');
						cartsList.add(cart);
					}
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

	static class Cart implements Comparable<Cart>
	{
		private int x;
		private int y;
		private char orientation;
		private char trackShape;
		private boolean accidentFlag = false;
		private int turningPattern = 0;

		public Cart(int x, int y, char orientation, char trackShape)
		{
			this.x = x;
			this.y = y;
			this.orientation = orientation;
			this.trackShape = trackShape;
		}

		public boolean move(char[][] tracks)
		{

			tracks[y][x] = trackShape;

			switch (orientation)
			{
			case '>' :
			{
				trackShape = tracks[y][++x];

				if (trackShape == '\\')
					orientation = 'v';
				else if (trackShape == '/')
				{
					orientation = '^';
				}
				else if (trackShape == '+')
				{
					moveCrossRoads();
					// x--;
				}
				else if (isCart(trackShape))
					return true;
				break;
			}

			case '<' :
			{
				trackShape = tracks[y][--x];

				if (trackShape == '/')
					orientation = 'v';
				else if (trackShape == '\\')
				{
					orientation = '^';
				}
				else if (trackShape == '+')
				{
					moveCrossRoads();
					// x++;
				}
				else if (isCart(trackShape))
					return true;
				break;
			}

			case '^' :
			{
				trackShape = tracks[--y][x];

				if (trackShape == '/')
					orientation = '>';
				else if (trackShape == '\\')
				{
					orientation = '<';
				}
				else if (trackShape == '+')
				{
					moveCrossRoads();
					// y++;
				}
				else if (isCart(trackShape))
					return true;
				break;
			}

			case 'v' :
			{
				trackShape = tracks[++y][x];

				if (trackShape == '/')
					orientation = '<';
				else if (trackShape == '\\')
				{
					orientation = '>';
				}
				else if (trackShape == '+')
				{
					moveCrossRoads();
					// y--;
				}
				else if (isCart(trackShape))
					return true;
				break;
			}

			default :
			{
				System.out.println("Collision");
				collision = true;
			}

			}

			tracks[y][x] = orientation;
			return false;

		}

		public void moveCrossRoads()
		{
			switch (turningPattern)
			{
			case 0 :
			{
				turnLeft();
				break;
			}
			case 1 :
			{
				break;
			}
			case 2 :
			{
				turnRight();
				break;
			}
			}

			turningPattern++;
			turningPattern = turningPattern % 3;
		}

		public void turnLeft()
		{
			if (orientation == '^')
				orientation = '<';

			else if (orientation == '<')
				orientation = 'v';

			else if (orientation == 'v')
				orientation = '>';

			else if (orientation == '>')
				orientation = '^';
		}

		public void turnRight()
		{
			if (orientation == '^')
				orientation = '>';

			else if (orientation == '<')
				orientation = '^';

			else if (orientation == 'v')
				orientation = '<';

			else if (orientation == '>')
				orientation = 'v';
		}

		public boolean isCart(char shape)
		{
			if (shape == '^' || shape == 'v' || shape == '<' || shape == '>')
				return true;
			else
				return false;
		}

		public char getTrackShape()
		{
			return trackShape;
		}

		public int getX()
		{
			return x;
		}

		public int getY()
		{
			return y;
		}

		public char getOrientation()
		{
			return orientation;
		}

		public void setTrackShape(char trackShape)
		{
			this.trackShape = trackShape;
		}

		public boolean hadAccident()
		{
			return accidentFlag;
		}

		public void setAccident(boolean flag)
		{
			accidentFlag = flag;
		}

		@Override
		public int compareTo(Cart other)
		{
			if (this.getY() <= other.getY())
			{
				if (this.getX() <= other.getX())
				{
					return -1;
				}
				else
				{
					return 1;
				}
			}

			return 1;
		}

		public String toString()
		{
			return " x: " + x + " y: " + y + " or: " + orientation;
		}
		
	}
}
