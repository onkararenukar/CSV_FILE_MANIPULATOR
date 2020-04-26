import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVFormat;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

class CSVLowPrice {

	private static final String csvFile = "../csv1/src/main/java/Output/filteredCountry.csv";
	private static final String newCSV = "../csv1/src/main/java/Output/lowPrice.csv";
	private static float FIRST_MINIMUM = 0, SECOND_MINIMUM = 0;
	private static int flag = 0;
	private static String TempSKU = "";

	public static void main(String args[]) throws IOException {

		FileWriter writer = null;
		CSVParser csvParser = null;
		Reader reader = null;

		try {
			reader = Files.newBufferedReader(Paths.get(csvFile));
			csvParser = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(reader);
			writer = new FileWriter(newCSV);
			final CSVWriter csvWriter = new CSVWriter(writer);
			FIRST_MINIMUM = SECOND_MINIMUM = Float.MAX_VALUE;//Sets value to MAX

			if (flag == 0) {
				flag = 1;
				//prints the headers in lowPrice.csv file
				csvWriter.writeNext(new String[] { "SKU", "FIRST_MINIMUM_PRICE", "SECOND_MINIMUM_PRICE" });
			}
			
			//adds each record to lowPrice.csv file by claculating FIRST_MINIMUM and SECOND_MINIMUM
			csvParser.forEach(C -> {
				Float PRICE = Float.parseFloat(C.get(5));

				if (SECOND_MINIMUM != Float.MAX_VALUE && !(C.get(0).equals(TempSKU))) {
					//Writes data into .csv file
					csvWriter.writeNext(
							new String[] { TempSKU, String.valueOf(FIRST_MINIMUM), String.valueOf(SECOND_MINIMUM) });
				}

				//finds FIRST_MINIMUM and SECOND_MINIMUM
				if (C.get(0).equals(TempSKU)) {
					if (FIRST_MINIMUM > PRICE) {
						SECOND_MINIMUM = FIRST_MINIMUM;
						FIRST_MINIMUM = PRICE;
					} else if (SECOND_MINIMUM > PRICE && (SECOND_MINIMUM > FIRST_MINIMUM)) {
						SECOND_MINIMUM = PRICE;
					}
				} else {
					TempSKU = C.get(0);
					FIRST_MINIMUM = PRICE;
					SECOND_MINIMUM = Float.MAX_VALUE;
				}
			});
			csvWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writer.close();
			csvParser.close();
		}
	}
}