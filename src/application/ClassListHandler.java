package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ClassListHandler {

	public ClassListHandler() {
		numOfStudents = 0;
	}

	public int getNumOfStudents() {
		return numOfStudents;
	}

	public static void setNumOfStudents(int num) {
		numOfStudents = num;
	}

	static Vector<String> studentList = new Vector<>();
	static Vector<String> firstRowList = new Vector<>();
	static Vector<String> sitAloneList = new Vector<>();
	static Map<String, String> fixedChairMap = new HashMap<String, String>();
	static Map<String, Vector<String>> forbiddenNeighborsMap = new HashMap<String, Vector<String>>();

	private static int numOfStudents;
	private static String classListFilename = "C:/Users/mails/Projekte/seating-chart-generator/1a.json";

	private static void writeStudentListToJson(String filename, JSONObject data) throws IOException {
		FileWriter file = new FileWriter(filename);
		file.write(data.toJSONString());
		file.flush();
	}

	private static JSONObject readStudentListFromJson(String filename)
			throws IOException, ParseException, FileNotFoundException {
		JSONParser parser = new JSONParser();
		// Use JSONObject for simple JSON and JSONArray for array of JSON.
		JSONObject data = (JSONObject) parser.parse(new FileReader(filename));
		return data;
	}

	public static int loadClassList() {
		JSONObject data;
		int id = 0;
		try {
			data = readStudentListFromJson(getClassListFilename());
			String json = data.toJSONString();

			JSONObject student = (JSONObject) data.get(Integer.toString(id));
			while (student != null) {
				System.out.println(student);
				String name = (String) student.get("name");
				System.out.println(name);
				studentList.add(name);

				String fixedChair = (String) student.get("fixedChair");
				if (fixedChair != null) {
					fixedChairMap.put(name, fixedChair);
				}

				boolean sitAlone = (boolean) student.getOrDefault("sitAlone", false);
				if (sitAlone == true) {
					sitAloneList.add(name);
				}

				boolean firstRow = (boolean) student.getOrDefault("firstRow", false);
				if (firstRow == true) {
					firstRowList.add(name);
				}

				JSONArray forbiddenNeighbors = (JSONArray) student.getOrDefault("forbiddenNeighbors", null);
				if (forbiddenNeighbors != null) {
					Vector<String> forbiddenPersons = new Vector<>();
					Iterator<String> iterator = forbiddenNeighbors.iterator();
					while (iterator.hasNext()) {
						forbiddenPersons.add(iterator.next());
					}
					forbiddenNeighborsMap.put(name, forbiddenPersons);
				}
				++id;
				student = (JSONObject) data.get(Integer.toString(id));
			}
			setNumOfStudents(id);

		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}

	public static String getClassListFilename() {
		return classListFilename;
	}

	public void setClassListFilename(String filename) {
		classListFilename = filename;
	}
}
