
public class Day11
{
	
	private static int input = 4455;
	private static int[][] fuelCells;
	
	public static void main(String[] args)
	{
		fuelCells = new int[300][300];
		
		for(int i=0; i<fuelCells.length; i++)
		{
			for(int j = 0; j< fuelCells[0].length; j++)
			{
				int rackId = i+1;
				rackId += 10;
				int power = rackId * (j+1);
				power+= input;
				power = power * rackId;
				power = ((power%1000)/100) - 5;
				fuelCells[i][j] = power;
				//System.out.println(fuelCells[i][j]);
			}

		}
		
		
		int atX=0;
		int atY = 0;
		int atSize = 0;
		int maxPower=Integer.MIN_VALUE;
		
		System.out.println("calculating");
		
		for(int size=1; size <= fuelCells.length; size++)
		{
			for(int i=0; i<fuelCells.length -(size-1); i++)
			{
				for(int j=0; j< fuelCells[0].length-(size-1) ; j++)
				{
					int currentPower = calculatePowerSquare(i,j, size);
					if(currentPower > maxPower)
					{
						maxPower = currentPower;
						atX = i;
						atY = j;
						atSize = size;
						System.out.println("Max power of: "+maxPower+" at x: "+(atX+1)+" at y: "+(atY+1)+" at size: "+atSize);
					}
				}
			}
		}
		System.out.println("DONE");

		System.out.println("Max power of: "+maxPower+" at x: "+(atX+1)+" at y: "+(atY+1)+" at size: "+atSize);

	}
	
	private static int calculatePowerSquare(int x, int y, int size)
	{
		int result = 0;
		for(int i=0; i<size; i++ )
		{
			for(int j=0; j<size; j++)
			{
				result+= fuelCells[x+i][y+j];
			}
		}
		
		return result;
	}

}
