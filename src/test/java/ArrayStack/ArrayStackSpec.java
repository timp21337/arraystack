package ArrayStack;

import jdslcomp.simple.api.Stack;
import jdslcomp.simple.api.StackEmptyException;
import jdslcomp.simple.api.StackFullException;
import jdslcomp.simple.api.StackOutOfScopeException;
//JUnit 3
import junit.framework.TestCase;

public abstract class ArrayStackSpec extends TestCase {
	
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
    Stack s = getSizedStack(1);
    exerciseSized(s, 1);
	}
  public void testThreeConstructor() {
    Stack s = getSizedStack(3);
    exerciseSized(s, 3);
  }
  public void testCapacityConstructor() {
    Stack s = getSizedStack(ArrayStack.CAPACITY);
    exerciseSized(s, ArrayStack.CAPACITY);
  }
  public void testDefaultConstructor() {
    Stack s = getDefaultStack();
    exerciseSized(s, ArrayStack.CAPACITY);
  }

  private void exerciseSized(Stack s, int size) {
    assertTrue(s.isEmpty());
    assertEquals("", s.toString());
    try { 
      s.top();
    } catch (StackEmptyException e) { 
      e = null;
    }
    String expected = "";
    for (int i = 0; i < size; i++) { 
      s.push(new Integer(i));
      expected += i + " ";
      assertEquals(expected, s.toString());
      assertFalse(s.isEmpty());
      assertEquals(new Integer(i), s.top());
      if (s instanceof ArrayStack) {
        assertEquals(s.top(),((ArrayStack)s).atPosition(i + 1));
        try {
          ((ArrayStack)s).atPosition(i + 2);
        } catch (StackOutOfScopeException e) { 
          e = null;
        }
        try {
          ((ArrayStack)s).atPosition(0);
        } catch (IllegalArgumentException e) { 
          e = null;
        } catch (ArrayIndexOutOfBoundsException e) { 
          fail(failMessage(e)); // bug
        }
      } else {
        assertEquals(s.top(),((FixedArrayStack)s).atPosition(i + 1));   
        try {
          ((FixedArrayStack)s).atPosition(i + 2);
        } catch (StackOutOfScopeException e) { 
          e = null;
        }
        try {
          ((FixedArrayStack)s).atPosition(0);
        } catch (IllegalArgumentException e) { 
          e = null;
        } catch (ArrayIndexOutOfBoundsException e) { 
          fail(failMessage(e)); // bug
        }
      }
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
       fail("Should have bombed with StackFullException"); 
     } catch (StackEmptyException e) { 
       e = null; // expected
     } 
  }
  
  abstract Stack getDefaultStack();
  abstract Stack getSizedStack(int size);
  
  private String failMessage(Exception e) {
    return e.getClass().getName() + (e.getMessage() == null ? "" : ":" + e.getMessage());
  }
}
