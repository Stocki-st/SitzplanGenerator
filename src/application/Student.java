/**;
 * 
 */
package application;

import java.util.Vector;

/**
 * @author mails
 *
 */
public class Student {
	
	private String name;
	private boolean sitAlone;
	private boolean firstRow;
	private String fixedChar;
	private Vector<String> forbiddenNeighbours;
	
	/**
	 * @param name
	 * @param sitAlone
	 * @param firstRow
	 * @param fixedChar
	 * @param forbiddenNeighbours
	 */
	public Student(String name, boolean sitAlone, boolean firstRow, String fixedChar,
			Vector<String> forbiddenNeighbours) {

		this.name = name;
		this.sitAlone = sitAlone;
		this.firstRow = firstRow;
		this.fixedChar = fixedChar;
		this.forbiddenNeighbours = forbiddenNeighbours;
	}
	

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the sitAlone
	 */
	public boolean isSitAlone() {
		return sitAlone;
	}

	/**
	 * @param sitAlone the sitAlone to set
	 */
	public void setSitAlone(boolean sitAlone) {
		this.sitAlone = sitAlone;
	}

	/**
	 * @return the firstRow
	 */
	public boolean isFirstRow() {
		return firstRow;
	}

	/**
	 * @param firstRow the firstRow to set
	 */
	public void setFirstRow(boolean firstRow) {
		this.firstRow = firstRow;
	}

	/**
	 * @return the fixedChar
	 */
	public String getFixedChair() {
		return fixedChar;
	}

	/**
	 * @param fixedChar the fixedChar to set
	 */
	public void setFixedChar(String fixedChar) {
		this.fixedChar = fixedChar;
	}

	/**
	 * @return the forbiddenNeighbours
	 */
	public Vector<String> getForbiddenNeighbours() {
		return forbiddenNeighbours;
	}

	/**
	 * @param forbiddenNeighbours the forbiddenNeighbours to set
	 */
	public void setForbiddenNeighbours(Vector<String> forbiddenNeighbours) {
		this.forbiddenNeighbours = forbiddenNeighbours;
	}

}
