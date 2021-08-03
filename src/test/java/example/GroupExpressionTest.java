package example;

import java.util.ArrayList;
import java.util.HashMap;

public class GroupExpressionTest {

	public GroupExpressionTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		ArrayList<ListItem> items = new ArrayList<ListItem>(); 
		items.add(new ListItem("G", "G", 1));
		items.add(new ListItem("GR.GR1", "GR.GR1", 1));
		items.add(new ListItem("GR.GR1.F1", "GR.GR1.F1", 0));
		items.add(new ListItem("GR.GR1.GR2", "GR.GR1.GR2", 2));
		items.add(new ListItem("GR.GR1.GR2.C1", "GR.GR1.GR2.C1", 0));
		items.add(new ListItem("GR.GR1.GR2.C2", "GR.GR1.GR2.C2", 0));
		items.add(new ListItem("GR.GR1.GR2.GR3", "GR.GR1.GR3", 2));
		items.add(new ListItem("GR.GR1.GR2.GR3.C3", "GR.GR1.GR3.C1", 0));
		items.add(new ListItem("GR.GR1.GR2.GR3.C4", "GR.GR1.GR3.C2", 0));
		
		printList(items);
		
		parsingGroup(items);
		
		printList(items);
	}
	
	public static void printList(ArrayList<ListItem> items) {
		System.out.println(">> print List : " + items.size());
		for(ListItem i : items) {
			System.out.println(i.toString());
		}
		System.out.println(">>>>>>\n");		
	}
	public static String getParentItemPath(String path) {
		int idx = path.lastIndexOf(".");
		if(idx > 0) {
			return path.substring(0, idx); 
		}
		return path;
	}
	
	public static ArrayList<ListItem> parsingGroup(ArrayList<ListItem> items) {
		HashMap<String, String> groupIndexs = new HashMap<String, String>();
		
		String fPath = null;
		String parentPath = null;
		String changePath = null;
		// make group index
		for(ListItem i : items) {
			fPath = i.getPath();
			parentPath = getParentItemPath(fPath);
			changePath = groupIndexs.get(parentPath);
			if(changePath != null) {
				fPath = fPath.replace(parentPath, changePath);
			}
			
			if(i.getItemId() == 2) {
				groupIndexs.put(i.getPath(), fPath +"[*]");
//				System.out.println("Add GRID INDEX : "+ i.getPath() + " -> " + groupIndexs.get(i.getPath()));
				i.setFullPath(fPath +"[*]");
			}
			else {
				i.setFullPath(fPath);
//				System.out.println("Append GRID : "+ i.getPath() + " -> " + i.getFullPath());
			}
		}
		return items;
	}
}
