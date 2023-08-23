package gui;

import java.util.List;
import java.util.ArrayList;
import java.io.*; 
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Scanner;
import java.util.Arrays;



public class ATMFunc {

    ATMFunc(){}
    public void givenDataArray_whenConvertToCSV_thenOutputCreated(List<String[]> dataLines, String CSV_FILE_NAME) throws IOException {
		File csvOutputFile = new File(CSV_FILE_NAME);
		try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
			dataLines.stream()
			  .map(this::convertToCSV)
			  .forEach(pw::println);
		}
	}

	public String escapeSpecialCharacters(String data) {
		String escapedData = data.replaceAll("\\R", " ");
		if (data.contains(",") || data.contains("\"") || data.contains("'")) {
			data = data.replace("\"", "\"\"");
			escapedData = "\"" + data + "\"";
		}
		return escapedData;
	}

	public String convertToCSV(String[] data) {
		return Stream.of(data)
		  .map(this::escapeSpecialCharacters)
		  .collect(Collectors.joining(","));
	}

	public List<String> getRecordFromLine(String line) {
		List<String> values = new ArrayList<String>();
		try (Scanner rowScanner = new Scanner(line)) {
			rowScanner.useDelimiter(",");
			while (rowScanner.hasNext()) {
				values.add(rowScanner.next());
			}
		}
		return values;
	}


	public Boolean checkDB(String data1,String data2, List<List<String>> items){
		for (List<String> l1 : items) {
			// for(int i=0;i<l1.size();i++) {
				String curr = l1.get(0);
				String next = l1.get(1);

				if(curr.equals(data1) && next.equals(data2)){
					return true;
				}
			// }
		 } 
		 return false;
	}

    public String checkDBpass(String data1,String data2, List<List<String>> items){
        
		for (List<String> l1 : items) {  
				String curr = l1.get(0);
				String next = l1.get(2);
                // System.out.println(curr+" "+next);
				if(curr.equals(data1) && next.equals(data2)){
					return l1.get(1);
				}
		 } 
		 return "";
	}

	public List<List<String>> dbActions(String filename){

		List<List<String>> records = new ArrayList<>();
		// Full path : "C:\\Users\\smrit\\Documents\\Boston\\Semester-1\\OOPS\\BANK\\Fancy-Bank\\database\\login_info.csv"
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				records.add(Arrays.asList(values));
				
			}
		}catch (FileNotFoundException ex) {
			System.out.println(ex);
		} catch (IOException ex2) {
			System.out.println(ex2);
		}

		List<List<String>> items = new ArrayList<>();
		try (Scanner scanner = new Scanner(new File(filename));) {
			while (scanner.hasNextLine()) {
				items.add(getRecordFromLine(scanner.nextLine()));
			}
		}catch (FileNotFoundException ex) {
			System.out.println(ex);
		}

		return items;
	}

}

