import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Day1 
{
	
	private static Scanner sc = new Scanner(System.in);
	
	private static List<Integer> frequencyList = new ArrayList<>();
	private static Set<Integer> occuredFrequencySet =	new HashSet<>();

	public static void main(String[] args) 
	{
		loadData();
		
		int sum = 0;
		occuredFrequencySet.add(sum);
		
		for(int i=0; i<frequencyList.size(); i = ++i%frequencyList.size())
		{
			Integer temp = frequencyList.get(i);
			sum += temp.intValue();
			if(occuredFrequencySet.contains(new Integer(sum)) == true)
			{
				System.out.println("Repeated frequency: " + sum);
				break;
			}
			occuredFrequencySet.add(new Integer(sum));
		}
		
		System.out.println("Sum equals: "+ sum);

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
				frequencyList.add((new Integer(fileScanner.nextInt())));
			}
			fileScanner.close();
				
		} catch (FileNotFoundException e) 
		{
			System.out.println("Error in reading from a file");
			return;
		}

	}

}
