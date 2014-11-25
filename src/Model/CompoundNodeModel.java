package Model;

import java.util.ArrayList;
import java.util.List;

public class CompoundNodeModel extends NodeModel 
{
	private List<NodeModel> children;

	public CompoundNodeModel(String id) {
		super(id);
		this.children = new ArrayList<NodeModel>();
		// TODO Auto-generated constructor stub
	}
	
	public void addChildren(NodeModel child)
	{
		this.children.add(child);
	}
	
	public String toString()
	{
		return super.toString();
	}

	public List<NodeModel> getChildren() {
		return children;
	}

	public void setChildren(List<NodeModel> children) {
		this.children = children;
	}
}
