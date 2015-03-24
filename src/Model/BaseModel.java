package Model;

public class BaseModel implements Cloneable
{
	String id;
	String label;
	
	public BaseModel(String id)
	{
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
