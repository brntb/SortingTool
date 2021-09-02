package sorting;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(final String[] args) {
        String sortType = "natural";
        String dataType = "word";
        String inputFile = "";
        String outputFile = "";
        List<String> parameters = Arrays.asList("-sortingType", "-dataType", "-inputFile", "-outputFile");
        List<String> validSorts = Arrays.asList("byCount", "natural");
        List<String> validData = Arrays.asList("long", "word", "line");

        for (int i = 0; i < args.length - 1; i++) {
            if (!parameters.contains(args[i]) && !validSorts.contains(args[i]) && !validData.contains(args[i])) {
                System.out.println("\"" + args[i] + "\" is not a valid parameter. It will be skipped.");
                continue;
            }

            if ("-sortingType".equals(args[i])) {
                if (validSorts.contains(args[i + 1])) {
                    sortType = args[i + 1];
                } else {
                    sortType = "";
                }

            } else if ("-dataType".equals(args[i])) {
                if (validData.contains(args[i + 1])) {
                    dataType = args[i + 1];
                } else {
                    dataType = "";
                }
            } else if ("-inputFile".equals(args[i])) {
                inputFile = args[i + 1];
            } else if ("-outputFile".equals(args[i])) {
                outputFile = args[i + 1];
            }



        }

        if (args.length == 0) {
            System.out.println("No parameters passed!");
        } else if (dataType.isEmpty()) {
            System.out.println("No data type defined!");
        } else if (sortType.isEmpty()) {
            System.out.println("No sorting type defined!");
        } else {
            SortController sortController = new SortController(sortType, dataType, inputFile, outputFile);
            sortController.sort();
        }
    }
}
