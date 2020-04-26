import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVFormat;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

class CSVReader {

	private static final String csvFile = "../csv1/src/main/java/Input/main.csv";
	private static final String newCSV = "../csv1/src/main/java/Output/filteredCountry.csv";

	public static void main(String args[]) throws IOException {
		int flag = 0;

		FileWriter writer = null;
		CSVWriter csvWriter = null;
		CSVParser csvParser = null;
		Reader reader = null;

		try {
			reader = Files.newBufferedReader(Paths.get(csvFile));
			csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
			writer = new FileWriter(newCSV);
			csvWriter = new CSVWriter(writer);
			
			//writes data which contains USA in country column in .csv file
			for (CSVRecord csvRecord : csvParser) {
				if (csvRecord.get(8).contains("USA") || flag == 0) {
					flag = 1;
					csvWriter.writeNext(new String[] { csvRecord.get(0), csvRecord.get(1), csvRecord.get(2),
							csvRecord.get(3), csvRecord.get(4), csvRecord.get(5).replaceAll("[^a-zA-Z0-9.]", "") , csvRecord.get(6), csvRecord.get(7),
							csvRecord.get(8) });
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			csvWriter.close();
			writer.close();
			csvParser.close();
		}
	}
}