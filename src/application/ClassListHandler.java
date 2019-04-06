package application;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

	public ClassListHandler() {
		numOfStudents = 0;
	}

	public ClassListHandler(ClassListHandler list) {

		this.studentList = new Vector<String>(list.studentList);
		this.firstRowList = new Vector<String>(list.firstRowList);
		this.sitAloneList = new Vector<String>(list.sitAloneList);
		this.fixedChairMap = new HashMap<String, String>(list.fixedChairMap);
		this.forbiddenNeighborsMap = new HashMap<String, Vector<String>>(list.forbiddenNeighborsMap);
	}

	public int getNumOfStudents() {
		return numOfStudents;
	}

	public static void setNumOfStudents(int num) {
		numOfStudents = num;
	}

	public static Vector<String> studentList = new Vector<>();
	public static Vector<String> firstRowList = new Vector<>();
	public static Vector<String> sitAloneList = new Vector<>();
	public static Map<String, String> fixedChairMap = new HashMap<String, String>();
	public static Map<String, Vector<String>> forbiddenNeighborsMap = new HashMap<String, Vector<String>>();

	private static int numOfStudents;
	private static String classListFilename;

	private static void writeStudentListToJson(String filename, JSONObject data) throws IOException {
		FileWriter file = new FileWriter(filename);
		file.write(data.toJSONString());
		file.flush();
	}

	public static void removeNameFromLists(String name) {
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
			sitAloneList.remove(index);
		}

		// fixedChairMap.remove(name);
	}

	private static JSONObject readStudentListFromJson(String filename)
			throws IOException, ParseException, FileNotFoundException {
		JSONParser parser = new JSONParser();
		// Use JSONObject for simple JSON and JSONArray for array of JSON.
		JSONObject data = (JSONObject) parser.parse(new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8")));
		return data;
	}

	@SuppressWarnings("unchecked")
	public static int loadClassList(String filename) {
		JSONObject data;
		int id = 0;
		try {

			data = readStudentListFromJson(filename);
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
