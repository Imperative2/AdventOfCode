import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.*;


public class Day19
{
	private static List<Message> instructionsList = new ArrayList<>();
	private static int ipReg;
	private static int pCounter = 0;
	private static int[] registers = new int[6];

	public static void main(String[] args)
	{
		Arrays.fill(registers, 0);
		registers[0] = 1;

		loadData();
		
		
		while(pCounter>=0 && pCounter<instructionsList.size())
		{
			Message instruction = instructionsList.get(pCounter);
			instruction.setBeforeRegs(registers);
			instruction.calculate();
			registers = instruction.getAfterRegs();
			registers[ipReg]++;
			pCounter = registers[ipReg];
		}
		
		System.out.println("End of program: ");
		System.out.println(Arrays.toString(registers));

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
			String ipRegLine;
			ipRegLine = fileScanner.nextLine();
			ipRegLine = ipRegLine.replaceAll("#ip ","");
			ipRegLine = ipRegLine.trim();
			ipReg = Integer.parseInt(ipRegLine);

			String line;
			
			while (fileScanner.hasNext())
			{
				line = fileScanner.nextLine();
				line = line.trim();
				String[] words = line.split(" ");
				
				Message message = new Message(0,0,0,0,0,0);

				String opCode = words[0];
				int a = Integer.parseInt(words[1]);
				int b = Integer.parseInt(words[2]);
				int c = Integer.parseInt(words[3]);

				message.setOpCode(opCode, a, b, c);
				instructionsList.add(message);
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

		
		private int[] regBefore = new int[6];
		
		private int[] regAfter = new int[6];

		private String opCode;
		private int regA;
		private int regB;
		private int regC;

		public Message(int reg0, int reg1, int reg2, int reg3, int reg4, int reg5)
		{
			regBefore[0] = reg0;
			regBefore[1] = reg1;
			regBefore[2] = reg2;
			regBefore[3] = reg3;
			regBefore[4] = reg4;
			regBefore[5] = reg5;
		}

		public void setBeforeRegs(int[] regBefore)
		{
			this.regBefore = regBefore;
		}

		public void setOpCode(String opcode, int a, int b, int c)
		{
			opCode = opcode;
			regA = a;
			regB = b;
			regC = c;
		}

		public void setAfterRegs(int reg0, int reg1, int reg2, int reg3, int reg4, int reg5)
		{
			regBefore[0] = reg0;
			regBefore[1] = reg1;
			regBefore[2] = reg2;
			regBefore[3] = reg3;
			regBefore[4] = reg4;
			regBefore[5] = reg5;
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
				case "eqir":
				{
					regAfter = eqir();
					break;
				}
				case "borr":
				{
					regAfter = borr();
					break;
				}
				case "addr":
				{
					regAfter = addr();
					break;
				}
				case "gtri":
				{
					regAfter = gtri();
					break;
				}
				case "muli":
				{
					regAfter = mulli();
					break;
				}
				case "gtir":
				{
					regAfter = gtir();
					break;
				}
				case "mulr":
				{
					regAfter = mullr();
					break;
				}
				case "banr":
				{
					regAfter = banr();
					break;
				}
				case "bori":
				{
					regAfter = bori();
					break;
				}
				case "eqri":
				{
					regAfter = eqri();
					break;
				}
				case "eqrr":
				{
					regAfter = eqrr();
					break;
				}
				case "bani":
				{
					regAfter = bani();
					break;
				}
				case "setr":
				{
					regAfter = setr();
					break;
				}
				case "gtrr":
				{
					regAfter = gtrr();
					break;
				}
				case "addi":
				{
					regAfter = addi();
					break;
				}
				case "seti":
				{
					regAfter = seti();
					break;
				}
				
			}
		}

		public boolean regEquals(int[] regResult)
		{
			for(int i=0;i<6; i++)
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
