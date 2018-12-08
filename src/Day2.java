import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Day2 {

	private static Scanner sc = new Scanner(System.in);
	
	private static List<String> boxesIds = new ArrayList<>();
	
	
	public static void main(String[] args) 
	{
		int twos = 0;
		int threes = 0;
		boolean twosFlag = false;
		boolean threesFlag = false;
		loadData();
		for(String boxId : boxesIds)
		{
			twosFlag = false;
			threesFlag = false;
			HashMap<Character,Integer> wordMap = new HashMap<>();
			for(int i=0; i<boxId.length(); i++)
			{
				Character c = Character.valueOf(boxId.charAt(i));
				if(wordMap.containsKey(c))
				{
					Integer value = wordMap.get(c);
					int intValue = value.intValue();
					intValue++;
					wordMap.replace(c, Integer.valueOf(intValue));
				}
				else
				{
					wordMap.put(c, Integer.valueOf(1));
				}
			}
			
			Collection<Integer> wordValues = wordMap.values();
			for(Integer value: wordValues)
			{
				if(value.intValue() == 2 && twosFlag == false)
				{
					twosFlag = true;
				}
				if(value.intValue() == 3 && threesFlag == false)
				{
					threesFlag = true;
				}
			}
			
			if(twosFlag == true)
			{
				twos++;
			}
			if(threesFlag == true)
			{
				threes++;
			}
			
		}
		
		System.out.println("twos: "+twos+" threes: "+threes+" sum: "+twos*threes);
		
		String boxA = "";
		String boxB = "";
		
		
		for(int i=0; i<boxesIds.size(); i++)
		{
			for(int j=0; j<boxesIds.size(); j++)
			{
				if(i == j)
					continue;
				String thisBox = boxesIds.get(i);
				String otherBox = boxesIds.get(j);
				int differenceValue = 0;
				
				if(thisBox.length() == otherBox.length())
				{
					for(int k=0; k<thisBox.length(); k++)
					{
						if(thisBox.charAt(k) != otherBox.charAt(k))
						{
							differenceValue++;
						}
					}
					if(differenceValue == 1)
					{
						boxA = thisBox;
						boxB = otherBox;
					}
				}

			}
			
		}
		
		System.out.println(boxA);
		System.out.println(boxB);

	}
	
	private static void loadData()
	{
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
				boxesIds.add(fileScanner.next());
			}
			fileScanner.close();
				
		} catch (FileNotFoundException e) 
		{
			System.out.println("Error in reading from a file");
			return;
		}

	}

}
