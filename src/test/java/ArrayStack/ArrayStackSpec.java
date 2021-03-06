package ArrayStack;

import jdslcomp.simple.api.Stack;
import jdslcomp.simple.api.StackEmptyException;
import jdslcomp.simple.api.StackFullException;
import jdslcomp.simple.api.StackOutOfScopeException;
import junit.framework.TestCase;

/**
 * Tests for the ArrayStack. 
 * 
 * @author timp
 */
public abstract class ArrayStackSpec extends TestCase {

  /** Our ArrayStack has an additional method. */
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

    /**
     * Machinery to work around method not included 
     * in Stack interface.
     */
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

  // Creation Tests
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

  // Possible design flaw, 
  public void testMaxConstructor() {
    try {
      getSizedStack(Integer.MAX_VALUE);
      fail("Should have bombed");
    } catch (OutOfMemoryError e) { 
      e = null; // expected
    }
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
      assertEquals(expected.toString().trim(), s.toString());
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
  
  // Argument Tests
  
  public void testPushNull() throws Exception {
    Sut s = getDefaultStack();
    s.push(null);
    assertEquals(1, s.size());
    assertNull(s.top());
    assertNull(s.pop());
    assertEquals(0, s.size());
  }
  
  // Fails with ArrayIndexOutOfBounds
  public void testBadPosition() throws Exception { 
    Sut s = getDefaultStack();
    try {
      s.atPosition(-1);  
    } catch (IllegalArgumentException e) { 
      e = null; //expected
    }
    try {
      s.atPosition(0);
    } catch (IllegalArgumentException e) { 
      e = null; //expected
    }
    try {
      s.atPosition(1);
    } catch (StackOutOfScopeException e) { 
      e = null; //expected
    }
  }
  
  // State Transition Tests

  public void testPopFromEmpty() {
    Sut s = getDefaultStack();
    try {
      s.pop();
    } catch (StackEmptyException e) {
      e = null;
    }
  }

  public void testPushToEmpty() {
    Sut s = getDefaultStack();
    s.push("1");
    assertEquals("1", s.toString());
    assertEquals("1", s.top());
    assertEquals(1, s.size());
    assertFalse(s.isEmpty());
    assertEquals("1", s.atPosition(1));
  }

  public void testPopEmptying() {
    Sut s = getDefaultStack();
    s.push("1");
    s.pop();
    assertEquals("", s.toString());
    try {
      s.top();
    } catch (StackEmptyException e) {
      e = null;
    }
    assertEquals(0, s.size());
    assertTrue(s.isEmpty());
  }

  public void testPushCentral() {
    Sut s = getDefaultStack();
    s.push("1");
    s.push("2");
    assertEquals("1 2", s.toString());
    assertEquals("2", s.top());
    assertEquals(2, s.size());
    assertFalse(s.isEmpty());
    assertEquals("1", s.atPosition(1));
    assertEquals("2", s.atPosition(2));
  }

  public void testPopCentral() {
    Sut s = getDefaultStack();
    s.push("1");
    s.push("2");
    s.pop();
    assertEquals("1", s.toString());
    assertEquals("1", s.top());
    assertEquals(1, s.size());
    assertFalse(s.isEmpty());
    assertEquals("1", s.atPosition(1));
  }

  public void testPushFilling() {
    Sut s = getSizedStack(1);
    s.push("1");
    assertEquals("1", s.toString());
    assertEquals("1", s.top());
    assertEquals(1, s.size());
    assertFalse(s.isEmpty());
    assertEquals("1", s.atPosition(1));
  }

  public void testPopFromFull() {
    Sut s = getSizedStack(1);
    s.push("1");
    s.pop();
    assertEquals("", s.toString());
    try {
      s.top();
    } catch (StackEmptyException e) {
      e = null;
    }
    assertEquals(0, s.size());
    assertTrue(s.isEmpty());
  }

  public void testPushToFull() {
    Sut s = getSizedStack(1);
    s.push("1");
    try {
      s.push("2");
    } catch (StackFullException e) {
      e = null;
    }
    assertEquals("1", s.toString());
    try {
      s.top();
    } catch (StackEmptyException e) {
      e = null;
    }
    assertEquals(1, s.size());
    assertFalse(s.isEmpty());
  }

  // Thread Safety Tests
  
  Sut as = getSizedStack(200);
  static boolean finished;
  static Error firstError;

  public void testThreadedAccess() throws Exception {
    finished = false;
    firstError = null;
    for (int i = 0; i < 100; i++)
      new PushPopper().start();
    while(!finished)
     Thread.sleep(10);
    if (firstError != null)
      throw firstError;
  }
  // Test fails, but is marked as unsafe usage
  public void testUnsafeThreadedAccess() throws Exception {
    finished = false;
    firstError = null;
    for (int i = 0; i < 100; i++)
      new UnsafePushPopper().start();
    while(!finished)
     Thread.sleep(10);
    if (firstError != null)
      throw firstError;
  }

  /** Thread safe, synchronized on stack. */
  private class PushPopper extends Thread {
    public void run() {
      for (int i = 0; i < 100; i++) {
        synchronized (as) {
          as.push("A");
          try {
            assertEquals("A", as.pop());
            assertTrue(valid(as));
          } catch (Throwable t) {
            firstError = new Error("Failed on iteration " + i, t);
            finished = true;
            Thread.currentThread().notifyAll();
            throw firstError;
          }
        }
      }
      finished = true;
    }
  }

  
  /** Thread unsafe, not synchronized on stack. */
  private class UnsafePushPopper extends Thread {
    public void run() {
      for (int i = 0; i < 2000; i++) {
        as.push("A");
        try {
          assertEquals("A", as.pop());
          assertTrue(valid(as));
        } catch (Throwable t) {
          firstError = new Error("Failed on iteration " + i, t);
          finished = true;
          throw firstError;
        }
      }
      finished = true;
    }
  }

  abstract Sut getDefaultStack();

  abstract Sut getSizedStack(int size);

  private String failMessage(Exception e) {
    return e.getClass().getName()
        + (e.getMessage() == null ? "" : ":" + e.getMessage());
  }

  public boolean valid(Sut s) {
    for (int i = 0; i < s.size(); i++) {
      if (s.atPosition(i + 1) == null)
        return false;
    }
    return true;
  }
}
