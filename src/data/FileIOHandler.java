package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.filechooser.FileSystemView;

/**
 * @author Leon Kelle
 * @class AGB\AH911
 * @project TicTacToe
 */

public class FileIOHandler
{
	private static volatile FileIOHandler instance;
	private String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\";
	private String file = "TicTacToe.csv";

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

	public ArrayList<String> readFile()
	{
		ArrayList<String> fileAsList = new ArrayList<>();

		try (FileReader fileReader = new FileReader(path + file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);)
		{
			String line = bufferedReader.readLine(); // Read first Line

			while (line != null)
			{
				fileAsList.add(line);
				line = bufferedReader.readLine();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return fileAsList;
	}

	public void writeFile()
	{
		try (FileWriter fileWriter = new FileWriter(path + file);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter))
		{
			bufferedWriter.write("Erste Zeile");
			bufferedWriter.newLine(); // LineBreak
			bufferedWriter.write("Zweite Zeile, die ein wenig länger ist.");
			bufferedWriter.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
