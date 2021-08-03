package example;

public class ListItem {

	public ListItem() {
		// TODO Auto-generated constructor stub
	}
	int itemId;
	String path;
	String fullPath;
	
	public ListItem(String path, String fullPath, int itemId) {
		super();
		this.path = path;
		this.fullPath = fullPath;
		this.itemId = itemId;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFullPath() {
		return fullPath;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	@Override
	public String toString() {
		return "ListItem [itemId=" + itemId + ", path=" + path + ", fullPath=" + fullPath + "]";
	}
	
	
}
