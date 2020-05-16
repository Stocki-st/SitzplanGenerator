package application;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ClassListHandler {

	private static Vector<Student> studentArray = new Vector<>();
	public static Vector<Student> getStudentArray() {
		return studentArray;
	}

	public Vector<String> studentList = new Vector<>();
	public Vector<String> firstRowList = new Vector<>();
	public Vector<String> sitAloneList = new Vector<>();
	public Map<String, String> fixedChairMap = new HashMap<String, String>();
	public  Map<String, Vector<String>> forbiddenNeighborsMap = new HashMap<String, Vector<String>>();

	private static int numOfStudents;
	private static String classListFilename;
	
	public ClassListHandler() {
		numOfStudents = 0;
	}

	public class Classlists {

	}

	public ClassListHandler getClassLists() {
		for (Student studi : studentArray) {
			String name = studi.getName();
			studentList.add(name);

			String fixedChair = studi.getFixedChair();
			if (!fixedChair.isEmpty()) { // or != null ?
				fixedChairMap.put(name, fixedChair);
			}

			if (studi.isFirstRow()) {
				firstRowList.add(name);
			}

			if (studi.isSitAlone()) {
				sitAloneList.add(name);
			}
			Vector<String> forbiddenPersons = studi.getForbiddenNeighbours();

			try {
				if (!forbiddenPersons.isEmpty()) {
					forbiddenNeighborsMap.put(name, forbiddenPersons);
				}
			} catch (Exception e) {

			}

		}
		return this;

	}



	public int getNumOfStudents() {
		return numOfStudents;
	}

	public static void setNumOfStudents(int num) {
		numOfStudents = num;
	}

	

	@SuppressWarnings("unused")
	private static void writeStudentListToJson(String filename, JSONObject data) throws IOException {
		@SuppressWarnings("resource")
		FileWriter file = new FileWriter(filename);
		file.write(data.toJSONString());
		file.flush();
	}

	public void removeNameFromLists(String name) {
		int index = 0;
		index = studentList.indexOf(name);
		if (index > 0) {
			studentList.remove(index);
		}

		index = firstRowList.indexOf(name);
		if (index > 0) {
			firstRowList.remove(index);
		}

		index = sitAloneList.indexOf(name);
		if (index > 0) {
			sitAloneList.set(index, "");
		}

		// fixedChairMap.remove(name);
	}

	private static JSONObject readStudentListFromJson(String filename)
			throws IOException, ParseException, FileNotFoundException {
		JSONParser parser = new JSONParser();
		// Use JSONObject for simple JSON and JSONArray for array of JSON.
		JSONObject data = (JSONObject) parser
				.parse(new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8")));
		return data;
	}

	@SuppressWarnings("unchecked")
	public int loadClassList(String filename) {
		JSONObject data;
		int id = 0;
		try {

			data = readStudentListFromJson(filename);
			JSONObject studentJsonObject = (JSONObject) data.get(Integer.toString(id));
			while (studentJsonObject != null) {
				System.out.println(studentJsonObject);
				String name = (String) studentJsonObject.get("name");
				boolean sitAlone = (boolean) studentJsonObject.getOrDefault("sitAlone", false);
				boolean firstRow = (boolean) studentJsonObject.getOrDefault("firstRow", false);
				String fixedChair = (String) studentJsonObject.getOrDefault("fixedChair", "");

				Vector<String> forbiddenPersons = new Vector<String>();
				JSONArray forbiddenNeighbors = (JSONArray) studentJsonObject.getOrDefault("forbiddenNeighbors", null);
				if (forbiddenNeighbors != null) {
					Iterator<String> iterator = forbiddenNeighbors.iterator();
					while (iterator.hasNext()) {
						forbiddenPersons.add(iterator.next());
					}
				}
				Student tempStudent = new Student(name, sitAlone, firstRow, fixedChair, forbiddenPersons);
				studentArray.add(tempStudent);
				++id;
				studentJsonObject = (JSONObject) data.get(Integer.toString(id));
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
