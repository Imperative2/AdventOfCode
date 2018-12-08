import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Day3 
{

	private static List<ElfClaim> elfClaims = new ArrayList<>();
	private static int[][] materialClaims = new int[1000][1000];
	private static ElfClaim[][] elfClaimOnMaterial = new ElfClaim[1000][1000];
	private static HashMap<String,ElfClaim> goodClaims = new HashMap<>();
	
	
	public static void main(String[] args) 
	{
		for(int i=0; i < materialClaims.length; i++)
		{
			for(int j=0; j< materialClaims[0].length; j++)
			{
				materialClaims[i][j] = 0;
				elfClaimOnMaterial[i][j] = null;
			}
		}
		loadData();
		
		System.out.println("good claims size: " + goodClaims.size()+" elfClaims: "+elfClaims.size());
		
		for(ElfClaim claim : elfClaims)
		{
			//System.out.println("start vertical: "+ claim.getStartVertical()+" end vertical "+ claim.getEndVertical() + " start horizontal " + claim.getStartHorizontal() + " end horizontal " + claim.getEndHorizontal());
			for(int i=claim.getStartHorizontal(); i<claim.getEndHorizontal(); i++)
			{
				for(int j = claim.getStartVertical(); j< claim.getEndVertical(); j++)
				{
					materialClaims[i][j]++;
					if(elfClaimOnMaterial[i][j] == null)
					{
						elfClaimOnMaterial[i][j] = claim;
					}
					else
					{
						ElfClaim otherClaim = elfClaimOnMaterial[i][j];
						goodClaims.remove(claim.getId());
						goodClaims.remove(otherClaim.getId());
					}
				}
			}
		}
		
		int fabricOverClaimed = 0;
		
		for(int i=0; i < materialClaims.length; i++)
		{
			for(int j=0; j< materialClaims[0].length; j++)
			{
				if(materialClaims[i][j] > 1)
				{
					fabricOverClaimed++;
				}
			}
		}
		
		System.out.println("Fabric over claimed: "+ fabricOverClaimed);
		System.out.println("good claims size: " + goodClaims.size());
		System.out.println("good claim index: "+Arrays.asList(goodClaims));

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
				String line = fileScanner.nextLine();
				System.out.println(line);
				line = line.replaceAll("@|,|x|\\s|:"," ");
				line = line.replaceAll("\\s+", " ");
				line = line.trim();
				System.out.println("Replaced line:"+line);
				String[] values = line.split(" ");
				System.out.println(values.length);
				String id = values[0];
				int leftOffset = Integer.parseInt(values[1]);
				int topOffset = Integer.parseInt(values[2]);
				int length = Integer.parseInt(values[3]);
				int heigth = Integer.parseInt(values[4]);
				ElfClaim claim = new ElfClaim(id,heigth, length, leftOffset, topOffset);
				
				elfClaims.add(claim);
				System.out.println(claim.getId()+"x");
				goodClaims.put(claim.getId(), claim);
				
			}
			fileScanner.close();
				
		} catch (FileNotFoundException e) 
		{
			System.out.println("Error in reading from a file");
			return;
		}

	}
	public static class ElfClaim
	{
		private String id;
		private int heigth;
		private int length;
		private int leftOffset;
		private int topOffset;
		
		public ElfClaim(String id, int heigth, int length, int leftOffset, int topOffset)
		{
			this.id = id;
			this.heigth = heigth;
			this.length = length;
			this.leftOffset = leftOffset;
			this.topOffset = topOffset;
		}
		public String getId()
		{
			return id;
		}
		public int getStartHorizontal()
		{
			return leftOffset> 1000 ? 1000 : leftOffset;
		}
		public int getEndHorizontal()
		{
			return leftOffset+length >= 1000 ? 1000 :leftOffset+length;
		}
		public int getStartVertical()
		{
			return topOffset > 1000 ? 1000 : topOffset;
		}
		public int getEndVertical()
		{
			return topOffset+heigth >= 1000 ? 1000: topOffset+heigth;
		}
	}
}
