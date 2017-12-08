package edu.cj.stack;

import java.util.Stack;
/**
 * 值栈对象，将action压入栈，
 * TODO:暂时没什么用。
* @author david
 */

public class ValueStack {

	private Stack<Object> stack = new Stack<Object>();
	
	public void push(Object o){
		stack.push(o);
	}
	
	public Object pop(){
		return stack.pop();
	}
	
	public Object peek(){
		return stack.peek();
	}

}
