package models;

import java.text.ParseException;

public abstract class Model implements Cloneable {
	
	/* ******************************  ATTRIBUTES  *************************************** */
	
	protected String id;
	
	/* ******************************  CONSTRUCTORS  *************************************** */
	
	public Model() {
		this.id = generateId();
	}

	public Model(String id) {
		this.id = id;
	}
	
	/* ******************************  METHODS  *************************************** */
	
	/**
	 * Get the value of the property for the given key
	 * @param key of the property
	 * @return  property value for the given key
     */
	public Object get(String key) throws IllegalArgumentException {
		switch (key) {
		case "id":
			return (Object) this.id;
		default:
			throw new IllegalArgumentException("Invalid key");
		}
	}
	/**
	 * Set the value of the property for the given key
	 * @param key of the property
     */
	public void set(String key, Object value) throws IllegalArgumentException {
		switch (key) {
		case "id":
			this.id = (String) value;
			break;
		default:
			throw new IllegalArgumentException("Invalid key");
		}
	}

	public void update(Model newModel) throws IllegalArgumentException {
		if(newModel == null) throw new IllegalArgumentException("Null model");
		this.id = newModel.id;
	}
	@Override 
	public String toString() {
		return this.id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		Model model = (Model) obj;
		return this.id.equals(model.id);
	}
	
	@Override 
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
	
	public Model fromCSV(String csv) throws ParseException {
		String[] parts = csv.split(";");
		if (parts.length == 0) {
			throw new ParseException("Invalid csv record", 0);
		} else {
			this.id = parts[0];
			return this;
		}
	}
	
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}
	
	/* ******************************  GETTERS AND SETTERS  *************************************** */
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	
	/* ******************************  STATIC METHODS  *************************************** */
	public static String generateId() {
		return java.util.UUID.randomUUID().toString();
	}
}
