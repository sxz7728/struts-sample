package sample.action.test;

import org.apache.struts2.convention.annotation.Action;

import sample.action.BaseAction;

public class HelloWorld extends BaseAction {
	private static final long serialVersionUID = 1L;

	public HelloWorld() {
		super(false);
	}

	@Action("helloWorld")
	public String execute() {
		return INPUT;
	}

	public static void main(String[] args) {

	}
}
