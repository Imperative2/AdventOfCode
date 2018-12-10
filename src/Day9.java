import java.util.ArrayList;

public class Day9
{
	private static ArrayList<Integer> gameBoard = new ArrayList<>(71307 * 100);
	private static int ballSeed = 1;
	private static ArrayList<Player> playersList = new ArrayList<>();
	private static int numberOfIteration = 0;

	private static int currentPosition = 0;

	public static void main(String[] args)
	{
		gameBoard.add(0);

		int numberOfPlayers = 458;

		for (int i = 0; i < numberOfPlayers; i++)
		{
			playersList.add(new Player(i));
		}

		for (int i = 0; i < 71307 * 100; i++)
		{
			if (i % 100000 == 0)
				System.out.println("Number of iterations :" + (numberOfIteration++) + "  calculating");

			currentPosition = playersList.get(i % numberOfPlayers).makeMove(ballSeed++, currentPosition, gameBoard);
		}
		long maxScore = 0;
		int playerNumber = 0;
		for (Player player : playersList)
		{
			player.printPlayerScore();
			if (player.getScore() > maxScore)
			{
				maxScore = player.getScore();
				playerNumber = player.getPlayerNumber();
			}

		}
		System.out.println("\nMax score for player: " + playerNumber + "  : " + maxScore);
	}

	static class Player
	{
		private int playerNumber;
		private long points = 0;

		public Player(int playerNumber)
		{
			this.playerNumber = playerNumber;
		}

		public int makeMove(int marble, int currentPosition, ArrayList<Integer> gameBoard)
		{
			if (marble % 23 == 0)
			{
				if (currentPosition - 7 <= 0)
					currentPosition = currentPosition + gameBoard.size();

				currentPosition = (currentPosition - 7) % gameBoard.size();
				points += marble;
				points += gameBoard.get(currentPosition);
				gameBoard.remove(currentPosition);
			}
			else
			{

				if (currentPosition == gameBoard.size() - 1)
				{
					gameBoard.add(1, marble);
					currentPosition = 1;
				}
				else if (currentPosition == gameBoard.size() - 2)
				{
					currentPosition = gameBoard.size();
					gameBoard.add(marble);
				}
				else
				{
					currentPosition += 2;

					currentPosition = currentPosition % gameBoard.size();
					if (currentPosition == 0)
					{
						gameBoard.add(1, marble);
						currentPosition = 1;
					}
					else
					{
						gameBoard.add(currentPosition, marble);
					}
				}

			}

			// System.out.println("[" + (playerNumber+1) + "]" + gameBoard.toString());

			return currentPosition;
		}

		public void printPlayerScore()
		{
			System.out.println("Player[" + (playerNumber + 1) + "] score: " + points);
		}

		public long getScore()
		{
			return points;
		}

		public int getPlayerNumber()
		{
			return playerNumber;
		}

	}
}
