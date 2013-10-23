package org.wizehack.mri.Test;

import java.util.ArrayList;
import java.util.List;

public abstract class TestSuite {
	private float id;
	private String name;
	protected List<TestSuite> children = new ArrayList<TestSuite>();
	
	public TestSuite() {
		super();
	}

	public abstract void add(TestSuite testSuite);
	public abstract void setPassed(boolean passed);
	public abstract boolean isPassed();

	
	public List<TestSuite> getChildren(){
        return children;
    }

	public float getId() {
		return id;
	}

	public void setId(float id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
