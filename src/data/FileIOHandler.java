package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.filechooser.FileSystemView;

import logic.GameState;
import view.MainWindow;

/**
 * @author Leon Kelle
 * @class AGB\AH911
 * @project TicTacToe
 */

public class FileIOHandler
{
	private static volatile FileIOHandler instance;
	private String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\";
	private String fileName = "TicTacToe.csv";
	private ArrayList<String[]> statistics = new ArrayList<String[]>();

	private FileIOHandler()
	{
	}

	/*
	 * Singleton
	 */
	public static FileIOHandler getInstance()
	{
		if (instance == null)
		{
			synchronized (FileIOHandler.class)
			{
				if (instance == null)
				{
					instance = new FileIOHandler();
				}
			}
		}
		return instance;
	}

	public void readCSVFile()
	{
		try
		{
			File file = new File(path + fileName);
			
			if (file.exists())
			{
				Scanner fileReader = new Scanner(file);

				while (fileReader.hasNextLine())
				{
					String sessionInfo[] = fileReader.nextLine().split(";");

					statistics.add(sessionInfo);
				}

				fileReader.close();
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		countGameStatistics();
	}

	public void writeCSVFile()
	{
		try
		{
			FileWriter fileWriter = new FileWriter(path + fileName);

			for (String[] sessionInfo : statistics)
			{
				fileWriter.write(sessionInfo[0] + "; " + sessionInfo[1] + "; " + sessionInfo[2]);
				fileWriter.write(System.getProperty("line.separator"));
			}

			fileWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void countGameStatistics()
	{
		int drawCount = 0, looseCount = 0, winCount = 0;
		
		for (String[] sessionInfo : statistics)
		{
			switch (sessionInfo[1])
			{
				case "GameOver_Draw":
					drawCount++;
					break;
				case "GameOver_Loose":
					looseCount++;
					break;
				case "GameOver_Win":
					winCount++;
					break;
			}
		}
		
		MainWindow mainWindowInstance = MainWindow.getInstance();
		
		mainWindowInstance.setTxtDrawCountText(Integer.toString(drawCount));
		mainWindowInstance.setTxtLooseCountText(Integer.toString(looseCount));
		mainWindowInstance.setTxtWinCountText(Integer.toString(winCount));
		mainWindowInstance.addTblGameStatsRows(statistics);
	}
	
	public void addSessionInfo(GameState gameState, String opponentIp)
	{
		String[] sessionInfo = new String[3];
		
		sessionInfo[0] = Integer.toString(statistics.size()+1);
		sessionInfo[1] = gameState.toString();
		sessionInfo[2] = opponentIp;
		
		this.statistics.add(sessionInfo);
	}
}
