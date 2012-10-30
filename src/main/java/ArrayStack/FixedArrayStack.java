package ArrayStack;

import jdslcomp.simple.api.Stack;
import jdslcomp.simple.api.StackEmptyException;
import jdslcomp.simple.api.StackFullException;
import jdslcomp.simple.api.StackOutOfScopeException;

public class FixedArrayStack implements Stack{
 
 public static final int CAPACITY = 1000;
 private int capacity;
 private Object S[];
 private int tos = -1; // top of stack pointer

 public FixedArrayStack(){  // default constructor
  capacity = CAPACITY;
  S = new Object[capacity];
 }
 public FixedArrayStack(int cap){
  if(cap < 1)
    throw new IllegalArgumentException("A stack must be at least one element big.");
  capacity = cap;
  S = new Object[cap];
 }
 public int size(){
  return tos+1;
 }
 public boolean isEmpty(){
  return (tos<0);
 }
 public void push(Object e) throws StackFullException{
  if (size()==capacity)
    throw new StackFullException("Push to a full stack");
  tos++;
  S[tos] = e;
 }
 public Object top() throws StackEmptyException{
  if (isEmpty())
    throw new StackEmptyException("Call to top when stack isEmpty()");
  return S[tos];
 }
 public Object pop() throws StackEmptyException{
  if (isEmpty())
    throw new StackEmptyException("Call to pop when stack isEmpty()");
  Object e = S[tos];
  S[tos] = null;
  tos--;
  return e;
 }
 public Object atPosition(int i) throws StackOutOfScopeException{   
   if(i < 1)
     throw new IllegalArgumentException("Stack position must be greater than one.");
   if (i > (tos + 1))
      throw new StackOutOfScopeException("Attempt to go beyond top of stack (" + i + ">" + tos + ")");
   else return S[i-1];
 }
 public String toString(){
  String Sout = new String();
  for (int i=0; i<size(); i++)
   Sout = Sout + S[i].toString() + " ";
  return Sout;
 }

}