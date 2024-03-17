import java.io.*;
import java.util.*;
import com.opencsv.*;
public class CSVData {

    static List<String[]> data = new ArrayList<>();
    static List<String[]> data2 = new ArrayList<>();

    public static void randomPairings(int period) {
        String Period = String.valueOf(period);
        String file = FileSelector.readFromFile("input");
        data.clear();
        data2.clear();
        {try {

            FileReader filereader = new FileReader(Objects.requireNonNull(file));

            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();
            List<String[]> allData = csvReader.readAll();
            int i = 0;
            for (String[] row : allData) {
                for (String cell : row) {
                    if (cell.contains(Period)) {
                        data.add(row);
                    }
                    i++;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }}

        {FileWriter outputfile = null;
            try {
                outputfile = new FileWriter(Objects.requireNonNull(FileSelector.readFromFile("output")));
            } catch (IOException e) {
                e.printStackTrace();
            }

            CSVWriter writer = new CSVWriter(outputfile);

            for (int i = 0; i <data.size() ; i++) {
                data.get(i)[0]=data.get(i)[0].replace("\"","");
                data.get(i)[1]=data.get(i)[1].replace("\"","");
            }

            writer.writeAll(data);

            try {
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        {String line;
            String csvSplitBy = ",";

            List<String> columnA = new ArrayList<>();
            List<String> columnB = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader(Objects.requireNonNull(FileSelector.readFromFile("output"))))) {
                while ((line = br.readLine()) != null) {
                    String[] row = line.split(csvSplitBy);
                    if (row.length >= 2 && !row[0].isEmpty() && !row[1].isEmpty()) {
                        columnA.add(row[0]);
                        columnB.add(row[1]);
                    } else {
                        System.err.println("Skipping row: " + line + ". Insufficient or missing data.");
                    }
                }

                Collections.shuffle(columnB);

                for (int i = 0; i < Math.min(columnA.size(), columnB.size()); i++) {
                    String itemA = columnA.get(i);
                    String itemB = columnB.get(i);
                    String[] pair = {itemA, itemB};
                    data2.add(pair);
                }
                FileWriter outputfile = null;
                try {
                    outputfile = new FileWriter(Objects.requireNonNull(FileSelector.readFromFile("output")));
                } catch (IOException e) {
                    e.printStackTrace();
                }


                CSVWriter writer = new CSVWriter(outputfile);


                for (int i = 0; i <data2.size() ; i++) {
                    data2.get(i)[0]=data2.get(i)[0].replace("\"","");
                    data2.get(i)[1]=data2.get(i)[1].replace("\"","");
                }
                writer.writeAll(data2);

                writer.close();


            } catch (IOException e) {
                e.printStackTrace();
            }}
    }
}


