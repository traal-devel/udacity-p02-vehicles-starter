package ch.traal.vehicles.test.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * https://stackoverflow.com/questions/3693626/how-to-run-test-methods-in-specific-order-in-junit4
 */
public class OrderedRunner extends SpringJUnit4ClassRunner {

  
  /* member variables */

  
  /* constructors */
  public OrderedRunner(Class<?> clazz) throws InitializationError {
    super(clazz);
  }

  
  /* methods */
  @Override
  protected List<FrameworkMethod> computeTestMethods() {
    List<FrameworkMethod> list = super.computeTestMethods();
    List<FrameworkMethod> copy = new ArrayList<FrameworkMethod>(list);
    Collections.sort(copy, new Comparator<FrameworkMethod>() {

      @Override
      public int compare(FrameworkMethod f1, FrameworkMethod f2) {
        TestOrder o1 = f1.getAnnotation(TestOrder.class);
        TestOrder o2 = f2.getAnnotation(TestOrder.class);

        if (o1 == null || o2 == null) {
          return -1;
        }

        return o1.order() - o2.order();
      }
    });
    return copy;
  }
  
}
