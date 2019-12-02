package ch.traal.vehicles.test.util;

import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

public class StopOnFailureRunner extends OrderedRunner {

  /* member variables */

  /* constructors */
  public StopOnFailureRunner(Class<?> clazz) throws InitializationError {
    super(clazz);
  }

  /* methods */
  @Override
  public void run(RunNotifier runNotifier) {
    runNotifier.addListener(new FailureListener(runNotifier));
    super.run(runNotifier);
  }

}

class FailureListener extends RunListener {

  private RunNotifier runNotifier;

  public FailureListener(RunNotifier runNotifier) {
    super();
    this.runNotifier = runNotifier;
  }

  @Override
  public void testFailure(Failure failure) throws Exception {
    super.testFailure(failure);
    this.runNotifier.pleaseStop();
  }
}
