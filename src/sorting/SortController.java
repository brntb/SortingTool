package sorting;

import sorting.objects.DataType;
import sorting.objects.LineType;
import sorting.objects.LongType;
import sorting.objects.WordType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SortController {

    private final Scanner scanner;
    private StringBuilder input;
    private List<DataType> data;
    private final String sortType;
    private final String dataType;
    private final String inputFile;
    private final String outputFile;

    public SortController (String sortType, String dataType, String inputFile, String outputFile) {
        this.scanner = new Scanner(System.in);
        this.sortType = sortType;
        this.dataType = dataType;
        this.inputFile = inputFile;
        this.outputFile = outputFile;

    }

    public void sort() {
        //check if file was passed to load data, else get it from user

        if (!this.inputFile.isEmpty()) {
            loadDataFromFile();
        } else {
            getData();
        }

        //parse data depending on passed dataType
        if ("long".equals(dataType)) {
            parseDataToLongs();
        } else if ("word".equals(dataType)) {
            parseDataToWords();
        } else {
            parseDataToLines();
        }

        //sort data by sortType passed
        if ("natural".equals(sortType)) {
            compareNatural();
        } else {
            compareByCount();
        }
    }

    private void getData() {
        input = new StringBuilder();
        while (scanner.hasNext()) {
            input.append(scanner.nextLine()).append("\n");
        }
    }

    private void loadDataFromFile() {
        input = new StringBuilder();
        File file = new File(inputFile);

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                input.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + file);
        }


    }

    private void parseDataToLongs() {
        data = new ArrayList<>();

        String holder = input.toString().replaceAll("\\s+", ",");
        //remove last comma
        holder = holder.substring(0, holder.length() - 1);
        String[] data = holder.split(",");

        for (String str : data) {
            try {
                Long.parseLong(str);
                this.data.add(new LongType(str));
            } catch (NumberFormatException e) {
                System.out.println("\"" + str + "\" is not a long. It will be skipped.");
            }
        }

    }

    private void parseDataToWords() {
        data = new ArrayList<>();
        String holder = input.toString().replaceAll("\\s+", ",");
        //remove last comma
        holder = holder.substring(0, holder.length() - 1);
        String[] data = holder.split(",");

        for (String word : data) {
            this.data.add(new WordType(word));
        }

    }

    private void parseDataToLines() {
        data = new ArrayList<>();
        String[] lines = input.toString().split("\\n");

        for (String line : lines) {
            data.add(new LineType(line));
        }
    }

    private void compareNatural() {

        data.sort((dataType, t1) -> {
            //if is long, need to compare by long val
            if (dataType instanceof LongType) {
                long l1 = Long.parseLong(dataType.getVal());
                long l2 = Long.parseLong(t1.getVal());

                return (int) (l1 - l2);

            } else { //else just compare to str val
                return dataType.getVal().compareTo(t1.getVal());
            }
        });


        String heading = "Total numbers: " + data.size() + ".\nSorted data: ";
        boolean isFirstLine = true;
        StringBuilder dataToWrite = new StringBuilder();

        if (outputFile.isEmpty()) {
            System.out.print(heading);
        } else {
            dataToWrite.append(heading);
        }

        for (DataType type : data) {
            //if data is type line, need to add an extra first line for formatting
            if (type instanceof LineType) {
                if (isFirstLine) {
                    System.out.println();
                    dataToWrite.append("\n");
                    isFirstLine = false;
                }

                //check if need to output to console or to file
                if (outputFile.isEmpty()) {
                    System.out.println(type);
                } else {
                    dataToWrite.append(type);
                }

            } else {

                if (outputFile.isEmpty()) {
                    System.out.print(type + " ");
                } else {
                    dataToWrite.append(type).append(" ");
                }

            }
        }

        //now actually write to file if we need to
        if (!outputFile.isEmpty()) {
            writeTolFile(dataToWrite.toString());
        }

    }

    public void compareByCount() {
        List<DataType> holder = new ArrayList<>();

        //loop through data and update how many times each piece occurs
        for (DataType current : data) {
            if (holder.contains(current)) {
                int idx = getFirstIdx(current);
                DataType found = holder.get(idx);
                found.incrementCount();
                holder.set(idx, found);
            } else {
                holder.add(current);
            }
        }

        //now sort this list
        holder.sort((dataType, t1) -> {
            if (dataType.getCount() == t1.getCount()) {

                if (dataType instanceof LongType) {
                    long l1 = Long.parseLong(dataType.getVal());
                    long l2 = Long.parseLong(t1.getVal());
                    return (int) (l1 - l2);
                } else {
                    return dataType.getVal().compareTo(t1.getVal());
                }

            } else {
                return dataType.getCount() - t1.getCount();
            }
        });

        String header = "Total numbers: " + data.size() + ".";
        StringBuilder dataToWrite = new StringBuilder();

        if (outputFile.isEmpty()) {
            System.out.println(header);
        } else {
            dataToWrite.append(header).append("\n");
        }

        for (DataType current : holder) {
            double decimal = 1.0 * current.getCount() / data.size();
            double percentage = decimal * 100;
            String currentLine = String.format("%s: %d times(s), %d%%\n", current.getVal(), current.getCount(), Math.round(percentage));

            if (outputFile.isEmpty()) {
                System.out.print(currentLine);
            } else {
                dataToWrite.append(currentLine);
            }
        }

        if (!outputFile.isEmpty()) {
            writeTolFile(dataToWrite.toString());
        }
    }

    private int getFirstIdx(DataType toFind) {
        for (int i = 0; i < data.size(); i++) {
            DataType current = data.get(i);

            if (current.equals(toFind)) {
                return i;
            }
        }

        return -1;
    }

    private void writeTolFile(String data) {
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(data);
        } catch (IOException e) {
            System.out.printf("An exception occurs %s", e.getMessage());
        }
    }

}
