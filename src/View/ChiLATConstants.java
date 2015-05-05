package View;

public class ChiLATConstants
{
	public static enum ForceTuningParameterName
	{
		SPRING_FORCE,
		REPULSION_FORCE,
		GRAVITY_FORCE,
		COMP_GRAVITY_FORCE,
		GRAVITY_RANGE,
		COMP_GRAVITY_RANGE,
		IDEAL_EDGE_LENGTH
	};
	
	public static enum LayoutQualityParameterName
	{
		DRAFT,
		DEFAULT,
		POOR
	};
	
	public static enum ZoomPolicyDuringLayout
	{
		ZOOM_TO_FIT_DURING_LAYOUT,
		ZOOM_TO_SELECTED_NODE_DURING_LAYOUT,
		FREE_ZOOM_POLICY_DURING_LAYOUT
	};
}
