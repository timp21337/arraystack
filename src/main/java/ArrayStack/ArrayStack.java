package ArrayStack;

import jdslcomp.simple.api.Stack;
import jdslcomp.simple.api.StackEmptyException;
import jdslcomp.simple.api.StackFullException;
import jdslcomp.simple.api.StackOutOfScopeException;

public class ArrayStack implements Stack {

  public static final int CAPACITY = 1000;
  private int capacity;
  private Object S[];
  private int tos = -1; // top of stack pointer

  public ArrayStack() { // default constructor
    capacity = CAPACITY;
    S = new Object[capacity];
  }

  public ArrayStack(int cap) {
    capacity = CAPACITY;
    S = new Object[cap];
  }

  public void push(Object e) throws StackFullException {
    if (size() == CAPACITY)
      throw new StackFullException("Push to a full stack");
    tos++;
    S[tos] = e;
  }

  public Object pop() throws StackEmptyException {
    if (isEmpty())
      throw new StackEmptyException("Call to pop when stack isEmpty()");
    Object e = S[tos];
    S[tos] = null;
    tos--;
    return e;
  }

  public int size() {
    return tos + 1;
  }

  public boolean isEmpty() {
    return (tos < 0);
  }

  public Object top() throws StackEmptyException {
    if (isEmpty())
      throw new StackEmptyException("Call to top when stack isEmpty()");
    return S[tos];
  }

  public Object atPosition(int i) throws StackOutOfScopeException {
    if (i > tos)
      throw new StackOutOfScopeException("Attempt to go beyond top of stack");
    else
      return S[i - 1];
  }

  public String toString() {
    String Sout = new String();
    for (int i = 1; i < size(); i++)
      Sout = Sout + S[i].toString() + " ";
    return Sout;
  }

}