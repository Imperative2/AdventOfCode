import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day14
{

	public static void main(String[] args)
	{
		int firstElfPos = 0;
		int secondElfPos = 1;
		int afterRecipies = 440231;
		String[] seq = "4 4 0 2 3 1".split(" ");
		List<Integer> recipies = new ArrayList<>();

		recipies.add(3);
		recipies.add(7);
		


		while (recipies.size() < afterRecipies + 10)
		{
			int recipieNew = recipies.get(firstElfPos) + recipies.get(secondElfPos);
			if (recipieNew > 9)
			{
				recipies.add(recipieNew / 10);
				recipies.add(recipieNew % 10);
			}
			else
				recipies.add(recipieNew);

			firstElfPos = (1 + recipies.get(firstElfPos) + firstElfPos) % recipies.size();
			secondElfPos = (1 + recipies.get(secondElfPos) + secondElfPos) % recipies.size();
		}

		for (int i = afterRecipies; i < recipies.size(); i++)
		{
			System.out.print(recipies.get(i));
		}


		LinkedList<Integer> sequence = new LinkedList<>();
		for (int i = 0; i < seq.length; i++)
		{
			sequence.add(Integer.parseInt(seq[i]));

		}
		
		System.gc();
		recipies = new ArrayList<>(1000000);
		firstElfPos = 0;
		secondElfPos = 1;
		recipies.add(3);
		recipies.add(7);

		while (recipieMatches(sequence, recipies) == false)
		{
			int recipieNew = recipies.get(firstElfPos) + recipies.get(secondElfPos);
			if (recipieNew > 9)
			{
				recipies.add(recipieNew / 10);
				if(recipieMatches(sequence, recipies) == true)
				{
					break;	
				}
				recipies.add(recipieNew % 10);
			}
			else
				recipies.add(recipieNew);

			firstElfPos = (1 + recipies.get(firstElfPos) + firstElfPos) % recipies.size();
			secondElfPos = (1 + recipies.get(secondElfPos) + secondElfPos) % recipies.size();

		}
		
		System.out.println("\n appears after : "+(recipies.size()-sequence.size()));

	}

	private static boolean recipieMatches(LinkedList<Integer> sequence, List<Integer> recipies)
	{
		if (sequence.size() > recipies.size())
		{
			return false;
		}

		for (int i = 0; i < sequence.size(); i++)
		{
			if (sequence.get(i).equals(recipies.get(recipies.size() - sequence.size() + i)) == false)
			{
				return false;
			}
		}
		return true;
	}

}
