package model;

public class TestData {
	
	private String beanName;

	public TestData() {
		this.beanName=this.getClass().getName();
	
	}

	public String getName() {
		return beanName;
	}
}
