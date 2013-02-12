package nuthatch.tree.impl;

public class StringTree extends StandardTree<String, String> {
	public StringTree(String name) {
		super(name, "str");
	}


	public StringTree(String name, StringTree... children) {
		super(name, "str", children);
	}


	@Override
	public String getData() {
		String val = super.getData();
		if(val == null) {
			return super.getName();
		}
		else {
			return val;
		}
	}
}
