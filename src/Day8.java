import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Day8
{
	
	private static ArrayList<Integer> fieldsArray = new ArrayList<>();
	private static Integer[] fields;
	private static HashSet<Node> nodeSet = new HashSet<>();
	
 
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		loadData();
		
		System.out.println(fieldsArray.toString());
		
		fields = fieldsArray.stream().toArray(Integer[]::new);
		

		
		Node root = new Node(0,null, fieldsArray);
		
		long sum = 0;
		for(Node node : nodeSet)
		{
			sum += node.getMetaDataSum();
		}
		
		System.out.println("Sum of all metadata: "+sum);
		System.out.println(fieldsArray.toString());
		System.out.println("Deep meta data sum: "+ Node.getDeepMetaDataSum(root));
		
		System.out.println("Root metaData sum : "+root.getRootSum());

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
		sc.close();

		Scanner fileScanner;
		try
		{
			fileScanner = new Scanner(file);
			while (fileScanner.hasNext())
			{
				fieldsArray.add(fileScanner.nextInt());
			}
			fileScanner.close();

		} catch (FileNotFoundException e)
		{
			System.out.println("Error in reading from a file");
			return;
		}
	}
	
	static class Node
	{
		private Node parent;
		private ArrayList<Integer> metaData = new ArrayList<>();
		private ArrayList<Node> children = new ArrayList<>();
		
		public Node(int index, Node parent, ArrayList<Integer> fieldsArray)
		{
			//System.out.println("creating node");
			this.parent = parent;
			int numberOfKids = fieldsArray.get(index);
			fieldsArray.remove(index);
			int numberOfMetaData = fieldsArray.get(index);
			fieldsArray.remove(index);
			for(int i=0; i<numberOfKids; i++ )
			{
				
				Node child = new Node(index, this, fieldsArray);
				nodeSet.add(child);
				children.add(child);
			}
			for(int i=0; i<numberOfMetaData; i++)
			{
				metaData.add(fieldsArray.get(index));
				fieldsArray.remove(index);
			}
		}
		
		public int getMetaDataSum()
		{
			int sum = 0;
			for(Integer metadata : metaData)
			{
				sum += metadata;
			}
			return sum;
		}
		
		public static int getDeepMetaDataSum(Node node)
		{
			ArrayList<Integer> metaData = node.getMetaData();
			ArrayList<Node> children = node.getChildren();
			
			int sum = 0;
			for(Integer metadata : metaData)
			{
				sum += metadata;
			}
			
			for(Node child : children)
			{
				sum += getDeepMetaDataSum(child);
			}
			
			return sum;
			
		}
		
		public ArrayList<Node> getChildren()
		{
			return children;
		}
		
		public ArrayList<Integer> getMetaData()
		{
			return metaData;
		}
		
		public int getRootSum()
		{
			int sum = 0;
			if(children.size() == 0)
			{
				return getMetaDataSum();
			}
			
			for(Integer metadata : metaData)
			{
				int metaInt = metadata;
				metaInt--;
				if( metaInt > -1 && metaInt < children.size() )
				{
					Node child = children.get(metaInt);
					sum += child.getRootSum();
				}
			}
			
			return sum;
		}
		

	}
}
