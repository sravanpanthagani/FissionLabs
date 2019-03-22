package fissionLabs.fissionLabs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmployeeData {
	
	private static String directory=System.getProperty("user.dir");
	private final String sort1=directory+File.separator+"organization.txt";
	private final String sort2=directory+File.separator+"expNageRatio.txt";
	private final String sort3=directory+File.separator+"allFields.txt";

	Logger logger = LoggerFactory.getLogger(EmployeeData.class);
	List<EmployeeBean> empData = new ArrayList<EmployeeBean>();
	public void processEmployeeData() {
		Scanner scanner = new Scanner(System.in);
		try {
			
			System.out.println("Please provide Employee Data in format FirstName (String), LastName(String),Experience(Months), Age(Years), Organization(String)");
			String input = scanner.next();
			if (!"".equals(input)) {
				if (validateProcessInput(input, scanner, empData)) {

					empData = prepareData(input, empData);
					logger.info("Employee data contains data size {}", empData.size());
				} else if (input.equalsIgnoreCase("SORT")) {
					logger.info("Input data will be sorted and stored in files");
					
					
					Runnable task = new Runnable() {
						public void run() {

							try {
								logger.info("thread invoked");
								sortNstoreEmpData(empData);
								storeDataWithRatio(empData);
								appendAllData(empData);
								Thread.sleep(5000);

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					};
					Thread t = new Thread(task);
					t.start();
					

				} else if (input.equalsIgnoreCase("EXIT")) {
					System.exit(0);
				}

			} else {
				logger.info("Given input is Invalid {}", input);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			processEmployeeData();
		}

	}
	
	
	public boolean validateProcessInput(String input, Scanner scanner, List<EmployeeBean> empData) {

		boolean isValidInput = true;
		try {
			if (input.contains(",")) {

				String[] inputValues = input.split(",");

				if (inputValues.length == 5) {
					// logger.info("Validating experiance value, you enterted {}", inputValues[2]);
					int exp = Integer.parseInt(inputValues[2]);
					// logger.info("Validating age value, you enterted {}", inputValues[3]);
					int age = Integer.parseInt(inputValues[3]);

				} else {
					logger.info("Given input is invalid, please enter valid input");
					isValidInput = false;
				}

			} else {
				isValidInput = false;
			}
		} catch (NumberFormatException ne) {
			isValidInput = false;
			logger.info("Please check and enter valid experience and age");
		}
		return isValidInput;
	}
	
	
	public List<EmployeeBean> prepareData(String input, List<EmployeeBean> empData) {

		String[] inputValues = input.split(",");

		EmployeeBean eb = new EmployeeBean();
		eb.setFirstName(inputValues[0]);
		eb.setLastName(inputValues[1]);

		//logger.info("converting experience to integer {}", inputValues[2]);
		int exp = Integer.parseInt(inputValues[2]);
		eb.setExperience(exp + "");
		//logger.info("converting age to integer {}", inputValues[3]);
		int age = Integer.parseInt(inputValues[3]);
		eb.setRatio(Math.round(age / exp));
		eb.setAge(age + "");
		eb.setOrganization(inputValues[4]);
		empData.add(eb);

		return empData;
	}
	
	public void sortNstoreEmpData(List<EmployeeBean> empData) {
		Collections.sort(empData, new SortData.OrgnizationSorter().thenComparing(new SortData.ExperianceSorter())
				.thenComparing(new SortData.FirstNameSorter()).thenComparing(new SortData.LastNameSorter()));

		StringBuilder sb = new StringBuilder();
		for (EmployeeBean empValues : empData) {
			//System.out.println(empValues.toString());
			sb.append(empValues.toString());
		}

		writeContent(sort1, sb.toString());

	}
	
	public void storeDataWithRatio(List<EmployeeBean> empData) {
		Collections.sort(empData, new SortData.RatioSorter().thenComparing(new SortData.OrgnizationSorter()));
		StringBuilder sb = new StringBuilder();

		for (EmployeeBean empValues : empData) {
			//System.out.println(empValues.toString());
			sb.append(empValues.toString());
		}

		writeContent(sort2, sb.toString());

	}
	
	public void appendAllData(List<EmployeeBean> empData) {
		
		String emp="";
		for (EmployeeBean empValues : empData) {
			emp = emp + empValues.getFirstName() + "," + empValues.getLastName() + "," + empValues.getExperience() + ","
					+ empValues.getAge() + "," + empValues.getOrganization()+"\n";

		}
		
		writeContent(sort3, emp);
	}
	
	public void writeContent(String filePath, String content) {
		File file = new File(filePath);
		try (FileWriter fw = new FileWriter(file)) {
			fw.write(content);
			fw.flush();
			fw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		logger.info("File saved in location "+ file.getAbsolutePath());
	}
	
}
