package nuthatch.tree.impl;

import java.util.Arrays;

import nuthatch.tree.Path;

public class ExcursionPath implements Path {
	short[] data;
	int pos;
	short[] sourceData;
	int sourcePos;
	static final int N = 8;


	public ExcursionPath(StandardPath path) {
		sourceData = path.data;
		sourcePos = path.pos;
	}


	@Override
	public Path copy() {
		return new StandardPath(this);
	}


	@Override
	public int getElement(int i) {
		if(i < sourcePos) {
			return sourceData[i];
		}
		i = i - sourcePos;
		return data[i];
	}


	@Override
	public int popElement() {
		return data[--pos];
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
		return pos + sourcePos;
	}

}
