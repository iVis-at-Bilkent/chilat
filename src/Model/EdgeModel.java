package Model;

public class EdgeModel extends BaseModel
{
	private String sourceID;
	private String targetID;
	private String nodeLabel;
	
	public EdgeModel(String id)
	{
		super(id);
	}

	public String getSourceID() {
		return sourceID;
	}

	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}

	public String getTargetID() {
		return targetID;
	}

	public void setTargetID(String targetID) {
		this.targetID = targetID;
	}

	public String getNodeLabel() {
		return nodeLabel;
	}

	public void setNodeLabel(String nodeLabel) {
		this.nodeLabel = nodeLabel;
	}
	
	public String toString()
	{
		return "id: " + this.id + " sourceID: " + this.sourceID + " targetID: " + this.targetID;
	}
}
