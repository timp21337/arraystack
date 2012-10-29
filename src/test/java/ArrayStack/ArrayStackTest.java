package ArrayStack;

import jdslcomp.simple.api.Stack;

public class ArrayStackTest extends ArrayStackSpec {

  @Override
  Stack getDefaultStack() {
    return new ArrayStack();
  }

  @Override
  Stack getSizedStack(int size) {
    return new ArrayStack(size);
  }

}
