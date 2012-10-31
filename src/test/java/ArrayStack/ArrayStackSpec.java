package ArrayStack;

import jdslcomp.simple.api.Stack;
import jdslcomp.simple.api.StackEmptyException;
import jdslcomp.simple.api.StackFullException;
import jdslcomp.simple.api.StackOutOfScopeException;
import junit.framework.TestCase;

public abstract class ArrayStackSpec extends TestCase {
  public interface InspectableStack extends Stack {
    Object atPosition(int position);
  }

  public class Sut implements InspectableStack {
    Stack stack;

    public Sut(Stack s) {
      stack = s;
    }

    @Override
    public int size() {
      return stack.size();
    }

    @Override
    public boolean isEmpty() {
      return stack.isEmpty();
    }

    @Override
    public Object top() throws StackEmptyException {
      return stack.top();
    }

    @Override
    public void push(Object element) {
      stack.push(element);
    }

    @Override
    public Object pop() throws StackEmptyException {
      return stack.pop();
    }

    @Override
    public Object atPosition(int position) {
      if (stack instanceof ArrayStack) {
        return ((ArrayStack) stack).atPosition(position);
      } else if (stack instanceof FixedArrayStack) {
        return ((FixedArrayStack) stack).atPosition(position);
      } else
        throw new RuntimeException("Unexpected class "
            + stack.getClass().getName());
    }
    
    @Override 
    public String toString() { 
      return stack.toString();
    }
  }

  public void testNegativeSizeConstructor() {
    try {
      getSizedStack(-1);
    } catch (NegativeArraySizeException e) {
      fail(failMessage(e)); // bug
    } catch (IllegalArgumentException e) {
      e = null; // expected
    }
  }

  public void testZeroSizedConstructor() {
    @SuppressWarnings("unused")
    Stack zero = null;
    try {
      zero = getSizedStack(0);
      fail("Should have bombed with IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      e = null; // expected
    }
  }

  public void testOneConstructor() {
    Sut s = getSizedStack(1);
    exerciseSized(s, 1);
  }

  public void testThreeConstructor() {
    Sut s = getSizedStack(3);
    exerciseSized(s, 3);
  }

  public void testCapacityConstructor() {
    Sut s = getSizedStack(ArrayStack.CAPACITY);
    exerciseSized(s, ArrayStack.CAPACITY);
  }

  public void testMaxConstructor() {
    Sut s = getSizedStack(Integer.MAX_VALUE);
    exerciseSized(s, Integer.MAX_VALUE);
  }

  public void testDefaultConstructor() {
    Sut s = getDefaultStack();
    exerciseSized(s, ArrayStack.CAPACITY);
  }

  private void exerciseSized(Sut s, int size) {
    assertTrue(s.isEmpty());
    assertEquals("", s.toString());
    try {
      s.top();
    } catch (StackEmptyException e) {
      e = null;
    }
    StringBuffer expected = new StringBuffer();
    for (int i = 0; i < size; i++) {
      s.push(new Integer(i));
      assertFalse(s.isEmpty());
      assertEquals(new Integer(i), s.top());
      assertEquals(s.top(), s.atPosition(i + 1));
      try {
        s.atPosition(i + 2);
      } catch (StackOutOfScopeException e) {
        e = null;
      }
      try {
        s.atPosition(0);
      } catch (IllegalArgumentException e) {
        e = null;
      } catch (ArrayIndexOutOfBoundsException e) {
        fail(failMessage(e)); // bug
      }
      expected.append(i + " ");
      assertEquals(expected.toString(), s.toString());
    }
    try {
      s.push("breaker");
    } catch (ArrayIndexOutOfBoundsException e) {
      fail("Should have bombed with StackFullException");
    } catch (StackFullException e) {
      e = null; // expected
    }

    for (int i = 0; i < size; i++) {
      s.pop();
    }
    try {
      s.pop();
    } catch (ArrayIndexOutOfBoundsException e) {
      fail("Should have bombed with StackEmptyException");
    } catch (StackEmptyException e) {
      e = null; // expected
    }
  }

  abstract Sut getDefaultStack();

  abstract Sut getSizedStack(int size);

  private String failMessage(Exception e) {
    return e.getClass().getName()
        + (e.getMessage() == null ? "" : ":" + e.getMessage());
  }
}
