package nuthatch.tree.impl;

import java.util.Arrays;

import nuthatch.tree.Path;

public final class StandardPath implements Path {
	short[] data;
	int pos;
	static final int N = 8;


	public StandardPath() {
		super();
		data = new short[N];
	}


	public StandardPath(ExcursionPath excursionPath) {
		data = Arrays.copyOf(excursionPath.sourceData, excursionPath.sourcePos + excursionPath.data.length);
		pos = excursionPath.sourcePos;
		System.arraycopy(excursionPath.data, 0, data, pos, excursionPath.pos);
		pos = pos + excursionPath.pos;
	}


	public StandardPath(StandardPath path) {
		data = Arrays.copyOf(path.data, path.data.length);
		pos = path.pos;
	}


	@Override
	public Path copy() {
		return new StandardPath(this);
	}


	@Override
	public int getElement(int i) {
		//if(i < pos) {
		return data[i];
		//}
		//else {
		//	throw new ArrayIndexOutOfBoundsException(String.valueOf(i));
		//}
	}


	@Override
	public int popElement() {
		//if(pos > 0) {
		return data[--pos];
		//}
		//else {
		//throw new ArrayIndexOutOfBoundsException(String.valueOf(0));
		//}
	}


	@Override
	public void pushElement(int i) {
		assert i < Short.MAX_VALUE;
		if(pos >= data.length) {
			int newLength = data.length + Math.max(4, data.length >> 1);
			data = Arrays.copyOf(data, newLength);
			assert pos < data.length;
		}
		data[pos++] = (short) i;
	}


	@Override
	public int size() {
		return pos;
	}


	@Override
	public String toString() {
		StringBuilder b = new StringBuilder(size() * 2);
		b.append("T");
		for(int i = 0; i < pos; i++) {
			b.append(".");
			b.append(data[i]);
		}
		return b.toString();
	}

}
