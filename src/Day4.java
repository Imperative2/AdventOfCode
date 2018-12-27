import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.xml.crypto.Data;

public class Day4
{
	private static List<Message> messagesList = new ArrayList<>();
	private static Set<String> guardSet = new HashSet<>();
	private static List<Guard> guardArray = new ArrayList<>();

	public static void main(String[] args)
	{
		loadData();
		Object[] messagesArray = messagesList.toArray();
		Arrays.sort(messagesArray);
		Message[] sortedMessages = new Message[messagesArray.length];
		for (int i = 0; i < messagesArray.length; i++)
		{
			sortedMessages[i] = (Message) messagesArray[i];
			// System.out.println(sortedMessages[i].getMessage());
		}
		String[] guardsString = guardSet.toArray(new String[guardSet.size()]);
		for (String s : guardsString)
		{
			// System.out.println("Guard : "+s);
			guardArray.add(new Guard(s));
		}

		String guardId = sortedMessages[0].getGuardId();
		for (int i = 1; i < sortedMessages.length; i++)
		{
			Message message = sortedMessages[i];
			if (message.getGuardId() != null)
			{
				guardId = message.getGuardId();
			}
			else if (message.getGuardId() == null)
			{
				message.setGuardId(guardId);
			}
		}

		for (int i = 0; i < sortedMessages.length; i++)
		{
			// System.out.println(sortedMessages[i].getMessage()+"
			// "+sortedMessages[i].getGuardId());
		}

		for (Guard guard : guardArray)
		{
			for (int i = 0; i < sortedMessages.length; i++)
			{
				if (sortedMessages[i].guardId.equals(guard.getId()))
				{

					if (sortedMessages[i].getMessage().charAt(0) == 'f')
					{
						guard.addSleepingTime(sortedMessages[i], sortedMessages[++i]);
					}
				}
			}
			System.out.println(guard.getId() + " overal time slept: " + guard.getTimeSleept());
		}

		Guard mostSleepyGuard = guardArray.get(0);

		for (Guard guard : guardArray)
		{
			if (mostSleepyGuard.getTimeSleept() < guard.getTimeSleept())
			{
				mostSleepyGuard = guard;
			}
			System.out.println(" guard id: " + guard.id + " minute slept most: " + guard.minuteSleptMost() + " time : "
					+ guard.timeSleptMostMinute());
		}

		System.out.println("Most sleepy guard: " + mostSleepyGuard.getId() + " minute slept most: "
				+ mostSleepyGuard.minuteSleptMost());

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
				String line = fileScanner.nextLine();
				// System.out.println(line);
				line = line.replaceAll("\\[|\\-|\\:|\\]\\s", " ");
				// System.out.println(line);
				line = line.trim();
				String[] data = line.split(" ");
				if (data.length == 9)
				{
					int year = Integer.parseInt(data[0]);
					int month = Integer.parseInt(data[1]);
					int day = Integer.parseInt(data[2]);
					int hour = Integer.parseInt(data[3]);
					int minute = Integer.parseInt(data[4]);
					String messageText = data[6] + " " + data[7];
					guardSet.add(data[6]);
					// System.out.println("message: "+ messageText);

					Message message = new Message(year, month, day, hour, minute, messageText);
					message.setGuardId(data[6]);
					messagesList.add(message);
				}
				else if (data.length == 7)
				{
					int year = Integer.parseInt(data[0]);
					int month = Integer.parseInt(data[1]);
					int day = Integer.parseInt(data[2]);
					int hour = Integer.parseInt(data[3]);
					int minute = Integer.parseInt(data[4]);
					String messageText = data[5];
					// System.out.println("message: "+ messageText);

					Message message = new Message(year, month, day, hour, minute, messageText);

					messagesList.add(message);
				}
				else
				{
					System.out.println("error");

				}

			}
			fileScanner.close();

		}
		catch (FileNotFoundException e)
		{
			System.out.println("Error in reading from a file");
			sc.close();
			return;
		}
		sc.close();
	}

	static class Message extends GregorianCalendar
	{
		private String message;
		private Calendar date;
		private String guardId = null;

		public Message()
		{

		}

		public Message(int year, int month, int day, int hour, int minute, String message)
		{
			super(year, month, day, hour, minute);
			this.message = message;
		}

		public String getMessage()
		{
			return message;
		}

		public void setGuardId(String guardId)
		{
			this.guardId = guardId;
		}

		public String getGuardId()
		{
			return guardId;
		}
	}

	static class Guard
	{
		private String id = null;

		private int[] hoursSlept = new int[60];

		public Guard(String id)
		{
			this.id = id;
			Arrays.fill(hoursSlept, 0);
		}

		public void addSleepingTime(Message messOne, Message messTwo)
		{
			int start = 0;
			int stop = 59;
			if (messOne.get(Calendar.HOUR) == 23)
			{
				start = 0;
			}
			else
			{
				start = messOne.get(Calendar.MINUTE);
			}

			if (messTwo.get(Calendar.HOUR) == 1)
			{
				stop = 59;
			}
			else
			{
				stop = messTwo.get(Calendar.MINUTE);
			}
			for (int i = start; i < stop; i++)
			{
				hoursSlept[i]++;
			}
		}

		public long getTimeSleept()
		{
			long sum = 0;
			for (int time : hoursSlept)
			{
				sum += time;
			}
			return sum;
		}

		public int minuteSleptMost()
		{
			int max = Integer.MIN_VALUE;
			int id = 0;
			for (int i = 0; i < hoursSlept.length; i++)
			{
				if (hoursSlept[i] > max)
				{
					max = hoursSlept[i];
					id = i;
				}

			}
			return id;
		}

		public int timeSleptMostMinute()
		{
			return hoursSlept[minuteSleptMost()];
		}

		public String getId()
		{
			return id;
		}
	}
}
