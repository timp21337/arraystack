package ArrayStack;

import jdslcomp.simple.api.Stack;

public class FixedArrayStackTest extends ArrayStackSpec {

  @Override
  Stack getDefaultStack() {
    return new FixedArrayStack();
  }

  @Override
  Stack getSizedStack(int size) {
    return new FixedArrayStack(size);
  }

}
