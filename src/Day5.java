import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Day5 
{
	private static String polymer;
	private static HashSet<Character> characterSet = new HashSet<>();
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException
	{
		loadData();
		//System.out.println("polimer: ");
		digest(polymer);
		
		for (char c : polymer.toCharArray()) 
		{
			  characterSet.add(Character.toLowerCase(c));
		}
		System.out.println("Character set size: "+ characterSet.size());
		System.out.println("----------------");
		
		int min = Integer.MAX_VALUE;
		char minForChar = '1';
		
		for(Character character : characterSet)
		{
			char lower = Character.toLowerCase(character);
			char upper = Character.toUpperCase(character);
			String regex = lower+"|"+upper;
			String input = polymer.replaceAll(regex, "");
			
			System.out.println(upper);
			
			int result = digest(input);
			if(result < min)
			{
				min = result;
				minForChar = upper;
			}
			
		}
		
		System.out.println("Min : "+min+" for char: "+minForChar);
		


	}
	
	public static int digest(String poly)
	{
		ArrayList<Character> charArray = new ArrayList<>(50000);
		for (char c : poly.toCharArray()) 
		{
			  charArray.add(c);
		}
		
		
		boolean changeFlag = true;
		while(changeFlag == true)
		{
			for(int i=0; i<charArray.size()-1; i++)
			{
				changeFlag = false;
				char firstChar = charArray.get(i);
				char secondChar = charArray.get(i+1);
				if(Character.toLowerCase(firstChar) == Character.toLowerCase(secondChar) )
				{
					if((Character.isUpperCase(firstChar) && Character.isLowerCase(secondChar))
							|| (Character.isLowerCase(firstChar) && Character.isUpperCase(secondChar)) )
					{
						charArray.remove(i);
						charArray.remove(i);
						changeFlag = true;
						break;
					}
						
				}

			}
			

		}

		System.out.println("Char array size : "+ charArray.size());
		return charArray.size();
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

				polymer = fileScanner.next();
			
			fileScanner.close();
				
		} catch (FileNotFoundException e) 
		{
			System.out.println("Error in reading from a file");
			return;
		}

	}

}
