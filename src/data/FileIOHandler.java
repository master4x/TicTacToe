package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Leon Kelle
 * @class AGB\AH911
 * @project TicTacToe
 */

public class FileIOHandler
{
	public ArrayList<String> readFile(String pathAndFileName)
	{
		ArrayList<String> fileAsList = new ArrayList<>();

		try (FileReader fileReader = new FileReader(pathAndFileName);
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

	public void writeFile(String pathAndFileName)
	{
		try (FileWriter fileWriter = new FileWriter(pathAndFileName);
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
