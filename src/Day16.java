import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day16
{

	private static List<Message> messagesList = new ArrayList<>();
	private static List<Message> opCodesList = new ArrayList<>();

	public static void main(String[] args)
	{
		loadData();
		int counter = 0;
		for(Message message : messagesList)
		{
			if(message.doAction() > 2)
			{
				counter++;
			}
		}
		
		System.out.println("Number of function fitting 3 or more: "+counter);
		
		loadData();
		
		int[] regBefore = opCodesList.get(0).getBeforeRegs();

		
		for(Message message : opCodesList)
		{
			message.setBeforeRegs(regBefore);
			message.calculate();
			regBefore = message.getAfterRegs();
				
		}
		
		System.out.println(Arrays.toString(regBefore));
		
		

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
				String row = fileScanner.nextLine();
				String[] line = null;

				if (row.length() < 2)
					continue;
				if (row.matches("Before:.*"))
				{

					row = row.replaceAll("Before:|\\[|\\]|\\,", "");
					row = row.trim();
					line = row.split(" ");
					int reg0 = Integer.parseInt(line[0]);
					int reg1 = Integer.parseInt(line[1]);
					int reg2 = Integer.parseInt(line[2]);
					int reg3 = Integer.parseInt(line[3]);

					Message message = new Message(reg0, reg1, reg2, reg3);

					row = fileScanner.nextLine();
					line = row.split(" ");

					int opCode = Integer.parseInt(line[0]);
					int a = Integer.parseInt(line[1]);
					int b = Integer.parseInt(line[2]);
					int c = Integer.parseInt(line[3]);

					message.setOpCode(opCode, a, b, c);

					row = fileScanner.nextLine();
					row = row.replaceAll("After:|\\[|\\]|\\,", "");
					row = row.trim();
					line = row.split(" ");

					reg0 = Integer.parseInt(line[0]);
					reg1 = Integer.parseInt(line[1]);
					reg2 = Integer.parseInt(line[2]);
					reg3 = Integer.parseInt(line[3]);

					message.setAfterRegs(reg0, reg1, reg2, reg3);
					
					messagesList.add(message);

				}
				else
				{
					Message message = new Message(0,0,0,0);
					line = row.split(" ");
					int opCode = Integer.parseInt(line[0]);
					int a = Integer.parseInt(line[1]);
					int b = Integer.parseInt(line[2]);
					int c = Integer.parseInt(line[3]);

					message.setOpCode(opCode, a, b, c);
					opCodesList.add(message);
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

	static class Message
	{

		
		private int[] regBefore = new int[4];
		
		private int[] regAfter = new int[4];

		private int opCode;
		private int regA;
		private int regB;
		private int regC;

		public Message(int reg0, int reg1, int reg2, int reg3)
		{
			regBefore[0] = reg0;
			regBefore[1] = reg1;
			regBefore[2] = reg2;
			regBefore[3] = reg3;
		}

		public void setBeforeRegs(int[] regBefore)
		{
			this.regBefore = regBefore;
		}

		public void setOpCode(int opcode, int a, int b, int c)
		{
			opCode = opcode;
			regA = a;
			regB = b;
			regC = c;
		}

		public void setAfterRegs(int reg0, int reg1, int reg2, int reg3)
		{
			regAfter[0] = reg0;
			regAfter[1] = reg1;
			regAfter[2] = reg2;
			regAfter[3] = reg3;
		}
		
		public int[] getAfterRegs()
		{
			return regAfter;
		}
		
		public int[] getBeforeRegs()
		{
			return regBefore;
		}
		
		public void calculate()
		{
				switch(opCode)
				{
				case 0:
				{
					regAfter = eqir();
					break;
				}
				case 1:
				{
					regAfter = borr();
					break;
				}
				case 2:
				{
					regAfter = addr();
					break;
				}
				case 3:
				{
					regAfter = gtri();
					break;
				}
				case 4:
				{
					regAfter = mulli();
					break;
				}
				case 5:
				{
					regAfter = gtir();
					break;
				}
				case 6:
				{
					regAfter = mullr();
					break;
				}
				case 7:
				{
					regAfter = banr();
					break;
				}
				case 8:
				{
					regAfter = bori();
					break;
				}
				case 9:
				{
					regAfter = eqri();
					break;
				}
				case 10:
				{
					regAfter = eqrr();
					break;
				}
				case 11:
				{
					regAfter = bani();
					break;
				}
				case 12:
				{
					regAfter = setr();
					break;
				}
				case 13:
				{
					regAfter = gtrr();
					break;
				}
				case 14:
				{
					regAfter = addi();
					break;
				}
				case 15:
				{
					regAfter = seti();
					break;
				}
				
			}
		}
		
		
		public int doAction()
		{
			int counter = 0;
			String chosenFunction = "";
			if(regEquals(addr()))
			{
				counter++;
				chosenFunction += " addr";
			}

			if(regEquals(addi()))
			{
				counter++;
				chosenFunction += " addi";
			}
			if(regEquals(mulli()))
			{
				counter++;
				chosenFunction += " mulli";
			}
			if(regEquals(mullr()))
			{
				counter++;
				chosenFunction += " mullr";
			}
			if(regEquals(banr()))
			{
				counter++;
				chosenFunction += " banr";
			}
			if(regEquals(bani()))
			{
				counter++;
				chosenFunction += " bani";
			}
			if(regEquals(borr()))
			{
				counter++;
				chosenFunction += " borr";
			}
			if(regEquals(bori()))
			{
				counter++;
				chosenFunction += " bori";
			}
			if(regEquals(setr()))
			{
				counter++;
				chosenFunction += " setr";
			}
			if(regEquals(seti()))
			{
				counter++;
				chosenFunction += " seti";
			}
			if(regEquals(gtri()))
			{
				counter++;
				chosenFunction += " gtri";
			}
			if(regEquals(gtir()))
			{
				counter++;
				chosenFunction += " gtir";
			}
			if(regEquals(gtrr()))
			{
				counter++;
				chosenFunction += " gtrr";
			}
			if(regEquals(eqir()))
			{
				counter++;
				chosenFunction += " eqir";
			}
			if(regEquals(eqri()))
			{
				counter++;
				chosenFunction += " eqri";
			}
			if(regEquals(eqrr()))
			{
				counter++;
				chosenFunction += " eqrr";
			}
			
			if(counter < 16 && opCode != 0 && opCode != 3 && opCode != 9 && opCode != 10 && opCode != 13 && opCode != 5
					&& opCode != 12 && opCode != 7 && opCode != 11 && opCode != 15 && opCode != 2 && opCode != 14)
			{
				

				// && opCode != 2 && opCode != 14 
				//&& opCode != 6
				System.out.println("opcode : "+opCode+"  func: "+chosenFunction);
			}
			
			return counter;
		}
		
		public boolean regEquals(int[] regResult)
		{
			for(int i=0;i<4; i++)
			{
				if(regResult[i] != regAfter[i])
					return false;
			}
			
			return true;
		}
		
		public int[] addr()
		{
			int[] regResult = regBefore.clone();
			int result;
			result = regBefore[regA] + regBefore[regB];
			regResult[regC] = result;
			return regResult;
			
		}
		
		public int[] addi()
		{
			int[] regResult = regBefore.clone();
			int result;
			result = regBefore[regA] + regB;
			regResult[regC] = result;
			return regResult;
		}
		
		public int[] mullr()
		{
			int[] regResult = regBefore.clone();
			int result;
			result = regBefore[regA] * regBefore[regB];
			regResult[regC] = result;
			return regResult;
		}
		
		public int[] mulli()
		{
			int[] regResult = regBefore.clone();
			int result;
			result = regBefore[regA] * regB;
			regResult[regC] = result;
			return regResult;
		}
		
		public int[] banr()
		{
			int[] regResult = regBefore.clone();
			int result;
			result = regBefore[regA] & regBefore[regB];
			regResult[regC] = result;
			return regResult;
		}
		
		public int[] bani()
		{
			int[] regResult = regBefore.clone();
			int result;
			result = regBefore[regA] & regB;
			regResult[regC] = result;
			return regResult;
		}
		
		public int[] borr()
		{
			int[] regResult = regBefore.clone();
			int result;
			result = regBefore[regA] | regBefore[regB];
			regResult[regC] = result;
			return regResult;
		}
		
		public int[] bori()
		{
			int[] regResult = regBefore.clone();
			int result;
			result = regBefore[regA] | regB;
			regResult[regC] = result;
			return regResult;
		}
		
		public int[] setr()
		{
			int[] regResult = regBefore.clone();
			int result;
			result = regBefore[regA];
			regResult[regC] = result;
			return regResult;
		}
		
		public int[] seti()
		{
			int[] regResult = regBefore.clone();
			int result;
			result = regA;
			regResult[regC] = result;
			return regResult;
		}
		
		public int[] gtir()
		{
			int[] regResult = regBefore.clone();
			int result;
			if(regA > regBefore[regB])
				result = 1;
			else
				result =  0;
			regResult[regC] = result;
			return regResult;
		}
		
		public int[] gtri()
		{
			int[] regResult = regBefore.clone();
			int result;
			if(regBefore[regA] > regB)
				result = 1;
			else
				result =  0;
			regResult[regC] = result;
			return regResult;
		}
		
		public int[] gtrr()
		{
			int[] regResult = regBefore.clone();
			int result;
			if(regBefore[regA] > regBefore[regB])
				result = 1;
			else
				result =  0;
			regResult[regC] = result;
			return regResult;
		}
		
		public int[] eqir()
		{
			int[] regResult = regBefore.clone();
			int result;
			if(regA == regBefore[regB])
				result = 1;
			else
				result =  0;
			regResult[regC] = result;
			return regResult;
		}
		
		public int[] eqri()
		{
			int[] regResult = regBefore.clone();
			int result;
			if(regBefore[regA] == regB)
				result = 1;
			else
				result =  0;
			regResult[regC] = result;
			return regResult;
		}
		
		public int[] eqrr()
		{
			int[] regResult = regBefore.clone();
			int result;
			if(regBefore[regA] == regBefore[regB])
				result = 1;
			else
				result =  0;
			regResult[regC] = result;
			return regResult;
		}
		
		
	}

}
